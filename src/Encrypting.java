public class Encrypting {

    private int[][] inPt;
    // Deflector. It takes the opposite value
    public int[] deflector = { 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
            57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 0,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34 };

    public Encrypting(int[][] inPt) {
        this.inPt = inPt;
    }

    public int[][] calculate() {

        // in inPt 0 = switchValues on the 4 defferent rotors 1 = Rotor positions 2 =
        // the inputed words turned to values

        RotorSwitchScedule rotSwitch = new RotorSwitchScedule();
        CommonVariables commons = new CommonVariables();

        int alphabetLength = 70;
        int alphabetMin1 = alphabetLength - 1;

        // The switch scedule values
        int[] switchScedulevalue = inPt[0];

        // The Rotor Positions
        int[] rP = inPt[1];

        // The message in Integer form
        int[] incryptOutpt = new int[inPt[2].length];
        int i = 0;
        System.out.println("The Inputed Words To The Encrypted Ones");
        while (i < inPt[2].length) {

            rP[0] += 3;
            rP[1]++;
            if (rP[0] > alphabetMin1) {
                rP[1]++;
                rP[0] = rP[0] % alphabetLength;
            }
            if (rP[1] > alphabetMin1) {
                rP[2]++;
                rP[1] = rP[1] % alphabetLength;
            }
            if (rP[2] > alphabetMin1) {
                rP[3]++;
                rP[2] = 0;
            }
            if (rP[3] > alphabetMin1) {
                rP[3] = 0;
            }

            // There exists four rotors but eight outputs, Two outputs are usage for
            // inputting. 8 + 2 = 10 There for rotorOutpt is ten long
            int[] rotorOutpt = new int[10];

            rotorOutpt[0] = inPt[2][i];
            // Rotor 1, 2 och 3 in
            for (int o = 1, p = 0; o < 5 && p < 4; o++, p++) {
                rotorOutpt[o] = (rotSwitch.rotSwitchScedule[switchScedulevalue[p]][(rotorOutpt[p] + rP[p])
                        % alphabetLength]
                        + rP[p])
                        % alphabetLength;
            }

            // deflector
            rotorOutpt[5] = deflector[rotorOutpt[4]];

            // Rotor 1, 2 och 3 deflected
            for (int o = 6, p = 3; o < 10 && p > -1; o++, p--) {
                rotorOutpt[o] = ((rotSwitch.rotSwitchScedule[switchScedulevalue[p]][((rotorOutpt[(o - 1)] - rP[p])
                        + alphabetLength)
                        % alphabetLength] - rP[p]) + alphabetLength) % alphabetLength;
            }

            incryptOutpt[i] = rotorOutpt[9];

            // Printing out the unencrypted to encrypted for debugging purposes
            System.out.print("| " + commons.alphabet[inPt[2][i]] + " -> " + commons.alphabet[rotorOutpt[9]] + " ");

            i++;
        }
        // To make visual space
        System.out.println("|");

        // Packageing the useful information for sendOf
        int[][] sendOf = { rP, incryptOutpt };

        return sendOf;
    }

}
