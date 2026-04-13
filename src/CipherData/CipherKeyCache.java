package CipherData;

public record CipherKeyCache(int[][] stepping, int[][] stepStart, int[][] permutationMap, int[][] encipherSkipMap,
        int[][] conditions,
        int[] conditionReset) {
}