package command;

import org.jline.reader.LineReader;

import cipherCore.cipherKeyProcessing.CipherKeyProcessing;

public class InputCipherKeyCommand implements Command {
    private final LineReader reader;

    public InputCipherKeyCommand(LineReader reader) {
        this.reader = reader;
    }

    public String name() {
        return "-ic";
    }

    public String secondaryName() {
        return "--input-cipher-key";
    }

    public String description() {
        return "Used for inputting CipherKeys";
    }

    private final LineReader reader;

    public InputCipherKeyCommand(LineReader reader) {
        this.reader = reader;
    }

    public void execute(String[] args) {

        String cipherKey = reader.readLine("Input CipherKey: > ");

        CipherKeyProcessing x = new CipherKeyProcessing();

        x.runCipherKeyReader(cipherKey);

    }

}
