package cipherCore;

import CipherData.CipherKeyCache;
import CipherData.CipherKeyCacheStore;
import CipherData.CipherKeySegmentCache;
import cipherDataHandling.characterCodec.CharacterCodecRepository;
import cipherDataHandling.characterCodec.CharacterCodecService;
import cipherDataHandling.permutationMap.PermutationMapRepository;
import cipherDataHandling.permutationMap.PermutationMapService;
import console.ConsoleOutput;

public class CipherManager {

    private int[] encipheringWithCipherSegment(Encrypting encryptor, int[] toBeEncrypted, CipherKeySegmentCache p) {

        if (CommonVariables.debug) {
            for (int j = 0; j < p.stepping().length; j++) {
                ConsoleOutput.printLnDebugInfo(
                        "Stepping for rotor " + (j) + ": " + p.stepping()[j] + " | Start Step: " + p.stepStart()[j]);
            }
        }

        return encryptor.calculate(toBeEncrypted, p);

    }

    private int[] cipherCoordinator(int[] toBeEncrypted, boolean decrypt) {

        PermutationMapService pm = new PermutationMapService(new PermutationMapRepository());
        CharacterCodecService cc = new CharacterCodecService(new CharacterCodecRepository());

        Encrypting encryptor = new Encrypting(pm.getPermutationMap(), cc.getCharacterCodecLength());

        if (decrypt == false) {
            CipherKeyCache x = CipherKeyCacheStore.get();
            for (int i = 0; i < x.stepping().length; i++) {
                CipherKeySegmentCache p = new CipherKeySegmentCache(x.deflector()[i], x.stepping()[i], x.stepStart()[i],
                        x.permutationMap()[i], x.conditions()[i], x.conditionReset()[i]);

                toBeEncrypted = encipheringWithCipherSegment(encryptor, toBeEncrypted, p);
            }
        } else {
            CipherKeyCache x = CipherKeyCacheStore.get();
            for (int i = x.stepping().length - 1; i > -1; i--) {
                CipherKeySegmentCache p = new CipherKeySegmentCache(x.deflector()[i], x.stepping()[i], x.stepStart()[i],
                        x.permutationMap()[i], x.conditions()[i], x.conditionReset()[i]);

                toBeEncrypted = encipheringWithCipherSegment(encryptor, toBeEncrypted, p);
            }
        }

        return toBeEncrypted;
    }

    public int[] runCipher(int[] toBeEncrypted, boolean encryptOrDecrypt) {

        try {
            CipherManager p = new CipherManager();

            return p.cipherCoordinator(toBeEncrypted, encryptOrDecrypt);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println();
            System.err.println("Failed to run Cipher");
            return null;
        }

    }

}
