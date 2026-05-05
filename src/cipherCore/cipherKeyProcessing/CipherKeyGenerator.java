package cipherCore.cipherKeyProcessing;

import java.util.Random;

import CipherData.CipherKeyGenerationCache;

public class CipherKeyGenerator {

    public static CipherKeyGenerationCache generateWithSeed(long key) { // It works to some degree

        int alphabetLength = 100;

        Random random = new Random(key);

        int segmentAmount = (int) (1
                + (key % 1 + Math.round(Math.log10(random.nextLong(key) / Math.log10(8)))));

        System.out.println("segmentAmount: " + segmentAmount);

        int[] conditionContentArray = new int[segmentAmount];
        int[] deflectorArray = new int[segmentAmount];
        int[] rotorAmountArray = new int[segmentAmount];
        int[][] steppingArray = new int[segmentAmount][];
        int[][] startStepArray = new int[segmentAmount][];
        int[][] permutationMapArray = new int[segmentAmount][];
        int[] conditionAmountArray = new int[segmentAmount];
        int[][] conditionArray = new int[segmentAmount][];
        int[] conditionReset = new int[segmentAmount];

        for (int i = 0; i < segmentAmount; i++) {

            Random random2 = new Random(segmentAmount * i);

            conditionContentArray[i] = random.nextInt((3));
            System.out.println(i + " conditionContentArray: " + conditionContentArray[i]);

            int deflector = Math.abs(random2.nextInt(segmentAmount * 10));

            deflectorArray[i] = deflector;

            System.out.println(i + " Deflector: " + deflector);

            int rotorAmount = 1 + (Math.abs(random2.nextInt(segmentAmount + i)));
            rotorAmountArray[i] = rotorAmount;

            System.out.println(i + " rotorAmount: " + rotorAmount);
            int[] stepping = new int[rotorAmount];
            int[] startStep = new int[rotorAmount];
            int[] permutationMap = new int[rotorAmount];

            for (int o = 0; o < rotorAmount; o++) {

                stepping[o] = Math.abs(random2.nextInt(alphabetLength));
                System.out.println(o + " steppingArray: " + stepping[o]);
                startStep[o] = Math.abs(random2.nextInt(alphabetLength));
                System.out.println(o + " startStepArray: " + startStep[o]);
                permutationMap[o] = Math.abs(random2.nextInt());
                System.out.println(o + " permutationMapArray: " + permutationMap[o]);

            }

            steppingArray[i] = stepping;
            startStepArray[i] = startStep;
            permutationMapArray[i] = permutationMap;

            if (conditionContentArray[i] > 0) {
                int conditionAmount = 1
                        + (Math.abs(random.nextInt() % (i + 1)) * Math.abs(random2.nextInt(segmentAmount)));
                conditionAmountArray[i] = conditionAmount;
                System.out.println(i + " ConditionAmount: " + conditionAmount);

                int[] condition = new int[conditionAmount * 2];

                for (int o = 0; o < conditionAmount * 2; o++) {

                    Random random3 = new Random(o * i + Math.abs(random.nextInt()));

                    condition[o] = Math
                            .abs(random3
                                    .nextInt(alphabetLength + (Math.abs((int) (Math.log10(key * (i + 1) * (o + 1)))))));
                    System.out.println(o + " CONDITION: " + condition[o]);

                }

                conditionArray[i] = condition;

                if (conditionContentArray[i] == 2) {
                    conditionReset[i] = Math
                            .abs(random2.nextInt(alphabetLength + (Math.abs((int) (Math.log10(key * (i + 1)))))));

                    System.out.println(i + " conditionReset: " + conditionReset[i]);
                }
            }

            System.out.println("");

        }

        CipherKeyGenerationCache x = new CipherKeyGenerationCache(segmentAmount, conditionContentArray,
                deflectorArray, rotorAmountArray, steppingArray,
                startStepArray, permutationMapArray, conditionAmountArray, conditionArray, conditionReset);

        return x;

    }

}
