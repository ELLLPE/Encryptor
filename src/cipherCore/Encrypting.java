package cipherCore;

import CipherData.CipherKeySegmentCache;

public class Encrypting {

    private int[][] permutationMap;

    private int alphabetLength;

    // Constructor
    public Encrypting(int[][] permutationMap, int alphabetLength) {

        this.permutationMap = permutationMap;

        this.alphabetLength = alphabetLength;
    }

    /**
     * rotorPositionUpdating is used for updating positions
     * based on there position in line and speed
     * 
     * @param rotorPosition
     * @param stepping
     * @return
     */
    private int[] rotorPositionUpdating(int[] rotorPosition, int[] stepping) {
        int i = 0;
        int[] temporary = new int[rotorPosition.length];
        int[] packageReturn = new int[rotorPosition.length];

        while (i < rotorPosition.length) {
            if (stepping[i] != 0) {
                temporary[i] = (stepping[i] + rotorPosition[i]);
                if (CommonVariables.debug == true) {
                    System.out.println("rotor " + i + " updated to position " + temporary[i]);
                }

            } else {
                temporary[i] = rotorPosition[i];
                if (CommonVariables.debug == true) {
                    System.out.println("rotor " + i + " did not move and is still at position " + temporary[i]);
                }

            }

            if (temporary[i] > (alphabetLength - 1)) {

                // debugging info
                if (CommonVariables.debug == true) {
                    System.out.println("rotor " + i + " exceeded max alphabet length and is being reset");
                }

                if ((rotorPosition.length - 1) != i) {
                    rotorPosition[i + 1]++;
                    packageReturn[i] = temporary[i] % alphabetLength;

                    // debugging info
                    if (CommonVariables.debug == true) {
                        System.out.println("rotor " + (i + 1) + " increased by 1 due to rotor " + i
                                + " exceeding max alphabet length");
                    }

                } else {
                    rotorPosition[0]++; // If the last rotor exceeds, the first rotor will increase by 1
                    packageReturn[i] = temporary[i] % alphabetLength;
                    if (CommonVariables.debug == true) {
                        System.out.println("Last rotor exceeded max alphabet length, resetting to "
                                + packageReturn[i] + " and increasing first rotor to "
                                + (stepping[0] + packageReturn[i]));
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

    /**
     * rotorCalculatingUp calculates what value in the alphabet will be used instead
     * of the inputted one.
     * The Up part describes the direction which it calculating
     * 
     * @param rotorStep
     * @param permutation
     * @param rotorInput
     * @return
     */
    private int rotorCalculatingUp(int rotorStep, int permutation, int rotorInput) {

        int packageReturn = 0;

        int y = (rotorInput + rotorStep) % alphabetLength;

        int x = permutationMap[permutation][y];

        packageReturn = (x + rotorStep) % alphabetLength;

        return packageReturn;
    }

    /**
     * rotorCalculatingDown calculates what value in the alphabet will be used
     * instead
     * of the inputted one.
     * The Down part describes the direction which it calculating
     * 
     * @param rotorStep
     * @param permutation
     * @param rotorInput
     * @return
     */
    private int rotorCalculatingDown(int rotorStep, int permutation, int rotorInput) {

        int packageReturn = 0;

        int z = (rotorInput - rotorStep);

        int y = (z + alphabetLength) % alphabetLength;

        int x = permutationMap[permutation][y];

        packageReturn = ((x - rotorStep) + alphabetLength) % alphabetLength;

        return packageReturn;
    }

    // The main calculation function
    public int[] calculate(int[] toBeEncrypted, CipherKeySegmentCache cksc) {

        // The toBeEncrypted in Integer form
        int[] encryptedSymbol = toBeEncrypted;

        int[] rotorStep = cksc.stepStart();

        int[] rotorFinalOutputValue = new int[toBeEncrypted.length];

        // Used for skipping logic
        int xyz = 0;
        int forEachCondition = 0;
        boolean willSkip = true;
        for (int i = 0; i < toBeEncrypted.length; i++) {

            if (cksc.conditions() != null) {
                xyz++;

                if (forEachCondition < cksc.conditions().length) {
                    // need to find better solution :)
                    if (xyz == cksc.conditions()[forEachCondition] && willSkip == true) {
                        forEachCondition++;
                        xyz = 0;
                        willSkip = false;
                    }

                    if (xyz == cksc.conditions()[forEachCondition] && willSkip == false) {
                        forEachCondition++;
                        xyz = 0;
                        willSkip = true;
                    }
                } else if (cksc.conditionReset() != 0) {
                    if (xyz == cksc.conditionReset()) {
                        forEachCondition = 0;
                        xyz = 0;
                    }
                    willSkip = true;
                }

            } else if (cksc.conditions() == null) {
                willSkip = false;
            } else {
                willSkip = true;
            }

            if (willSkip == true) {
                rotorFinalOutputValue = toBeEncrypted;

            } else { // else continue with the cipher
                int rotorOutput = toBeEncrypted[i];

                rotorStep = rotorPositionUpdating(rotorStep, cksc.stepping());

                for (int o = 0; o < cksc.stepStart().length; o++) {
                    rotorOutput = rotorCalculatingUp(rotorStep[o], cksc.permutationMap()[o], rotorOutput);
                }

                // Deflector
                rotorOutput = permutationMap[cksc.deflector()][rotorOutput];

                for (int o = cksc.stepStart().length - 1; o > -1; o--) {
                    rotorOutput = rotorCalculatingDown(rotorStep[o], cksc.permutationMap()[o], rotorOutput);
                }

                rotorFinalOutputValue[i] = rotorOutput;
            }
        }

        encryptedSymbol = rotorFinalOutputValue;

        return encryptedSymbol;
    }

}
