package cipherCore.cipherKeyProcessing;

import java.util.ArrayList;
import CipherData.CipherKeyCache;
import cipherDataHandling.characterCodec.CharacterCodecRepository;
import cipherDataHandling.characterCodec.CharacterCodecService;

class CipherKeySegmenter {
    ArrayList<Integer> leftOverCipherKey;
    int[] decKeySegStepping;
    int[] decKeySegStepStart;
    int[] decKeySegPermutationMap;
    int[] decKeySegEncipherSkipMap;
    int[] decKeySegConditions;
    int decKeySegConReset;

    CipherKeySegmenter(ArrayList<Integer> leftOverCipherKey, int[] stepping, int[] startStep, int[] permutationMap,
            int[] encipherSkipMap, int[] conditions, int conditionReset) {
        this.leftOverCipherKey = leftOverCipherKey;
        decKeySegStepping = stepping;
        decKeySegStepStart = startStep;
        decKeySegPermutationMap = permutationMap;
        decKeySegEncipherSkipMap = encipherSkipMap;
        decKeySegConditions = conditions;
        decKeySegConReset = conditionReset;

    }
}

class Combining {
    ArrayList<Integer> leftOverArrayListInput;
    int value;

    Combining(ArrayList<Integer> leftOverArrayListInput, int combinedIndex) {
        this.leftOverArrayListInput = leftOverArrayListInput;
        value = combinedIndex;
    }
}

/**
 * 
 */
public class CipherKeyProcessing {

    public static int[] symbolMappingToIndex(String encrypted) {

        CharacterCodecService service = new CharacterCodecService(new CharacterCodecRepository());

        int[] x = new int[encrypted.length()];
        for (int i = 0; i < encrypted.length(); i++) {
            x[i] = service.getCharacterCodec().indexOf(encrypted.toCharArray()[i]);
        }

        return x;
    }

    static Combining getCombining(ArrayList<Integer> arrayListInput, int stopPos) {

        arrayListInput.removeFirst();
        int x = 0;
        for (int i = 0; i < stopPos; i++) {

            int y = (int) Math.pow(10, (stopPos - 1) - i);

            x += arrayListInput.getFirst() * y;
            arrayListInput.removeFirst();

        }

        return new Combining(arrayListInput, x);

    }

    /**
     * 
     * @param cipherKeyInteger
     * @return
     */
    static CipherKeySegmenter getCipherKeySegmenter(ArrayList<Integer> cipherKeyInteger) {

        //
        int conditionContent = cipherKeyInteger.getFirst();
        cipherKeyInteger.removeFirst();

        //
        Combining rotor = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
        cipherKeyInteger = rotor.leftOverArrayListInput;

        int[] stepping = new int[rotor.value];
        int[] startStep = new int[rotor.value];
        int[] permutationMap = new int[rotor.value];
        int[] encipherSkipMap = new int[rotor.value];

        for (int i = 0; i < rotor.value; i++) {

            //
            stepping[i] = cipherKeyInteger.getFirst(); // Extracting the "Key" part or the most important.
            cipherKeyInteger.removeFirst();
            startStep[i] = cipherKeyInteger.getFirst();
            cipherKeyInteger.removeFirst();

            Combining permutation = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
            permutationMap[i] = permutation.value;

            Combining encipherSkip = getCombining(permutation.leftOverArrayListInput,
                    permutation.leftOverArrayListInput.getFirst());
            encipherSkipMap[i] = encipherSkip.value;

            cipherKeyInteger = encipherSkip.leftOverArrayListInput;
        }

        // returns the information if there is no conditions
        if (conditionContent == 0) {
            return new CipherKeySegmenter(cipherKeyInteger, stepping, startStep, permutationMap, encipherSkipMap, null,
                    0);
        }

        // Reading in Condition amount
        Combining con = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
        cipherKeyInteger = con.leftOverArrayListInput;

        int[] conditions = new int[con.value * 2];

        // Extracting ConditionStart And ConditionDuration
        for (int i = 0; i < con.value * 2; i += 2) {

            Combining conS = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
            conditions[i] = conS.value;

            Combining conD = getCombining(conS.leftOverArrayListInput, conS.leftOverArrayListInput.getFirst());
            conditions[i + 1] = conD.value;

            cipherKeyInteger = conD.leftOverArrayListInput;
        }

        if (conditionContent == 2) {
            Combining conR = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
            int conditionReset = conR.value;

            return new CipherKeySegmenter(cipherKeyInteger, stepping, startStep, permutationMap, encipherSkipMap,
                    conditions, conditionReset);
        }

        return new CipherKeySegmenter(cipherKeyInteger, stepping, startStep, permutationMap, encipherSkipMap,
                conditions, 0);
    }

    /**
     * 
     * @param cipherKey
     */
    public static CipherKeyCache cipherKeyReader(String cipherKey) {

        //
        ArrayList<Integer> cipherKeyInteger = new ArrayList<Integer>();
        for (int i = 0; i < cipherKey.length(); i++) {
            cipherKeyInteger.add(CipherKeyProcessing.symbolMappingToIndex(cipherKey)[i]);
        }

        if (cipherKeyInteger.size() < 11)
            throw new IllegalArgumentException("CipherKey To Short!");

        //
        Combining cipKey = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
        cipherKeyInteger = cipKey.leftOverArrayListInput;

        int[][] stepping = new int[cipKey.value][];
        int[][] stepStart = new int[cipKey.value][];
        int[][] permutationMap = new int[cipKey.value][];
        int[][] encipherSkipMap = new int[cipKey.value][];
        int[][] conditions = new int[cipKey.value][];
        int[] conditionResets = new int[cipKey.value];

        for (int i = 0; i < cipKey.value; i++) {
            try {
                CipherKeySegmenter segment = getCipherKeySegmenter(cipherKeyInteger);
                stepping[i] = segment.decKeySegStepping;
                stepStart[i] = segment.decKeySegStepStart;
                permutationMap[i] = segment.decKeySegPermutationMap;
                encipherSkipMap[i] = segment.decKeySegEncipherSkipMap;
                conditions[i] = segment.decKeySegConditions;
                conditionResets[i] = segment.decKeySegConReset;

                cipherKeyInteger = segment.leftOverCipherKey;

            } catch (Exception e) {
                System.err.println("Sowwy :3, No wowky");
            }
        }

        CipherKeyCache x = new CipherKeyCache(stepping, stepStart, permutationMap, encipherSkipMap, conditions,
                conditionResets);
        return x;

    }

}
