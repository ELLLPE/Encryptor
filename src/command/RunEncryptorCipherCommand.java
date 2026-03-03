package command;

import java.util.HashMap;
import java.util.Map;

import org.jline.reader.LineReader;

import CipherDataHandling.Seed.SeedRepository;
import CipherDataHandling.Seed.SeedService;
import EncryptionFunctions.InformationProcessing;

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
    private final Map<String, Command> commands = new HashMap<>();

    public RunEncryptorCipherCommand(LineReader reader) {
        this.reader = reader;
    }

    public void execute(String[] args) {
        System.out.println("Enter Text To Encrypt/Decrypt:");
        String input = reader.readLine("> ");

        try {
            SeedRepository repository = new SeedRepository();
            SeedService service = new SeedService(repository);
            System.out.println(
                    InformationProcessing.runEncryptorCipher(service.getCurrentSeed(), input));
        } catch (Exception e) {
            System.err.println("Error running Encryptor cipher: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
