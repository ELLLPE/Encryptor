import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        // Basically importing the common variables from the class CommonVariables
        CommonVariables commons = new CommonVariables();

        // Createing a object of HumanInput
        Humaninput ca = new Humaninput();
        int[][] inPt = ca.collectInput();

        // Createing a object that of Encrypting and giveing it the values from
        // HumanInput
        Encrypting rr = new Encrypting(inPt);
        int[][] incryptedOutPt = rr.calculate();

        // To make the terminal less clutterd
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spaceing1);
        }

        // Createing a LinkedHashMap for converting the Values to readeable characters
        LinkedHashMap<Integer, Character> iTA = new LinkedHashMap<>();
        for (int i = 0; i < commons.alphabet.length; i++) {
            iTA.put(i, commons.alphabet[i]);
        }

        // The step that actually turns the values to readeable characters
        char[] incryptedCharacterChar = new char[incryptedOutPt[1].length];
        for (int i = 0; i < incryptedOutPt[1].length; i++) {
            incryptedCharacterChar[i] = iTA.get(incryptedOutPt[1][i]);
        }

        System.out.println("The Encrypted Or Decrypted Message = ");
        for (int i = 0; i < incryptedCharacterChar.length - 1; i++) {
            System.out.print(incryptedCharacterChar[i]);
        }
        System.out.println(incryptedCharacterChar[incryptedCharacterChar.length - 1]);

        // To make the terminal less clutterd
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spaceing1);
        }

        System.out.println("Nya Seeden om du vill forsätta skriva");
        for (int i = 0; i < (incryptedOutPt[0].length - 1); i++) {
            if (incryptedOutPt[0][i] > 9) {
                System.out.print(incryptedOutPt[0][i]);
            } else {
                System.out.print("§" + incryptedOutPt[0][i]);
            }
        }
        if (incryptedOutPt[0][incryptedOutPt[0].length - 1] > 9) {
            System.out.print(incryptedOutPt[0][incryptedOutPt[0].length - 1]);
        } else {
            System.out.print("§" + incryptedOutPt[0][incryptedOutPt[0].length - 1]);
        }

    }
}