import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        // Basically importing the common variables from the class CommonVariables
        CommonVariables commons = new CommonVariables();

        // Creating a object of HumanInput
        HumanInput ca = new HumanInput();
        int[][] inPt = ca.collectInput();

        // Creating a object that of Encrypting and giving it the values from
        // HumanInput
        Encrypting rr = new Encrypting(inPt);
        int[][] encryptedOutPt = rr.calculate();

        // To make the terminal less cluttered
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spacing1);
        }

        // Creating a LinkedHashMap for converting the Values to readable characters
        LinkedHashMap<Integer, Character> iTA = new LinkedHashMap<>();
        for (int i = 0; i < commons.alphabet.length; i++) {
            iTA.put(i, commons.alphabet[i]);
        }

        // The step that actually turns the values to readable characters
        char[] encryptedMessageChar = new char[encryptedOutPt[1].length];
        for (int i = 0; i < encryptedOutPt[1].length; i++) {
            encryptedMessageChar[i] = iTA.get(encryptedOutPt[1][i]);
        }

        System.out.println("The Encrypted Or Decrypted Message = ");
        for (int i = 0; i < encryptedMessageChar.length - 1; i++) {
            System.out.print(encryptedMessageChar[i]);
        }
        System.out.println(encryptedMessageChar[encryptedMessageChar.length - 1]);

        // To make the terminal less cluttered
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spacing1);
        }
        // seedMaking is used to format and send the SeedEncode
        int[] seedMaking = new int[commons.seedDivided];
        for (int i = 0, o = commons.seedDivided / 2; i < commons.seedDivided / 2 && o < commons.seedDivided; i++, o++) {
            seedMaking[i] = inPt[0][i] + 1;
            seedMaking[o] = encryptedOutPt[0][i] + 1;
        }

        // creating a new object of the SeedEncode class
        SeedEncode retur = new SeedEncode(seedMaking);
        char[] newSeed = retur.generate();

        // A String Array for the info thats need printing before the new seed
        String[] newSeedInfo = { "The New Seed To Continue", " " };

        // Printing out the new seed
        for (int i = 0; i < 2; i++) {
            System.out.println(newSeedInfo[i]);
        }
        for (int i = 0; i < commons.seedLength - 1; i++) {
            System.out.print(newSeed[i]);
        }
        System.out.println(newSeed[commons.seedLength - 1]);

    }
}