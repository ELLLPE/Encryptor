package CipherDataHandling.characterCodec;

public class CharacterCodecService {

    private final CharacterCodecRepository repository;
    private CharacterCodec characterCodec;
    private CharacterCodec unsupportedCharactersReplacement;

    public CharacterCodecService(CharacterCodecRepository repository) {
        this.repository = repository;
        this.characterCodec = repository.loadCharacterCodec();
        this.unsupportedCharactersReplacement = repository.loadCharacterCodec();
    }

    public char[] getCharacterCodec() {
        return characterCodec.getCharacterCodec(); // If it works, it works
    }

    public char getUnsupportedCharactersReplacement() {
        return unsupportedCharactersReplacement.getUnsupportedCharactersReplacement();
    }

}
