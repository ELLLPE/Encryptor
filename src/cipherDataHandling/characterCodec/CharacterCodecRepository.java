package cipherDataHandling.characterCodec;

import cipherDataHandling.Core.SaveManager;

public class CharacterCodecRepository {

    private static final String PATH = "src/CipherData/characterCodec.json";

    public CharacterCodec loadCharacterCodec() {
        CharacterCodec characterCodec = SaveManager.load(PATH, CharacterCodec.class);
        if (characterCodec == null) {
            throw new RuntimeException("No CharacterCodec found at path: " + PATH);
        }
        return characterCodec;
    }

    public void saveCharacterCodec(CharacterCodec characterCodec) {
        SaveManager.save(PATH, characterCodec);
    }
}
