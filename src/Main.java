import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        String mellanrum1 = ("__________________________________________________________________________________________________________________");
        char[] alphabetet = {
                '_', 'E', 'A', 'N', 'T', 'R', 'S', 'I', 'L',
                'D', 'O', 'M', 'K', '.', 'G', 'U', ',', 'H',
                'F', 'V', 'Ä', 'Ö', 'Å', '-', 'P', 'J', '!',
                '?', 'Y', 'C', '"', '3', 'B', '\'', '1', '2',
                '4', ':', '0', '5', '6', ';', '(', '7', '8',
                '9', ')', '+', '=', '/', '@', '&', '%', '*',
                '<', '>', '[', ']', '{', '}', '|', '\\', 'Q',
                'W', '§', '½', 'X', 'Z', '#', '^' // 70 ord/tecken
        };

        Humaninput ca = new Humaninput(alphabetet);
        int[][] inPt = ca.collectInput();

        Incrypting rr = new Incrypting(inPt, alphabetet);
        int[][] incryptedOutPt = rr.calculate();

        // För att snygga upp Terminalen
        for (int i = 0; i < 2; i++) {
            System.out.println(mellanrum1);
        }

        // Skapar en LinkedHashMap för att lätt mata in ett Integer värde för att få ut
        // en Character
        LinkedHashMap<Integer, Character> iTA = new LinkedHashMap<>();
        for (int i = 0; i < alphabetet.length; i++) {
            iTA.put(i, alphabetet[i]);
        }

        // Översätter siffror till bokstäver
        char[] incryptedCharacterChar = new char[incryptedOutPt[1].length];
        for (int i = 0; i < incryptedOutPt[1].length; i++) {
            incryptedCharacterChar[i] = iTA.get(incryptedOutPt[1][i]);
        }

        System.out.println("Den krypterade texten = ");
        for (int i = 0; i < incryptedCharacterChar.length - 1; i++) {
            System.out.print(incryptedCharacterChar[i]);
        }
        System.out.println(incryptedCharacterChar[incryptedCharacterChar.length - 1]);

        // För att snygga upp Terminalen
        for (int i = 0; i < 2; i++) {
            System.out.println(mellanrum1);
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