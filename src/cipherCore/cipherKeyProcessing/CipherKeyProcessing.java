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

    private int permutationMapAmount;

    public CipherKeyProcessing(int permutationMapAmount) {
        this.permutationMapAmount = permutationMapAmount;
    }

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

    private static ArrayList<Integer> rotorReader(ArrayList<Integer> cipherKeyInteger, int rotorCount) {

        int[] stepping = new int[rotorCount];
        int[] startStep = new int[rotorCount];
        int[] permutationMap = new int[rotorCount];

        for (int i = 0; i < rotorCount; i++) {

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
            permutationMap[i] = pm.value % cipherKeyProcessing.permutationMapAmount;

            cipherKeyInteger = pm.leftOverArrayListInput;
        }
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

        

        // removed rotor reader

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

    private void validateCipherKey(String cipherKey) {
        if (cipherKey == null || cipherKey.isEmpty()) {
            throw new IllegalArgumentException("Cipher key cannot be null or empty");
        }
        // Add more validation rules as needed, such as allowed characters, format, etc.
    }

    /**
     * 
     * @param cipherKey
     */
    private void cipherKeyReader(String cipherKey) {

        //

        if (cipherKey.length() > CommonVariables.maxCipherKeyLength) {
            throw new IllegalArgumentException("Cipher key length exceeds maximum allowed: " + CommonVariables.maxCipherKeyLength);
            // add a feature in future to split the cipher key into multiple parts if it exceeds the maximum length, and process them sequentially or in parallel.
        }

        ArrayList<Integer> cipherKeyInteger = new ArrayList<Integer>();
        int[] mappedKey = CipherKeyProcessing.symbolMappingToIndex(cipherKey);
        for (int i = 0; i < mappedKey.length; i++) {
            cipherKeyInteger.add(mappedKey[i]);
        }

        
        //
        Combining cipKeyAmount = getCombining(cipherKeyInteger, cipherKeyInteger.getFirst());
        cipherKeyInteger = cipKeyAmount.leftOverArrayListInput;

        int[] deflector = new int[cipKeyAmount.value];
        int[][] stepping = new int[cipKeyAmount.value][];
        int[][] stepStart = new int[cipKeyAmount.value][];
        int[][] permutationMap = new int[cipKeyAmount.value][];
        int[][] conditions = new int[cipKeyAmount.value][];
        int[] conditionResets = new int[cipKeyAmount.value];

        for (int i = 0; i < cipKeyAmount.value; i++) {
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
<