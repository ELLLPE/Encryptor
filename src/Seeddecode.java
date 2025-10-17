import java.util.LinkedHashMap;

public class Seeddecode {
    private String seedIn;

    public Seeddecode(String seedIn) {
        this.seedIn = seedIn;
    }

    public int[][] decode() {
        // SeedNummer värde för dekodning
        char[] seedNum = {
                '§', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
        };
        LinkedHashMap<Character, Integer> seedDecodning = new LinkedHashMap<>();
        for (int i = 0; i < 11; i++) {
            seedDecodning.put(seedNum[i], i);
        }

        // gör om seeden till siffror
        char[] seedChar = seedIn.toCharArray();
        int[] seedValue = new int[16];
        for (int i = 0; i < 16; i++) {
            seedValue[i] = seedDecodning.get(seedChar[i]);
            if (i % 2 == 0) {
                seedValue[i] = (seedValue[i] * 10);
            }
        }

        // för den färdiglästa seeden
        int[] seedDecoded = new int[8];

        // sätter dem tillsammans 1-2
        for (int i = 0, o = 0; i < 8 && o < 16; i++, o += 2) {
            seedDecoded[i] = (seedValue[o] + seedValue[o + 1]) - 1;
        }

        // skriver ut för vissuell kontroll
        for (int i = 0; i < 8; i++) {
            System.out.print(seedDecoded[i] + ", ");
        }
        System.out.println(" ");

        int[][] sendOf = new int[2][4];

        for (int i = 0; i < 4; i++) {
            sendOf[0][i] = seedDecoded[i] & 5;
        }
        for (int i = 4; i < 8; i++) {
            sendOf[1][i - 4] = seedDecoded[i];
        }

        return sendOf;
    }

}
