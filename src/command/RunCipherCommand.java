package command;

import cipherCore.CipherManager;
import cipherCore.SymbolTransformer;

import org.jline.reader.LineReader;

public class RunCipherCommand implements Command {
    private final LineReader reader;

    public RunCipherCommand(LineReader reader) {
        this.reader = reader;
    }

    public String name() {
        return "-rc";
    }

    public String secondaryName() {
        return "--run-cipher";
    }

    public String description() {
        return "Runs Encryptor Cipher";
    }

    public void execute(String[] args) {
        System.out.println("Cipher Condition: either <Encrypt> or <Decrypt>");
        String condition = reader.readLine("|en| or |de| > ");
        boolean isDecrypt = false;
        if (condition.equals("de")) {
            isDecrypt = true;
        }
        System.out.println("Cipher Condition: " + isDecrypt);

        String content = reader.readLine("Input Content: > ");

        CipherManager x = new CipherManager();
        int[][] symbolMap = SymbolTransformer.mapSymbolToIndex(content.toCharArray());

        System.out.println("Running Cipher...");
        System.out.print(SymbolTransformer.mapIndexToSymbol(x.runCipher(symbolMap[0], isDecrypt), symbolMap[1]));
        System.out.println("<:Output");
    }
}
