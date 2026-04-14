package CipherData;

public record CipherKeySegmentCache(int deflector, int[] stepping, int[] stepStart, int[] permutationMap,
        int[] conditions, int conditionReset) {

}
