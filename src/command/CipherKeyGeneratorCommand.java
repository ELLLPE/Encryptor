package command;

import cipherCore.cipherKeyProcessing.CipherKeyAssembler;
import cipherCore.cipherKeyProcessing.CipherKeyGenerator;
import console.ConsoleOutput;

public class CipherKeyGeneratorCommand implements Command {
    public String name() {
        return "-cg";
    }

    public String secondaryName() {
        return "--cipher-key-generator";
    }

    public String description() {
        return "Generates a new cipher key for Encryptor";
    }

    public void execute(String[] args) {
        ConsoleOutput.printLnInfo("Encryptor is operational and ready to use.");

        String x = CipherKeyAssembler.cipherKeyAssembly(CipherKeyGenerator.generateWithSeed(1335L));

        ConsoleOutput.printInfo("Generated Cipher Key:");
        ConsoleOutput.printEssentialInfo(x);
        ConsoleOutput.printLnInfo("|End|");

        // 1335346477756400083L : remove later
    }
}