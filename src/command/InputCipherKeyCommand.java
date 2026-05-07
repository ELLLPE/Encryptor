package command;

import java.io.Console;

import org.jline.reader.LineReader;
import org.jline.utils.AttributedStyle;

import cipherCore.cipherKeyProcessing.CipherKeyProcessing;
import console.ConsoleOutput;

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

    public void execute(String[] args) {

        try {
            String cipherKey = reader
                    .readLine(ConsoleOutput.colorize("Input Cipher Key: ", AttributedStyle.YELLOW) + "> ");

            try {
                CipherKeyProcessing x = new CipherKeyProcessing();

                x.runCipherKeyReader(cipherKey);
            } catch (Exception e) {
                ConsoleOutput.printLnError("An error occurred while processing the cipher key: ");
            }
        } catch (Exception e) {
            ConsoleOutput.printLnError("An error occurred: ");
        }

    }

}
