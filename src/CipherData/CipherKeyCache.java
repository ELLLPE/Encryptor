package CipherData;

public record CipherKeyCache(int[] deflector, int[][] stepping, int[][] stepStart, int[][] permutationMap,
        int[][] conditions, int[] conditionReset) {
}