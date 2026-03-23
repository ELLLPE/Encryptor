package CipherDataHandling.characterCodec;

public class CharacterCodec {

    private char[] characterCodec;
    private char unsupportedCharactersReplacement;

    public CharacterCodec(char[] characterCodec, char unsupportedCharactersReplacement) {
        this.characterCodec = characterCodec;
        this.unsupportedCharactersReplacement = unsupportedCharactersReplacement;
    }

    public char[] getCharacterCodec() {
        return characterCodec;
    }

    public char getUnsupportedCharactersReplacement() {
        return unsupportedCharactersReplacement;
    }
}
