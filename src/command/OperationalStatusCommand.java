package command;

import cipherCore.cipherKeyProcessing.CipherKeyAssembler;
import cipherCore.cipherKeyProcessing.CipherKeyGenerator;

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
        System.out.println("Encryptor is operational and ready to use.");

        String x = CipherKeyAssembler.cipherKeyAssembly(CipherKeyGenerator.generateWithSeed(1335346477756400083L));

        System.out.println("Cipher Key -> : " + x);

    }
}
