package EncryptionFunctions;

public class Encrypting {

    private int[][] inPt;
    // Deflector. It takes the opposite value
    private static int[] deflector = { 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,
            64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84,
            85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
            23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
            43, 44, 45, 46, 47, 48, 49 };

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
        if (CommonVariables.debug == true) {
            System.out.println(" Encrypting Process Started --> ");
        }
        while (i < inPt[2].length) {

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
            // 0 - 69 limit.
            int deflected = deflector[rotorUpwardCalcValues[inPt[1].length - 1]];

            // Priming by getting the first rotordownwardCalculating
            rotordownwardCalcValues[inPt[1].length - 1] = rotorCalculatingDown(rotorPos[inPt[1].length - 1],
                    switchScheduleValue[inPt[1].length - 1], deflected);

            //

            for (int o = (inPt[1].length - 2); o > -1; o--) {
                rotordownwardCalcValues[o] = rotorCalculatingDown(rotorPos[o], switchScheduleValue[o],
                        rotordownwardCalcValues[o + 1]);
            }

            int rotorFinalOutputValue = rotordownwardCalcValues[0];
            EncryptedInPt[i] = rotorFinalOutputValue;

            // Printing out the unencrypted to encrypted for debugging purposes
            if (CommonVariables.debug == true) {
                System.out.print(
                        "| " + CommonVariables.alphabet[inPt[2][i]] + " -> "
                                + CommonVariables.alphabet[rotorFinalOutputValue] + " ");
            }

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
