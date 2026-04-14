package cipherCore;

import CipherData.CipherKeyCache;
import CipherData.CipherKeySegmentCache;
import cipherDataHandling.characterCodec.CharacterCodecRepository;
import cipherDataHandling.characterCodec.CharacterCodecService;
import cipherDataHandling.permutationMap.PermutationMapRepository;
import cipherDataHandling.permutationMap.PermutationMapService;

public class CipherManager {

    private static int[] runEncryptorCipherSegment(Encrypting encryptor, int[] toBeEncrypted, CipherKeySegmentCache p) {

        return encryptor.calculate(toBeEncrypted, p);

    }

    public static int[] runEncryptorCipher(CipherKeyCache x, int[] toBeEncrypted, boolean decrypt) {

        PermutationMapService pm = new PermutationMapService(new PermutationMapRepository());
        CharacterCodecService cc = new CharacterCodecService(new CharacterCodecRepository());

        Encrypting encryptor = new Encrypting(pm.getPermutationMap(), cc.getCharacterCodecLength());

        if (decrypt == false) {
            System.out.println("Encrypterar");
            for (int i = 0; i < x.stepping().length; i++) {
                CipherKeySegmentCache p = new CipherKeySegmentCache(x.deflector()[i], x.stepping()[i], x.stepStart()[i],
                        x.permutationMap()[i], x.conditions()[i], x.conditionReset()[i]);
                toBeEncrypted = runEncryptorCipherSegment(encryptor, toBeEncrypted, p);
            }
        } else {
            System.out.println("Decrypterar");
            for (int i = x.stepping().length - 1; i > -1; i--) {
                CipherKeySegmentCache p = new CipherKeySegmentCache(x.deflector()[i], x.stepping()[i], x.stepStart()[i],
                        x.permutationMap()[i], x.conditions()[i], x.conditionReset()[i]);
                toBeEncrypted = runEncryptorCipherSegment(encryptor, toBeEncrypted, p);
            }
        }

        return toBeEncrypted;
    }

}
