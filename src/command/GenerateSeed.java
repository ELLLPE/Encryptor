package command;

public class GenerateSeed implements Command {
    public String name() {
        return "-gs";
    }

    public String description() {
        return "Generates a random seed for encryption/decryption";
    }

    public void execute(String[] args) {
        int seed = (int) (Math.random() * 1000000000);
        System.out.println("Generated seed: " + seed);
    }

    @Override
    public String secondaryName() {
        return "--generate-seed";
    }

}
