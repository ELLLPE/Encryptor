package command;

import CipherData.CipherKeyCache;
import cipherCore.*;
import cipherCore.cipherKeyProcessing.*;
import cipherDataHandling.characterCodec.CharacterCodecRepository;
import cipherDataHandling.characterCodec.CharacterCodecService;
import cipherDataHandling.permutationMap.PermutationMap;
import cipherDataHandling.permutationMap.PermutationMapRepository;
import cipherDataHandling.permutationMap.PermutationMapService;

public class OperationalStatusCommand implements Command {
    public String name() {
        return "-os";
    }

    public String secondaryName() {
        return "--operational-status";
    }

    public String description() {
        return "Displays the current operational status of Encryptor";
    }

    public void execute(String[] args) {
        System.out.println("Encryptor is operational and ready to use.");

        try {
            CipherKeyCache x = CipherKeyProcessing.cipherKeyReader(args[0]);
            int[][] i = SymbolTransformer.mapSymbolToIndex(args[2].toCharArray());
            boolean k = true;

            int[] y = CipherManager.runEncryptorCipher(x, i[0], k);
            String p = SymbolTransformer.mapIndexToSymbol(y, i[1]);
            System.out.println("output: " + p);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.err.println("");
            System.err.println("Error to do somthing");
        }

    }
}
