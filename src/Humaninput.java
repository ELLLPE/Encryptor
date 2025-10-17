import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Humaninput {
    private char[] alphabetet;

    public Humaninput(char[] alphabetet) {
        this.alphabetet = alphabetet;
    }

    public int[][] collectInput() {

        // mellanrum variablerna
        String mellanrum1 = ("__________________________________________________________________________________________________________________");

        // aTI är Alphabetet till integer
        LinkedHashMap<Character, Integer> aTI = new LinkedHashMap<>();
        for (int i = 0; i < alphabetet.length; i++) {
            aTI.put(alphabetet[i], i);
        }

        // Medelande till användare om hur seed funktionen funkar
        String[] seedMsg = {
                mellanrum1, mellanrum1,
                "Seed inmatning. Seed strukturen är 2 siffror för pos och schema. 1-8 är SwitchScheman. 9-16 är Rotorpositionerna",
                "Tillexempel  §1§5§3§23417§567",
                "Den kånstiga symbolen § står i detta fallet för tomrum om det är framför ett värde",
                "Switchvärden är mellan 1 - 5   medans   rotorpositionerna är mellan 1 - 70", " ",
                "Input Seed -->"
        };
        // seed förklaring
        for (int i = 0; i < 8; i++) {
            System.err.println(seedMsg[i]);
        }

        // inmatning av seeden
        Scanner seedInPt = new Scanner(System.in);
        String seedIn = seedInPt.nextLine();

        // mellanrum skapare för snyggare Terminal
        for (int i = 0; i < 2; i++) {
            System.out.println(mellanrum1);
        }
        System.out.println("debug vy för seedens avkodning");

        // Avkodning av seeden
        Seeddecode sd = new Seeddecode(seedIn);
        int[][] seedDecOtPt = sd.decode();

        // För snyggare Terminal samt vad och hur text inmatas
        for (int i = 0; i < 2; i++) {
            System.out.println(mellanrum1);
        }
        System.out.println("Skriv in din text. Men inga siffror, dem får du skriva med ord");
        System.out.println(" ");
        System.out.println("Input Text -->");

        // Inmatning av text samt göra om det till stora bokstäver och sisst nummer
        Scanner string = new Scanner(System.in);
        String text = string.nextLine();
        String noSpaces = text.replaceAll("\s+", "_");
        System.out.println(noSpaces);
        String upperCase = noSpaces.toUpperCase(Locale.forLanguageTag("sv"));
        char[] charForm = upperCase.toCharArray();
        System.out.println(charForm);

        int[] charToAlphaValue = new int[charForm.length];
        for (int i = 0; i < charForm.length; i++) {
            charToAlphaValue[i] = aTI.get(charForm[i]);
        }

        // För att snygga upp Terminalen samt för att debuga
        for (int i = 0; i < 2; i++) {
            System.out.println(mellanrum1);
        }

        // packar ner information och drar tillbaka till Main.java. packedUp 0 = Vilket
        // Switchschema 1 = RotPos 2 = den sifferkodade texten
        int[][] packedUp = {
                seedDecOtPt[0], seedDecOtPt[1], charToAlphaValue
        };
        string.close();
        seedInPt.close();
        return packedUp;
    }
}