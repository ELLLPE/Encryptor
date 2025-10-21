import java.util.LinkedHashMap;

public class AutoSeedGen {

    public AutoSeedGen() {

    }

    public char[] generate() {

        CommonVariables commons = new CommonVariables();

        // Generateing the values
        int[] seedMakeing = new int[commons.seedDevided];
        for (int i = 0; i < commons.seedDevided / 2; i++) {
            seedMakeing[i] = (int) (Math.random() * commons.switchSceduleAmount);
        }
        for (int i = commons.seedDevided / 2; i < commons.seedDevided; i++) {
            seedMakeing[i] = (int) (Math.random() * commons.alphabetLength);
        }

        // Formating to seedForm

        // SeedNumber Values For Encodeing
        char[] seedNumValue = {
                '/', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
        };
        LinkedHashMap<Integer, Character> seedEncodeing = new LinkedHashMap<>();
        for (int i = 0; i < 11; i++) {
            seedEncodeing.put(i, seedNumValue[i]);
        }

        char[] generatedSeedChar = new char[commons.seedLength];
        for (int i = 0, o = 1; i < commons.seedDevided && o < commons.seedLength; i++, o += 2) {
            if (seedMakeing[i] < 10) {
                generatedSeedChar[o - 1] = '0';
                generatedSeedChar[o] = seedEncodeing.get(seedMakeing[i]);
            } else {
                generatedSeedChar[o - 1] = seedEncodeing.get((seedMakeing[i] / 10));
                generatedSeedChar[o] = seedEncodeing.get(seedMakeing[i] - ((seedMakeing[i] / 10) * 10));
            }

        }
        return generatedSeedChar;
    }
}
