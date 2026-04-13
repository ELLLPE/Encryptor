package cipherCore;

public class Encrypting {

    private int[][] permutationMap;
    private int[] deflector;
    private int alphabetLength;

    // Constructor
    public Encrypting(int[][] permutationMaps, int[][] encryptingSkipMaps, int[] deflector,
            int alphabetLength) {

        this.permutationMap = permutationMaps;
        this.deflector = deflector;
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
    public int[] calculate(int[] stepping, int[] startRotorStep, int[] permutation, int[] symbols, int[] condition,
            int[] encipherSkipMap) {

        // The symbols in Integer form
        int[] encryptedSymbol = symbols;

        int[] rotorStep = startRotorStep;

        int[] rotorFinalOutputValue = new int[symbols.length];
        int zyz = 0;
        int forEachSkipWithSkipMap = 0;
        int xyz = 0;
        int forEachCondition = 0;
        boolean willSkip = false;
        for (int i = 0; i < symbols.length; i++, xyz++, zyz++) {

            // need to find better solution :)
            if (xyz == condition[forEachCondition] && willSkip == false) {
                forEachCondition++;
                xyz = 0;
                willSkip = true;
            }

            if (xyz == condition[forEachCondition] && willSkip == true) {
                forEachCondition++;
                xyz = 0;
                willSkip = false;
            }

            if (willSkip == false || encipherSkipMap[forEachSkipWithSkipMap] == zyz) {
                rotorFinalOutputValue = symbols;
                if (encipherSkipMap[forEachSkipWithSkipMap] == zyz) {
                    forEachSkipWithSkipMap++;
                    zyz = 0;
                }

            } else { // else continue with the cipher
                int[] rotorUpwardCalcValues = new int[permutation.length];
                int[] rotordownwardCalcValues = new int[permutation.length];

                rotorStep = rotorPositionUpdating(rotorStep, stepping);

                // Priming by getting the first rotorCalculation
                rotorUpwardCalcValues[0] = rotorCalculatingUp(rotorStep[0], permutation[0], symbols[0]);

                //
                for (int o = 1; o < permutation.length; o++) {
                    rotorUpwardCalcValues[o] = rotorCalculatingUp(rotorStep[o], permutation[o],
                            rotorUpwardCalcValues[o - 1]);
                }

                // Using the deflector Array to make the inputted value to the opposite one in a
                // 0 - deflector.length - 1 limit.
                int deflected = deflector[rotorUpwardCalcValues[permutation.length - 1]];

                // Priming by getting the first rotordownwardCalculating
                rotordownwardCalcValues[permutation.length - 1] = rotorCalculatingDown(
                        rotorStep[permutation.length - 1],
                        permutation[permutation.length - 1], deflected);

                //
                for (int o = (permutation.length - 2); o > -1; o--) {
                    rotordownwardCalcValues[o] = rotorCalculatingDown(rotorStep[o], permutation[o],
                            rotordownwardCalcValues[o + 1]);
                }

                rotorFinalOutputValue[i] = rotordownwardCalcValues[0];
            }
        }

        encryptedSymbol = rotorFinalOutputValue;

        return encryptedSymbol;
    }

}
