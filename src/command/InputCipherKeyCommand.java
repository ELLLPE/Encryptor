package command;

import org.jline.reader.LineReader;

import CipherData.CipherKeyCache;
import cipherCore.cipherKeyProcessing.CipherKeyProcessing;

public class InputCipherKeyCommand implements Command {

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

        CipherKeyProcessing x = new CipherKeyProcessing();

        String input = reader.readLine("Input here -> ");

        x.runCipherKeyReader(input);

    }

}
