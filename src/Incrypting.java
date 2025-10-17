public class Incrypting {

    private int[][] inPt;
    private char[] alphabetet;
    // måste skaffa en ordenklig deflector som ger motsatt värde igemtemot rotorn
    public int[] deflector = { 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
            57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 0,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34 };
    public int[][] rotSwitch = { {
            37, 61, 65, 45, 56, 47, 50, 44, 53, 66, 67, 46, 40, 63, 57, 62, 49, 59, 42, 54, 41, 58, 48, 39, 60,
            52, 36, 64, 35, 51, 38, 55, 43, 69, 68, 28, 26,
            0, 30, 23, 12, 20, 18, 32, 7, 3, 11, 5, 22, 16, 6, 29, 25, 8, 19, 31, 4, 14, 21, 17, 24, 1, 15, 13,
            27, 2, 9, 10, 34, 33
    }, {
            42, 54, 67, 60, 46, 36, 37, 47, 61, 51, 49, 52, 35, 62, 64, 57, 39, 69, 58, 45, 41, 55, 38, 48, 50,
            68, 44, 59, 53, 65, 66, 40, 56, 63, 43, 12, 5, 6, 22, 16, 31, 20, 0, 34, 26, 19, 4, 7, 23, 10, 24,
            9, 11, 28, 1, 21, 32, 15, 18, 27, 3, 8, 13, 33, 14, 29, 30, 2, 25, 17
    }, {
            48, 38, 64, 40, 47, 57, 43, 35, 67, 58, 60, 59, 42, 44, 61, 49, 53, 66, 55, 65, 36, 37, 46, 51, 52,
            62, 54, 56, 50, 39, 41, 68, 45, 63, 69, 7, 20, 21, 1, 29, 3, 30, 12, 6, 13, 32, 22, 4, 0, 15, 28,
            23, 24, 16, 26, 18, 27, 5, 9, 11, 10, 14, 25, 33, 2, 19, 17, 8, 31, 34
    }, {
            54, 53, 35, 55, 65, 48, 61, 49, 40, 46, 51, 50, 44, 67, 60, 62, 38, 39, 69, 66, 43, 58, 52, 57, 64,
            37, 45, 42, 59, 47, 56, 36, 63, 68, 41, 2, 31, 25, 16, 17, 8, 34, 27, 20, 12, 26, 9, 29, 5, 7, 11,
            10, 22, 1, 0, 3, 30, 23, 21, 28, 14, 6, 15, 32, 24, 4, 19, 13, 33, 18
    }, {
            46, 56, 52, 61, 37, 53, 42, 50, 41, 67, 49, 48, 47, 44, 51, 55, 63, 43, 36, 65, 62, 57, 54, 69, 45,
            35, 39, 60, 38, 58, 66, 40, 64, 59, 68, 25, 18, 4, 28, 26, 31, 8, 6, 17, 13, 24, 0, 12, 11, 10, 7,
            14, 2, 5, 22, 15, 1, 21, 29, 33, 27, 3, 20, 16, 32, 19, 30, 9, 34, 23
    }
    };

    public Incrypting(int[][] inPt, char[] alphabetet) {
        this.inPt = inPt;
        this.alphabetet = alphabetet;
    }

    public int[][] calculate() {

        // i inPt 0 = switchValues på de tre olika rotorerna 1 = Rotor positioner 2 = är
        // bokstavsvärdena

        int alphabetLength = 70;
        int alphabetMin1 = alphabetLength - 1;

        // Switch schema value
        int[] swSchValue = inPt[0];

        // Rotor pos
        int[] rP = inPt[1];

        // Incrypterad Output
        int[] incryptOutpt = new int[inPt[2].length];
        int i = 0;
        System.out.println("Inmatade bokstavsvärde till de omskakade bokstavsvärdena");
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

            // Det finns fyra Rotorer men 8 outputs två används för inmatning 8 + 2 = 10
            int[] rotorOutpt = new int[10];

            rotorOutpt[0] = inPt[2][i];
            // Rotor 1, 2 och 3 in
            for (int o = 1, p = 0; o < 5 && p < 4; o++, p++) {
                rotorOutpt[o] = (rotSwitch[swSchValue[p]][(rotorOutpt[p] + rP[p]) % alphabetLength] + rP[p])
                        % alphabetLength;
            }

            // deflector
            rotorOutpt[5] = deflector[rotorOutpt[4]];

            // Rotor 1, 2 och 3 deflected
            for (int o = 6, p = 3; o < 10 && p > -1; o++, p--) {
                rotorOutpt[o] = ((rotSwitch[swSchValue[p]][((rotorOutpt[(o - 1)] - rP[p]) + alphabetLength)
                        % alphabetLength] - rP[p]) + alphabetLength) % alphabetLength;
            }

            incryptOutpt[i] = rotorOutpt[9];

            System.out.print(alphabetet[inPt[2][i]] + " -> " + alphabetet[rotorOutpt[9]] + " | ");

            i++;
        }
        System.out.println(" ");

        int[][] output = { rP, incryptOutpt };

        return output;
    }

}
