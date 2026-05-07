package cipherCore;

import CipherData.CipherKeySegmentCache;
import console.ConsoleOutput;

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

        for (int i = 0; i < rotorPosition.length; i++) {
            if (CommonVariables.debug == true) {
                ConsoleOutput.printInfo(
                        "rotor " + i + " position: " + rotorPosition[i] + " Stepping: " + stepping[i] + " |");
            }
        }

        int[] temporary = new int[rotorPosition.length];
        int[] packageReturn = new int[rotorPosition.length];

        for (int i = 0; i < rotorPosition.length; i++) {

            if (CommonVariables.debug == true) {
                ConsoleOutput.printEssentialInfo(
                        "rotor " + i + " position: " + rotorPosition[i] + " Stepping: " + stepping[i] + " |");
            }

            if (stepping[i] != 0) {
                temporary[i] = (stepping[i] + rotorPosition[i]);
                if (CommonVariables.debug == true) {
                    ConsoleOutput.printEssentialInfo(" updated |");
                }

            } else {
                temporary[i] = rotorPosition[i];
                if (CommonVariables.debug == true) {
                    ConsoleOutput.printEssentialInfo(" did not update |");
                }

            }

            if (temporary[i] > (alphabetLength - 1)) {

                if ((rotorPosition.length - 1) != i) {
                    rotorPosition[i + 1]++;
                    packageReturn[i] = temporary[i] % alphabetLength;

                    // debugging info
                    if (CommonVariables.debug == true) {
                        ConsoleOutput.printCriticalInfo("rotor " + (i + 1) + " increased by 1 due to rotor " + i
                                + " exceeding max length |");
                    }

                } else {
                    packageReturn[i] = temporary[i] % alphabetLength;

                    if (CommonVariables.debug == true) {
                        ConsoleOutput.printEssentialInfo("Last rotor exceeded max length, resetting to "
                                + packageReturn[i] + " |");
                    }
                }
            } else {
                packageReturn[i] = temporary[i];
                if (CommonVariables.debug == true) {
                    ConsoleOutput.printEssentialInfo(" set to position " + packageReturn[i] + " |");

                }
            }
        }
        return packageReturn; // The new updated Rotor
                              // Positions
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
                if (CommonVariables.debug) {
                    ConsoleOutput.printLnError(
                            "Symbol " + i + " in: " + SymbolTransformer.mapIndexToSymbol(toBeEncrypted[i]) + " out: "
                                    + SymbolTransformer.mapIndexToSymbol(rotorFinalOutputValue[i]));
                }
            }
        }

        encryptedSymbol = rotorFinalOutputValue;
        if (CommonVariables.debug) {
            System.out.println("");
            ConsoleOutput.printLnError("--- Encrypted Symbols ---");
            System.out.println("");
        }

        return encryptedSymbol;
    }

}