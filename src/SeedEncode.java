import java.util.LinkedHashMap;

public class SeedEncode {
    private int[] seedMaking;

    public SeedEncode(int[] seedMaking) {
        this.seedMaking = seedMaking;
    }

    public char[] generate() {

        CommonVariables commons = new CommonVariables();

        // SeedNumber Values For Encoding
        LinkedHashMap<Integer, Character> seedEncoding = new LinkedHashMap<>();
        for (int i = 0; i < 11; i++) {
            seedEncoding.put(i, commons.seedNumberValue[i]);
        }

        char[] generatedSeedChar = new char[commons.seedLength];
        for (int i = 0, o = 1; i < commons.seedDivided && o < commons.seedLength; i++, o += 2) {
            if (seedMaking[i] < 10) {
                generatedSeedChar[o - 1] = '0';
                generatedSeedChar[o] = seedEncoding.get(seedMaking[i]);
            } else {
                generatedSeedChar[o - 1] = seedEncoding.get((seedMaking[i] / 10));
                generatedSeedChar[o] = seedEncoding.get(seedMaking[i] - ((seedMaking[i] / 10) * 10));
            }
        }
        // This turns the slashes to zeros because i do not like the slashes
        for (int i = 0; i < commons.seedLength; i++) {
            if (generatedSeedChar[i] == commons.seedNumberValue[0]) {
                generatedSeedChar[i] = commons.seedNumberValue[10];
            }
        }
        return generatedSeedChar;
    }
}
