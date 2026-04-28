package command;

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

    public void execute(String[] args) {

        CipherKeyProcessing x = new CipherKeyProcessing();

        x.runCipherKeyReader(args[0]);

    }

}
