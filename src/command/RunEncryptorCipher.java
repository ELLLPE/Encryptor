package command;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;

import EncryptionFunctions.CommonVariables;
import EncryptionFunctions.Encrypting;
import EncryptionFunctions.InformationProcessing;

public class RunEncryptorCipher implements Command {
    public String name() {
        return "-rec";
    }

    public String secondaryName() {
        return "--run-encryptor-cipher";
    }

    public String description() {
        return "Runs the Encryptor cipher with the current seed";
    }

    public void execute(String[] args) {
        System.out.println("Running Encryptor cipher with the current seed...");
        // Placeholder for actual encryption/decryption logic
        Gson gson = new Gson();
        FileReader reader;
        try {
            reader = new FileReader("src/data/ciphersettings.json");
            Map loaded = gson.fromJson(reader, Map.class);
            System.out.println("Loaded data: " + loaded);

            System.out.println("Output text: "
                    + InformationProcessing.runEncryptorCipher(
                            InformationProcessing.seedDecode(loaded.get("seed").toString()), "0^FS?N~g~tTvDt2XC%F"));

            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
