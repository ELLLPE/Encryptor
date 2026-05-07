package command;

import java.io.Console;

import org.jline.utils.AttributedStyle;

import cipherCore.cipherKeyProcessing.CipherKeyAssembler;
import cipherCore.cipherKeyProcessing.CipherKeyGenerator;
import console.ConsoleOutput;

public class OperationalStatusCommand implements Command {
    public String name() {
        return "-os";
    }

    public String secondaryName() {
        return "--operational-status";
    }

    public String description() {
        return "Displays the current operational status of Encryptor";
    }

    public void execute(String[] args) {
        ConsoleOutput.printLnInfo("Encryptor is operational and ready to use.");
    }
}
