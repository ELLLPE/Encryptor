package command;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        FileWriter writer;
        try {

            Gson gson = new Gson();

            Map<String, String> seedMap = new HashMap<>();
            seedMap.put("seed", args[0]);

            writer = new FileWriter("src/data/ciphersettings.json");
            gson.toJson(seedMap, writer);

            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public String secondaryName() {
        return "--seed";
    }

}
