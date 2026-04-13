package cipherDataHandling.characterCodec;

public class CharacterCodec {
    private String characterCodec;
    private char unsupportedIndicator;

    public CharacterCodec(String characterCodec, char unsupportedIndicator) {
        this.characterCodec = characterCodec;
        this.unsupportedIndicator = unsupportedIndicator;
    }

    public String getCharacterCodec() {
        return characterCodec;
    }

    public char getUnsupportedIndicator() {
        return unsupportedIndicator;
    }

    public void setCharacterCodec(String characterCodec) {
        this.characterCodec = characterCodec;
    }
}
