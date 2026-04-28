package cipherCore;

import java.util.LinkedHashMap;

import cipherDataHandling.characterCodec.CharacterCodecRepository;
import cipherDataHandling.characterCodec.CharacterCodecService;

import java.util.ArrayList;

public class SymbolTransformer {

    public static int[][] mapSymbolToIndex(char[] mapSymbol) {

        CharacterCodecService service = new CharacterCodecService(new CharacterCodecRepository());
        ArrayList<Integer> unsupportedSymbol = new ArrayList<Integer>(); // initializing unsupportedSymbol arraylist

        int[] index = new int[mapSymbol.length];

        for (int i = 0; i < mapSymbol.length; i++) {

            if ((service.getCharacterCodec().indexOf(mapSymbol[i])) == -1) {
                unsupportedSymbol.add(i); // collects unsupported symbol positions

                if (CommonVariables.debug == true) {
                    System.out.println("found an unsupported symbol at " + i);
                }
                index[i] = 0; // Replaces the unsupported symbol with an supported one to be later replaced
                              // with unsupportedSymbol symbol
                continue;
            }

            index[i] = service.getCharacterCodec().indexOf(mapSymbol[i]);

        }

        int[][] packageReturn = new int[2][];
        packageReturn[0] = index;
        packageReturn[1] = unsupportedSymbol.stream().mapToInt(Integer::intValue).toArray();

        return packageReturn;
    }

    public static String mapIndexToSymbol(int[] mapIndex, int[] unsupportedSymbolPosition) {

        CharacterCodecService service = new CharacterCodecService(new CharacterCodecRepository());

        char[] symbols = new char[mapIndex.length];

        int o = 0;
        for (int i = 0; i < mapIndex.length; i++) {

            if (unsupportedSymbolPosition.length != o && unsupportedSymbolPosition.length != 0
                    && i == unsupportedSymbolPosition[o]) {
                symbols[i] = service.getUnsupportedIndicator();
                if (CommonVariables.debug == true) {
                    System.out.println("Replaced Character: " + i + " With Unsupported Symbol Indicator");
                }
                o++;
                continue;
            }

            try {
                symbols[i] = service.getCharacterCodec().charAt(mapIndex[i]);
            } catch (Exception e) {
                System.out.println("Error Converting Index To Symbol" + e.getMessage());
            }

        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < symbols.length; i++) {
            stringBuilder.append(symbols[i]);
        }

        if (CommonVariables.debug == true) {
            System.out.println(stringBuilder.toString());
        }

        return stringBuilder.toString();
    }

    public static String symbolToIndexToSymbol(String input) {

        int[][] index = SymbolTransformer.mapSymbolToIndex(input.toCharArray());

        return SymbolTransformer.mapIndexToSymbol(index[0], index[1]);

    }

    public static int mapSymbolNumbersToIndex(char[] symbolIn) {

        char[] symbolNumbers = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };

        LinkedHashMap<Character, Integer> symbolNumbersToIndex = new LinkedHashMap<>();
        for (int i = 0; i < symbolNumbers.length; i++) {
            symbolNumbersToIndex.put(symbolNumbers[i], i);
        }

        int x = 0;
        for (int i = 0; i < symbolIn.length; i++) {

            int y = (int) Math.pow(10, (symbolIn.length - 1) - i);

            x += symbolNumbersToIndex.get(symbolIn[i]) * y;

        }

        return x;
    }

}
