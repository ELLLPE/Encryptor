package cipherDataHandling.characterCodec;

public class CharacterCodecService {
    private final CharacterCodecRepository repository;
    private final CharacterCodec characterCodec;
    private final CharacterCodec unsupportedIndicator;

    public CharacterCodecService(CharacterCodecRepository repository) {
        this.repository = repository;
        this.characterCodec = repository.loadCharacterCodec();
        this.unsupportedIndicator = repository.loadCharacterCodec();
    }

    public String getCharacterCodec() {
        return characterCodec.getCharacterCodec();
    }

    public char getUnsupportedIndicator() {
        return unsupportedIndicator.getUnsupportedIndicator();
    }

    public void setCharacterCodec(String characterCodecIn) {
        characterCodec.setCharacterCodec(characterCodecIn);
        repository.saveCharacterCodec(characterCodec);
    }

}