package command;

import org.jline.reader.LineReader;

import cipherCore.CipherManager;
import cipherCore.SymbolTransformer;

public class RunCipherCommand implements Command {
    public String name() {
        return "-rc";
    }

    public String secondaryName() {
        return "--run-cipher";
    }

    public String description() {
        return "Runs Encryptor Cipher";
    }

    private final LineReader reader;

    public RunCipherCommand(LineReader reader) {
        this.reader = reader;
    }

    public void execute(String[] args) {

        boolean bool = false;

        if (args.length != 0) {
            bool = (args[0].equalsIgnoreCase("decrypt") || args[0].equalsIgnoreCase("1")
                    || args[0].equalsIgnoreCase("yes") || args[0].equalsIgnoreCase("y"));
        } else {
            System.out.println("No decryption argument provided, defaulting to encryption mode.");
        }

        CipherManager x = new CipherManager();

        String input = reader.readLine("Input here -> ");

        int[][] temp = SymbolTransformer.mapSymbolToIndex(input.toCharArray());

        int[] result = x.runCipher(temp[0], bool);

        System.out.println("");
        System.out.println(SymbolTransformer.mapIndexToSymbol(result, temp[1]));

    }
}
