package command;

import EncryptionFunctions.CommonVariables;

public class SeedCommand implements Command {
    public String name() {
        return "-s";
    }

    public String description() {
        return "Sets the seed for encryption/decryption (must be a number)";
    }

    public void execute(String[] args) {
        if (!args[0].matches("[0-9-]+")) {
            throw new IllegalArgumentException("Invalid characters in seed.");
        }

    }

}
