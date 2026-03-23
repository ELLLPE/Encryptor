package command;

import org.jline.reader.LineReader;
import cipherCore.InformationProcessing;

public class RunEncryptorCipherCommand implements Command {
    public String name() {
        return "-rec";
    }

    public String secondaryName() {
        return "--run-encryptor-cipher";
    }

    public String description() {
        return "Runs the Encryptor cipher with the current seed";
    }

    private final LineReader reader;

    public RunEncryptorCipherCommand(LineReader reader) {
        this.reader = reader;
    }

    public void execute(String[] args) {
        System.out.println("Enter Text To Encrypt/Decrypt:");
        String input = reader.readLine("> ");

        String result = InformationProcessing.runEncryptorCipherWithJsonFileSeed(input);
        if (result != null) {
            System.out.println("Result: " + result);
        }
    }
}
