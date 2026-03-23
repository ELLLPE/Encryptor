package cipherCore;

import java.util.LinkedHashMap;
import java.util.ArrayList;

import CipherDataHandling.characterCodec.CharacterCodecRepository;
import CipherDataHandling.characterCodec.CharacterCodecService;

public class SymbolTransformer {

    public static int[][] mapSymbolToIndex(char[] mapSymbol) {

        CharacterCodecRepository repository = new CharacterCodecRepository();
        CharacterCodecService service = new CharacterCodecService(repository);

        LinkedHashMap<Character, Integer> symbolToIndex = new LinkedHashMap<>();
        for (int i = 0; i < CommonVariables.alphabet.length; i++) {
            symbolToIndex.put(service.getCharacterCodec()[i], i);
        }

        ArrayList<Integer> unsupportedSymbol = new ArrayList<Integer>(); // initializing unsupportedSymbol arraylist

        int[] index = new int[mapSymbol.length];

        for (int i = 0; i < mapSymbol.length; i++) {

            if (!symbolToIndex.containsKey(mapSymbol[i])) {
                unsupportedSymbol.add(i); // collects unsupported symbol positions

                if (CommonVariables.debug = true) {
                    System.out.println("found an unsupported symbol at " + i);
                }
                index[i] = 0; // Replaces the unsupported symbol with an supported one to be later replaced
                              // with unsupportedSymbol symbol
                continue;
            }

            index[i] = symbolToIndex.get(mapSymbol[i]);

        }

        int[][] packageReturn = new int[2][];
        packageReturn[0] = index;
        packageReturn[1] = unsupportedSymbol.stream().mapToInt(Integer::intValue).toArray();

        return packageReturn;
    }

    public static String mapIndexToSymbol(int[] mapIndex, int[] unsupportedSymbolPosition) {

        CharacterCodecRepository repository = new CharacterCodecRepository();
        CharacterCodecService service = new CharacterCodecService(repository);

        LinkedHashMap<Integer, Character> indexToSymbol = new LinkedHashMap<>();
        for (int i = 0; i < CommonVariables.alphabet.length; i++) {
            indexToSymbol.put(i, service.getCharacterCodec()[i]);
        }

        char[] symbols = new char[mapIndex.length];

        int o = 0;
        for (int i = 0; i < mapIndex.length; i++) {

            if (unsupportedSymbolPosition.length != o && unsupportedSymbolPosition.length != 0
                    && i == unsupportedSymbolPosition[o]) {
                symbols[i] = service.getUnsupportedCharactersReplacement();
                if (CommonVariables.debug == true) {
                    System.out.println("Replaced Character: " + i + " With Unsupported Symbol Indicator");
                }
                o++;
                continue;
            }

            try {
                symbols[i] = indexToSymbol.get(mapIndex[i]);
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

}
