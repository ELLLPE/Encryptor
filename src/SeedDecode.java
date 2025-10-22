import java.util.LinkedHashMap;

public class SeedDecode {
    private char[] seedIn;

    public SeedDecode(char[] seedIn) {
        this.seedIn = seedIn;
    }

    public int[][] decode() {

        CommonVariables commons = new CommonVariables();

        // SeedNumber Values For Decoding

        LinkedHashMap<Character, Integer> seedDecoding = new LinkedHashMap<>();
        for (int i = 0; i < commons.seedNumberValue.length; i++) {
            seedDecoding.put(commons.seedNumberValue[i], i);
        }

        // Turns the seed to numbers
        int[] seedValue = new int[commons.seedLength];
        for (int i = 0; i < commons.seedLength; i++) {
            seedValue[i] = seedDecoding.get(seedIn[i]);
            if (seedValue[i] == 10) {
                seedValue[i] = 0;
            }
            if (i % 2 == 0) {
                seedValue[i] = (seedValue[i] * 10);
            }
        }

        // This Array will be used for the COMPLETED seedDecoding
        int[] seedDecoded = new int[commons.seedDivided];

        // Put them together 1-2
        for (int i = 0, o = 0; i < commons.seedDivided && o < commons.seedLength; i++, o += 2) {
            seedDecoded[i] = (seedValue[o] + seedValue[o + 1]) - 1;
        }

        // To make the terminal less cluttered
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spacing1);
        }

        // Explains to the user that the information is in debug purposes
        System.out.println("Debug View For SeedDecoding");

        // Print out for visual confirmation and debugging
        for (int i = 0; i < commons.seedDivided; i++) {
            System.out.print(seedDecoded[i] + ", ");
        }
        System.out.println(" ");

        // packaging the seed for sendOf
        int[][] sendOf = new int[2][commons.seedDivided / 2];

        // The formatting for rotor operation
        for (int i = 0; i < commons.seedDivided / 2; i++) {
            sendOf[0][i] = seedDecoded[i];
        }
        for (int i = commons.seedDivided / 2; i < commons.seedDivided; i++) {
            sendOf[1][i - commons.seedDivided / 2] = seedDecoded[i];
        }

        return sendOf;
    }

}
