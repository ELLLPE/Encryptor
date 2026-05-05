package cipherCore.cipherKeyProcessing;

import java.util.ArrayList;

import CipherData.CipherKeyGenerationCache;
import cipherCore.SymbolTransformer;

public class CipherKeyAssembler {

    private int[] intToSymbols(int intValue) { // ask the coder if the code is unreadable

        StringBuilder x = new StringBuilder();
        x.append(intValue);
        String stringValue = x.toString();

        int p = (stringValue.length() & 1) == 0 ? 0 : 1;

        String[] devidedStringValue = new String[(stringValue.length() + p) / 2];

        StringBuilder y = new StringBuilder();

        for (int i = 0, o = 0; i < (stringValue.length()); i += 2, o++) {

            int off = 0;

            y.append(stringValue.charAt(i));
            if (((stringValue.length() & 1) == 0) == true || o != devidedStringValue.length - 1) {
                y.append(stringValue.charAt(i + 1));
            } else {
                off++;
            }

            devidedStringValue[o] = y.toString();
            y.delete(0, 2 - off);

        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < devidedStringValue.length; i++) {

            char[] ox = devidedStringValue[i].toCharArray();

            int b = SymbolTransformer.mapSymbolNumbersToIndex(ox);

            if (ox[0] == '0')
                list.add(0);
            if (b > 0)
                list.add(b);

        }

        int[] intDevided = list.stream().mapToInt(Integer::intValue).toArray();

        return intDevided;

    }

    public static String cipherKeyAssembly(CipherKeyGenerationCache x) {

        StringBuilder p = new StringBuilder();

        CipherKeyAssembler z = new CipherKeyAssembler();

        String segmentAmount = SymbolTransformer.mapIndexToSymbol(z.intToSymbols(x.segmentAmount()));
        System.out.println("segmentAmount: " + segmentAmount + " " + x.segmentAmount());
        p.append(segmentAmount.length() + segmentAmount);

        for (int i = 0; i < x.segmentAmount(); i++) {

            p.append(x.conditionContent()[i]);

            String deflector = SymbolTransformer.mapIndexToSymbol(z.intToSymbols(x.deflector()[i]));
            p.append(deflector.length() + deflector);
            System.out.println("deflector: " + deflector + " " + x.deflector()[i]);

            String rotorAmount = SymbolTransformer.mapIndexToSymbol(z.intToSymbols(x.rotorAmount()[i]));
            p.append(rotorAmount.length() + rotorAmount);
            System.out.println("rotorAmount: " + rotorAmount + " " + x.rotorAmount()[i]);

            for (int o = 0; o < x.rotorAmount()[i]; o++) {
                p.append(SymbolTransformer.mapIndexToSymbol(x.stepping()[i][o]));
                System.out.println("stepping: " + SymbolTransformer.mapIndexToSymbol(x.stepping()[i][o]) + " "
                        + x.stepping()[i][o]);

                p.append(SymbolTransformer.mapIndexToSymbol(x.startStep()[i][o]));
                System.out.println("startStep: " + SymbolTransformer.mapIndexToSymbol(x.startStep()[i][o]) + " "
                        + x.startStep()[i][o]);

                String permutation = SymbolTransformer.mapIndexToSymbol(z.intToSymbols(x.permutation()[i][o]));
                p.append(permutation.length() + permutation);
                System.out.println("permutation: " + permutation + " " + x.permutation()[i][o]);
            }

            if (x.conditionContent()[i] > 0) {

                String conditionAmount = SymbolTransformer.mapIndexToSymbol(z.intToSymbols(x.conditionAmount()[i]));
                p.append(conditionAmount.length() + conditionAmount);
                System.out.println("conditionAmount: " + conditionAmount + " " + x.conditionAmount()[i]);

                for (int o = 0; o < x.conditionAmount()[i] * 2; o++) {
                    String condition = SymbolTransformer.mapIndexToSymbol(z.intToSymbols(x.condition()[i][o]));
                    p.append(condition.length() + condition);
                    System.out.println("condition: " + condition + " " + x.condition()[i][o]);
                }

                if (x.conditionContent()[i] == 2) {
                    String conditionReset = SymbolTransformer.mapIndexToSymbol(z.intToSymbols(x.conditionReset()[i]));
                    p.append(conditionReset.length() + conditionReset);
                    System.out.println("conditionReset: " + conditionReset + " " + x.conditionReset()[i]);
                }
            }

        }

        return p.toString();

    }

}
