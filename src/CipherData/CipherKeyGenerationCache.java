package CipherData;

public record CipherKeyGenerationCache(int segmentAmount, int[] conditionContent, int[] deflector, int[] rotorAmount,
                int[][] stepping,
                int[][] startStep, int[][] permutation, int[] conditionAmount, int[][] condition,
                int[] conditionReset) {

}
