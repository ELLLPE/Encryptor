package cipherCore.cipherKeyProcessing;

import java.util.ArrayList;
import CipherData.CipherKeyCache;
import CipherData.CipherKeyCacheStore;
import cipherCore.CommonVariables;
import cipherDataHandling.characterCodec.CharacterCodecRepository;
import cipherDataHandling.characterCodec.CharacterCodecService;
import console.ConsoleOutput;

class CipherKeySegmenter {
    ArrayList<Integer> leftOverCipherKey;
    int deflector;
    int[] decKeySegStepping;
    int[] decKeySegStepStart;
    int[] decKeySegPermutationMap;
    int[] decKeySegConditions;
    int decKeySegConReset;

    CipherKeySegmenter(ArrayList<Integer> leftOverCipherKey, int deflector, int[] stepping, int[] startStep,
            int[] permutationMap, int[] conditions, int conditionReset) {
        this.leftOverCipherKey = leftOverCipherKey;
        this.deflector = deflector;
        decKeySegStepping = stepping;
        decKeySegStepStart = startStep;
        decKeySegPermutationMap = permutationMap;
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

    private static final int MAX_COMBINING_DIGITS = 20;
    private static final int MAX_ROTOR_COUNT = 2048;
    private static final int MAX_CONDITION_SEGMENTS = 2048;

    public static int[] symbolMappingToIndex(String encrypted) {

        CharacterCodecService service = new CharacterCodecService(new CharacterCodecRepository());
        char[] chars = encrypted.toCharArray();

        int[] x = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            x[i] = service.getCharacterCodec().indexOf(chars[i]);
        }

        return x;
    }

    private static void validateCombiningLength(int stopPos, int availableSize) {
        if (stopPos < 1 || stopPos > availableSize) {
            throw new IllegalArgumentException("Invalid cipher segment length: " + stopPos);
        }
        if (stopPos > MAX_COMBINING_DIGITS) {
            throw new IllegalArgumentException("Cipher segment length too large: " + stopPos);
        }
    }

    private static void validateRotorCount(int rotorCount, int remainingSize) {
        if (rotorCount < 1 || rotorCount > MAX_ROTOR_COUNT) {
            throw new IllegalArgumentException("Invalid rotor count: " + rotorCount);
        }
        if (rotorCount > remainingSize / 3 + 1) {
            throw new IllegalArgumentException("Rotor count exceeds remaining key data: " + rotorCount);
        }
    }

    static Combining getCombining(ArrayList<Integer> arrayListInput, int stopPos) {

        validateCombiningLength(stopPos, arrayListInput.size());

        if (CommonVariables.debug) {
            ConsoleOutput.printDebugInfo("Combining next " + stopPos + " integers from cipher key segment...");
        }

        arrayListInput.removeFirst();
        int x = 0;
        for (int i = 0; i < stopPos; i++) {
            int y = (int) Math.pow(10, (stopPos - 1) - i);
            if (CommonVariables.debug) {
                ConsoleOutput.printDebugInfo("Combining Value: " + arrayListInput.getFirst() + " * " + y);
            }
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

        int permutationMapAmount = 100; // fix in future to be based on the units amount of permutation maps

        int conditionContent = cipherKeyInteger.getFirst();
        cipherKeyInteger.removeFirst();

        Combining deflector = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
        cipherKeyInteger = deflector.leftOverArrayListInput;

        Combining rotor = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
        cipherKeyInteger = rotor.leftOverArrayListInput;

        validateRotorCount(rotor.value, cipherKeyInteger.size());

        int[] stepping = new int[rotor.value];
        int[] startStep = new int[rotor.value];
        int[] permutationMap = new int[rotor.value];

        for (int i = 0; i < rotor.value; i++) {

            //
            stepping[i] = cipherKeyInteger.getFirst(); // Extracting the "Key" part or the most important.
            cipherKeyInteger.removeFirst();
            startStep[i] = cipherKeyInteger.getFirst();
            cipherKeyInteger.removeFirst();

            if (CommonVariables.debug) {
                ConsoleOutput.printDebugInfo("Extracted stepping for rotor " + i + ": " + stepping[i]);
                ConsoleOutput.printDebugInfo("Extracted start step for rotor " + i + ": " + startStep[i]);
            }

            Combining pm = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
            permutationMap[i] = pm.value % permutationMapAmount;

            cipherKeyInteger = pm.leftOverArrayListInput;
        }

        // returns the information if there is no conditions
        if (conditionContent == 0) {
            return new CipherKeySegmenter(cipherKeyInteger, deflector.value % permutationMapAmount, stepping, startStep,
                    permutationMap, null,
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

            return new CipherKeySegmenter(cipherKeyInteger, deflector.value % permutationMapAmount, stepping, startStep,
                    permutationMap,
                    conditions, conditionReset);
        }

        return new CipherKeySegmenter(cipherKeyInteger, deflector.value % permutationMapAmount, stepping, startStep,
                permutationMap,
                conditions, 0);
    }

    /**
     * 
     * @param cipherKey
     */
    private void cipherKeyReader(String cipherKey) {

        //
        ArrayList<Integer> cipherKeyInteger = new ArrayList<Integer>();
        int[] mappedKey = CipherKeyProcessing.symbolMappingToIndex(cipherKey);
        for (int i = 0; i < mappedKey.length; i++) {
            cipherKeyInteger.add(mappedKey[i]);
        }

        if (cipherKeyInteger.size() < 11)
            throw new IllegalArgumentException("CipherKey To Short!");

        //
        Combining cipKey = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
        cipherKeyInteger = cipKey.leftOverArrayListInput;

        int[] deflector = new int[cipKey.value];
        int[][] stepping = new int[cipKey.value][];
        int[][] stepStart = new int[cipKey.value][];
        int[][] permutationMap = new int[cipKey.value][];
        int[][] conditions = new int[cipKey.value][];
        int[] conditionResets = new int[cipKey.value];

        for (int i = 0; i < cipKey.value; i++) {
            try {
                CipherKeySegmenter segment = getCipherKeySegmenter(cipherKeyInteger);
                deflector[i] = segment.deflector;
                stepping[i] = segment.decKeySegStepping;
                stepStart[i] = segment.decKeySegStepStart;
                permutationMap[i] = segment.decKeySegPermutationMap;
                conditions[i] = segment.decKeySegConditions;
                conditionResets[i] = segment.decKeySegConReset;

                cipherKeyInteger = segment.leftOverCipherKey;

            } catch (Exception e) {
                e.printStackTrace();
                ConsoleOutput.printLnError("Sowwy :3, No wowky");

            }
        }

        CipherKeyCache x = new CipherKeyCache(deflector, stepping, stepStart, permutationMap,
                conditions,
                conditionResets);

        CipherKeyCacheStore.set(x);

    }

    public void runCipherKeyReader(String cipherKey) {

        try {
            CipherKeyProcessing x = new CipherKeyProcessing();
            x.cipherKeyReader(cipherKey);
        } catch (Exception e) {
            e.printStackTrace();
            ConsoleOutput.printLnError("Failed to run CipherKeyReader");
        }

    }

}
