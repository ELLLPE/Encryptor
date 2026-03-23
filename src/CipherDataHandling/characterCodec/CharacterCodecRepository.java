package CipherDataHandling.characterCodec;

import CipherDataHandling.Core.SaveManager;

public class CharacterCodecRepository {

    private static final String PATH = "src/CipherData/characterCodec.json";

    public CharacterCodec loadCharacterCodec() {
        CharacterCodec characterCodec = SaveManager.load(PATH, CharacterCodec.class);
        if (characterCodec == null) {
            throw new RuntimeException("No character codec found at path: " + PATH);
        }
        return characterCodec;
    }

}
