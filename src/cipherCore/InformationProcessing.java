package cipherCore;

import java.util.LinkedHashMap;

import CipherDataHandling.RotSwitSched.RotSwitSchedRepository;
import CipherDataHandling.RotSwitSched.RotSwitSchedService;
import CipherDataHandling.Seed.SeedRepository;
import CipherDataHandling.Seed.SeedService;

public class InformationProcessing {

    public static int[][] seedDecode(String seedIn) {

        int seedLength = (seedIn.length() - 1);
        int rotorSpeedLength = seedLength / 5;
        int scheduleAndRotorLengths = (seedLength / 5) * 4;
        int scheduleOrRotorLengths = (seedLength / 5) * 2;

        System.out.println("Seed Characteristics --> ");
        String[] seedInfo = {
                "Seed Length: " + seedLength,
                "Rotor Speed Length: " + rotorSpeedLength,
                "Schedule And Rotor Position Length: " + scheduleAndRotorLengths,
                "Schedule Or Rotor Position Length: " + scheduleOrRotorLengths
        };
        for (int i = 0; i < seedInfo.length; i++) {
            System.out.println(seedInfo[i]);
        }
        System.out.println("<-- End Of Seed Characteristics");

        // SeedNumber Values For Decoding
        char[] seedSplitting = seedIn.toCharArray();
        if (CommonVariables.debug == true) {
            System.out.println("Chared Array Created From Seed Input " + seedIn);
        }

        int seedSplittedPos = ((seedSplitting.length - 1) / 5);
        if (CommonVariables.debug == true) {
            System.out.println("Splitting at position: " + (seedSplittedPos + 1));
        }
        char[] rotorSpeedChar = new char[seedSplittedPos];
        for (int i = 0; i < seedSplittedPos; i++) {
            rotorSpeedChar[i] = seedSplitting[i];
            if (CommonVariables.debug == true) {
                System.out.println("rotor speed " + rotorSpeedChar[i]);
            }
        }

        int[][] rotorSpeedValue = SymbolTransformer.mapSymbolToIndex(rotorSpeedChar);

        char[] seedChar = new char[(seedSplitting.length - (seedSplittedPos + 1))];

        for (int i = (seedSplittedPos + 1); i < seedSplitting.length; i++) {
            seedChar[i - (seedSplittedPos + 1)] = seedSplitting[i];
            if (CommonVariables.debug == true) {
                System.out.println("seed Char Values " + seedChar[i - (seedSplittedPos + 1)]);
            }
        }

        LinkedHashMap<Character, Integer> seedDecoding = new LinkedHashMap<>();
        for (int i = 0; i < CommonVariables.seedNumberValue.length; i++) {
            seedDecoding.put(CommonVariables.seedNumberValue[i], i);
        }

        int[] seedValue = new int[scheduleAndRotorLengths];

        // used to correct the seed values
        for (int i = 0; i < scheduleAndRotorLengths; i++) {
            seedValue[i] = seedDecoding.get(seedChar[i]);

            // Turns the tens place correct
            if (i % 2 == 0) {
                seedValue[i] = (seedValue[i] * 10);
                if (CommonVariables.debug == true) {
                    System.out.println("Tens Place Adjusted on position " + i);
                }
            }
        }

        // This Array will be used for the COMPLETED seedDecoding
        int[] scheduleValues = new int[scheduleOrRotorLengths / 2];
        int[] RotorValues = new int[scheduleOrRotorLengths / 2];

        // Put them together 1-2
        for (int i = 0, o = 0; i < scheduleOrRotorLengths && o < scheduleAndRotorLengths; i++, o += 2) {
            if (i >= scheduleOrRotorLengths / 2) {
                RotorValues[i - (scheduleOrRotorLengths / 2)] = (seedValue[o] + seedValue[o + 1]);
            } else {
                scheduleValues[i] = (seedValue[o] + seedValue[o + 1]);
            }

        }

        // packaging the seed for sendoff
        int[][] packagedDecodedSeed = { rotorSpeedValue[0], scheduleValues, RotorValues };
        return packagedDecodedSeed;
    }

    public static int[][] autoSeedGenerate(int rotorAmount) {
        //
        int rotorSpeedLength = rotorAmount;
        int seedLength = rotorSpeedLength * 5;
        int scheduleAndRotorLengths = (seedLength / 5) * 4;
        int scheduleOrRotorLengths = (seedLength / 5) * 2;

        int[] seedMaking = new int[scheduleAndRotorLengths];
        int[] SpeedGeneration = new int[rotorSpeedLength];

        // Generating rotor speeds
        SpeedGeneration[0] = CommonVariables.rotorSpeedPossibleValues[(int) (Math.random() * 3) + 2];
        if (rotorSpeedLength > 1) {
            for (int i = 1; i < rotorSpeedLength; i++) {
                SpeedGeneration[i] = CommonVariables.rotorSpeedPossibleValues[(int) (Math.random() * 5)];
            }
        }

        for (int i = 0; i < scheduleAndRotorLengths; i++) {
            if (i % 2 == 0 && i < scheduleOrRotorLengths

            ) {
                seedMaking[i] = (int) (Math.random() * 2);
            } else if (i % 2 == 0) {
                seedMaking[i] = (int) (Math.random() * 9);
            } else {
                seedMaking[i] = (int) (Math.random() * 9) + 1;
            }

        }

        int[][] autoGeneratedSeed = { SpeedGeneration, seedMaking };
        return autoGeneratedSeed;
    }

    public static String autoGeneratedSeedToString(int[][] autoGeneratedSeed) {
        StringBuilder seedStringBuilder = new StringBuilder();
        for (int i = 0; i < autoGeneratedSeed[0].length; i++) {
            seedStringBuilder.append(CommonVariables.alphabet[autoGeneratedSeed[0][i]]);
        }
        seedStringBuilder.append("-");
        for (int i = 0; i < autoGeneratedSeed[1].length; i++) {
            seedStringBuilder.append(CommonVariables.seedNumberValue[autoGeneratedSeed[1][i]]);
        }
        return seedStringBuilder.toString();
    }

    public static String runEncryptorCipher(int[][] seedValues, String input) {

        int[][] fixedTextIntegerForm = SymbolTransformer.mapSymbolToIndex(input.toCharArray());

        int[][] inPt = new int[][] { seedValues[1], seedValues[2],
                fixedTextIntegerForm[0], seedValues[0] };

        RotSwitSchedRepository repository = new RotSwitSchedRepository();
        RotSwitSchedService service = new RotSwitSchedService(repository);

        Encrypting encryptor = new Encrypting(inPt, service.getRotorSwitchSchedule(), service.getDeflector());

        int[][] encrypted = encryptor.calculate();

        if (CommonVariables.debug == true) {
            System.out.println("Encrypted values: ");
            for (int i = 0; i < encrypted[1].length; i++) {
                System.out.print(CommonVariables.alphabet[encrypted[1][i]] + "");
            }
            System.out.println();
        }

        return SymbolTransformer.mapIndexToSymbol(encrypted[1], fixedTextIntegerForm[1]);
    }

    public static String runEncryptorCipherWithJsonFileSeed(String input) {
        try {
            SeedRepository repository = new SeedRepository();
            SeedService service = new SeedService(repository);

            return InformationProcessing.runEncryptorCipher(service.getCurrentSeed(), input);

        } catch (Exception e) {
            System.err.println("Error running Encryptor cipher: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
