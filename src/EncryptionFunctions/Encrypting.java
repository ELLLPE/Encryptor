package EncryptionFunctions;

public class Encrypting {

    private int[][] inPt;
    // Deflector. It takes the opposite value
    private static int[] deflector = { 3, 11, 64, 0, 6, 90, 4, 45, 18, 59, 89, 1, 42, 88, 31, 62, 80, 34, 8, 58, 32, 52,
            61, 65, 46, 86, 76, 36, 85, 93, 41, 14, 20, 92, 17, 97, 27, 56, 63, 98, 71, 30, 12, 72, 75, 7, 24, 48, 47,
            83, 68, 95, 21, 67, 57, 94, 37, 54, 19, 9, 74, 22, 15, 38, 2, 23, 77, 53, 50, 87, 78, 40, 43, 81, 60, 44,
            26, 66, 70, 84, 16, 73, 96, 49, 79, 28, 25, 69, 13, 10, 5, 99, 33, 29, 55, 51, 82, 35, 39, 91 };

    // Constructor
    public Encrypting(int[][] inPt) {
        this.inPt = inPt;
    }

    /*
     * rotorPositionUpdating is used for updating positions
     * based on there position in line and speed
     */
    private int[] rotorPositionUpdating(int[] rotorPosition, int[] rotorSpeed) {
        int i = 0;
        int[] temporary = new int[rotorPosition.length];
        int[] packageReturn = new int[rotorPosition.length];

        while (i < rotorPosition.length) {
            if (rotorSpeed[i] != 0) {
                temporary[i] = (rotorSpeed[i] + rotorPosition[i]);
                if (CommonVariables.debug == true) {
                    System.out.println("rotor " + i + " updated to position " + temporary[i]);
                }

            } else {
                temporary[i] = rotorPosition[i];
                if (CommonVariables.debug == true) {
                    System.out.println("rotor " + i + " did not move and is still at position " + temporary[i]);
                }

            }

            if (temporary[i] > (CommonVariables.alphabetLength - 1)) {

                // debugging info
                if (CommonVariables.debug == true) {
                    System.out.println("rotor " + i + " exceeded max alphabet length and is being reset");
                }

                if ((rotorPosition.length - 1) != i) {
                    rotorPosition[i + 1]++;
                    packageReturn[i] = temporary[i] % CommonVariables.alphabetLength;

                    // debugging info
                    if (CommonVariables.debug == true) {
                        System.out.println("rotor " + (i + 1) + " increased by 1 due to rotor " + i
                                + " exceeding max alphabet length");
                    }

                } else {
                    packageReturn[i] = temporary[i] % CommonVariables.alphabetLength;
                    if (CommonVariables.debug == true) {
                        System.out.println("Last rotor exceeded max alphabet length, resetting to "
                                + packageReturn[i]);
                    }
                }
            } else {
                packageReturn[i] = temporary[i];
                if (CommonVariables.debug == true) {
                    System.out.println("rotor " + i + " set to position " + packageReturn[i]);
                }
            }
            i++;
        }
        return packageReturn; // The new updated Rotor Positions
    }

    /*
     * rotorCalculatingUp calculates what value in the alphabet will be used instead
     * of the inputted one.
     * The Up part describes the direction which it calculating
     */
    private int rotorCalculatingUp(int rotorPos, int switchScheduleValue, int rotorInput) {
        int packageReturn = 0;

        int y = (rotorInput + rotorPos) % CommonVariables.alphabetLength;

        int x = RotorSwitchSchedule.rotSwitchSchedule[switchScheduleValue][y];

        packageReturn = (x + rotorPos) % CommonVariables.alphabetLength;

        return packageReturn;
    }

    /*
     * rotorCalculatingDown calculates down. Same as rotorCalculatingUp but reversed
     */
    private int rotorCalculatingDown(int rotorPos, int switchScheduleValue, int rotorInput) {
        int packageReturn = 0;

        int z = (rotorInput - rotorPos);

        int y = (z + CommonVariables.alphabetLength) % CommonVariables.alphabetLength;

        int x = RotorSwitchSchedule.rotSwitchSchedule[switchScheduleValue][y];

        packageReturn = ((x - rotorPos) + CommonVariables.alphabetLength) % CommonVariables.alphabetLength;

        return packageReturn;
    }

    // The main calculation function
    public int[][] calculate() {

        // in inPt 0 = switchValues on the different rotors 1 = Rotor positions 2 =
        // the inputted words turned to values

        // The switch schedule values
        int[] switchScheduleValue = inPt[0];

        // The Rotor Positions
        int[] rotorPos = inPt[1];

        // The message in Integer form
        int[] EncryptedInPt = new int[inPt[2].length];

        int i = 0;
        int x = 0;
        if (CommonVariables.debug == true) {
            System.out.println(" Encrypting Process Started --> ");
        }
        while (i < inPt[2].length) {
            int rotorFinalOutputValue;

            // skipping decryption to combat decipher technics utilizing the flaw of same
            // letter out can not be the same letter in. may be changed in future
            if (x == 19) {
                rotorFinalOutputValue = inPt[2][i];
                x = 0;
            } else { // else continue with the cipher
                int[] rotorUpwardCalcValues = new int[inPt[1].length];
                int[] rotordownwardCalcValues = new int[inPt[1].length];
                int[] rotorSpeed = inPt[3];
                rotorPos = rotorPositionUpdating(rotorPos, rotorSpeed);

                // Priming by getting the first rotorCalculation
                rotorUpwardCalcValues[0] = rotorCalculatingUp(rotorPos[0], switchScheduleValue[0], inPt[2][i]);

                //
                for (int o = 1; o < inPt[1].length; o++) {
                    rotorUpwardCalcValues[o] = rotorCalculatingUp(rotorPos[o], switchScheduleValue[o],
                            rotorUpwardCalcValues[o - 1]);
                }

                // Using the deflector Array to make the inputted value to the opposite one in a
                // 0 - deflector.length - 1 limit.
                int deflected = deflector[rotorUpwardCalcValues[inPt[1].length - 1]];

                // Priming by getting the first rotordownwardCalculating
                rotordownwardCalcValues[inPt[1].length - 1] = rotorCalculatingDown(rotorPos[inPt[1].length - 1],
                        switchScheduleValue[inPt[1].length - 1], deflected);

                //

                for (int o = (inPt[1].length - 2); o > -1; o--) {
                    rotordownwardCalcValues[o] = rotorCalculatingDown(rotorPos[o], switchScheduleValue[o],
                            rotordownwardCalcValues[o + 1]);
                }

                rotorFinalOutputValue = rotordownwardCalcValues[0];
            }

            EncryptedInPt[i] = rotorFinalOutputValue;

            // Printing out the unencrypted to encrypted for debugging purposes
            if (CommonVariables.debug == true) {
                System.out.print(
                        "| " + CommonVariables.alphabet[inPt[2][i]] + " -> "
                                + CommonVariables.alphabet[rotorFinalOutputValue] + " ");
            }

            x++;
            i++;

        }

        for (int o = 0; o < EncryptedInPt.length; o++) {
            if (CommonVariables.debug == true) {
                System.out.print("| " + CommonVariables.alphabet[inPt[2][o]] + " -> "
                        + CommonVariables.alphabet[EncryptedInPt[o]] + " ");
            }
        }

        // Packaging the useful information for sendOf
        int[][] sendOf = { rotorPos, EncryptedInPt };

        return sendOf;
    }

}
