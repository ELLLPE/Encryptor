import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Locale;

public class HumanInput {

    public HumanInput() {

    }

    public int[][] collectInput() {

        CommonVariables commons = new CommonVariables();

        // aTI Stands for "alphabet To Integer" and is used to convert Characters to
        // Integers
        LinkedHashMap<Character, Integer> aTI = new LinkedHashMap<>();
        for (int i = 0; i < commons.alphabet.length; i++) {
            aTI.put(commons.alphabet[i], i);
        }

        // To make the terminal less cluttered
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spacing1);
        }

        // Message to User how to operate the Encrypting program
        String[] seedMsg = {
                commons.spacing1, commons.spacing1,
                "Seed Inputting Structure Is Made Up Of Two Digits For Each Specific Input Variant. 0-8 Is Used For Switch Schedules. 9-16 Is Used For Rotor Start Positions",
                "For An Example The Seed May Look This ->  /1/5/3/23417/567  Or Like This -> 0105030234170567",
                "You Can Either Use Zero Or Slash To Mark An Empty Slot", " ", commons.spacing1, commons.spacing1,
                "The Possible Choices For Switch Schedules are 1-20. And The Possible Choices For Rotor Positions Are 1-70",
                " ",
                "Input Seed -->"
        };
        // Printing the message to the User
        for (int i = 0; i < 6; i++) {
            System.err.println(seedMsg[i]);
        }

        // Asking if the user want to auto generate a seed
        System.out.println("Do You Want To Auto Generate A seed? Y/n");
        Scanner yesNo = new Scanner(System.in);
        String answer = yesNo.nextLine();
        String answerBig = answer.toUpperCase();
        char[] answerChar = answerBig.toCharArray();

        int[] seedMaking = new int[commons.seedDivided];
        SeedEncode asg = new SeedEncode(seedMaking);

        char[] seedIn;
        int[][] decodedSeedOutPt;
        Scanner seedInPt = new Scanner(System.in);
        //
        if (!answer.trim().isEmpty() && answerChar[0] == 'Y') {

            // Generating the values
            for (int i = 0; i < commons.seedDivided / 2; i++) {
                seedMaking[i] = (int) (Math.random() * commons.switchScheduleAmount) + 1;
            }
            for (int i = commons.seedDivided / 2; i < commons.seedDivided; i++) {
                seedMaking[i] = (int) (Math.random() * commons.alphabetLength) + 1;
            }

            char[] autoSeed = asg.generate();
            System.out.println("The Auto Generated Seed --> ");
            for (int i = 0; i < autoSeed.length; i++) {
                System.out.print(autoSeed[i]);
            }
            System.out.println(" ");
            seedIn = autoSeed;
        } else {
            for (int i = 6; i < 11; i++) {
                System.out.println(seedMsg[i]);
            }

            // The Scanner collecting the seed
            String seedCollected = seedInPt.nextLine();

            // The seed is made in to an charArray to be checked, if actual seed then it
            // will be sent to seedDecode
            seedIn = seedCollected.toCharArray();

            // If The User just hit enter they will automatically get the standard base seed
            if (seedCollected.trim().isEmpty()) {
                String standardSeed = "0105030234170567";
                seedIn = standardSeed.toCharArray();
                System.out.println(standardSeed);
            }
        }

        // Creating an object of SeedDecode and giving it the seed
        SeedDecode sd = new SeedDecode(seedIn);
        decodedSeedOutPt = sd.decode();

        // To make the terminal less cluttered and to send a message to the user
        String[] textMsg = { commons.spacing1, commons.spacing1, "Write Your Text Here", " ", "Input Text -->" };
        for (int i = 0; i < 5; i++) {
            System.out.println(textMsg[i]);
        }

        // The collecting of the inputted message
        Scanner string = new Scanner(System.in);
        String text = string.nextLine();

        // Readapting the text for encrypting purposes
        String noSpaces = text.replaceAll("\s+", "_");
        System.out.println(noSpaces);
        String upperCase = noSpaces.toUpperCase(Locale.forLanguageTag("sv"));
        char[] charForm = upperCase.toCharArray();
        System.out.println(charForm);

        // Turning the now Readapted message in to values
        int[] charToAlphaValue = new int[charForm.length];
        for (int i = 0; i < charForm.length; i++) {
            charToAlphaValue[i] = aTI.get(charForm[i]);
        }
        // To make the terminal less cluttered
        for (int i = 0; i < 2; i++) {
            System.out.println(commons.spacing1);
        }

        // Packaging the human inputs to send back to main
        // 0 = Switch Schedule 1 = Rotor Positions 2 = The message
        int[][] packedUp = {
                decodedSeedOutPt[0], decodedSeedOutPt[1], charToAlphaValue
        };
        yesNo.close();
        string.close();
        seedInPt.close();

        return packedUp;
    }
}