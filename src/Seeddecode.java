import java.util.LinkedHashMap;

public class SeedDecode {
    private char[] seedIn;

    public SeedDecode(char[] seedIn) {
        this.seedIn = seedIn;
    }

    public int[][] decode() {

        CommonVariables commons = new CommonVariables();

        // SeedNumber Values For Decodeing
        char[] seedNumValue = {
                '/', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
        };
        LinkedHashMap<Character, Integer> seedDecodning = new LinkedHashMap<>();
        for (int i = 0; i < seedNumValue.length; i++) {
            seedDecodning.put(seedNumValue[i], i);
        }

        // Turns the seed to numbers
        int[] seedValue = new int[commons.seedLength];
        for (int i = 0; i < commons.seedLength; i++) {
            seedValue[i] = seedDecodning.get(seedIn[i]);
            if (seedValue[i] == 10) {
                seedValue[i] = 0;
            }
            if (i % 2 == 0) {
                seedValue[i] = (seedValue[i] * 10);
            }
        }

        // This Array will be used for the complered seedDecodeing
        int[] seedDecoded = new int[commons.seedDevided];

        // Put them together 1-2
        for (int i = 0, o = 0; i < commons.seedDevided && o < commons.seedLength; i++, o += 2) {
            seedDecoded[i] = (seedValue[o] + seedValue[o + 1]) - 1;
        }

        // To make the terminal less clutterd
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spaceing1);
        }

        // Explains to the user that the information is in debug purposes
        System.out.println("Debug View For SeedDecodeing");

        // Print out for visual confermation and debuging
        for (int i = 0; i < commons.seedDevided; i++) {
            System.out.print(seedDecoded[i] + ", ");
        }
        System.out.println(" ");

        // packageing the seed for sendOf
        int[][] sendOf = new int[2][commons.seedDevided / 2];

        // The formatation for rotor
        for (int i = 0; i < commons.seedDevided / 2; i++) {
            sendOf[0][i] = seedDecoded[i];
        }
        for (int i = commons.seedDevided / 2; i < commons.seedDevided; i++) {
            sendOf[1][i - commons.seedDevided / 2] = seedDecoded[i];
        }

        return sendOf;
    }

}
