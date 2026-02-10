package command;

public class GenerateSeed implements Command {
    public String name() {
        return "-gs";
    }

    public String description() {
        return "Generates a random seed for encryption/decryption";
    }

    public void execute(String[] args) {

    }

    @Override
    public String secondaryName() {
        return "--generate-seed";
    }

}
