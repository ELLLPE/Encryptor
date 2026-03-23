package command;

import CipherDataHandling.Seed.SeedRepository;
import CipherDataHandling.Seed.SeedService;
import cipherCore.InformationProcessing;

public class SeedCommand implements Command {
    public String name() {
        return "-s";
    }

    public String description() {
        return "Sets the seed for encryption/decryption (must be a number)";
    }

    public void execute(String[] args) {
        try {

            if (!args[1].matches("[0-9-]+")) {
                throw new IllegalArgumentException("Invalid characters in seed.");
            }

            SeedRepository repository = new SeedRepository();
            SeedService service = new SeedService(repository);

            int[][] seedData = InformationProcessing.seedDecode(args[1]);

            service.updateSeed(args[0], args[1], (args[1].length() - 1), seedData[0], seedData[1], seedData[2]);
            System.out.println("Seed updated successfully.");

        } catch (Exception e) {
            System.err.println("Error updating seed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String secondaryName() {
        return "--seed";
    }

}
