package command;

import cipherCore.CipherManager;
import cipherCore.SymbolTransformer;
import console.ConsoleOutput;

import org.jline.reader.LineReader;
import org.jline.utils.AttributedStyle;

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
        try {
            ConsoleOutput.printLnInfo("Cipher Condition: either <Encrypt> or <Decrypt>");

            String condition = reader.readLine(ConsoleOutput.colorize("|en| or |de| ", AttributedStyle.YELLOW) + "> ")
                    .trim().toLowerCase();
            boolean isDecrypt = false;
            if (condition.equals("de")) {
                isDecrypt = true;
            }
            ConsoleOutput.printLnInfo("Cipher Condition: " + isDecrypt);

            try {
                String content = reader
                        .readLine(ConsoleOutput.colorize("Input Content: ", AttributedStyle.YELLOW) + "> ");

                ConsoleOutput.printLnInfo("Content: " + content);

                CipherManager x = new CipherManager();
                int[][] symbolMap = SymbolTransformer.mapSymbolToIndex(content.toCharArray());

                ConsoleOutput.printLnInfo("Running Cipher...");
                ConsoleOutput.printEssentialInfo(
                        SymbolTransformer.mapIndexToSymbol(x.runCipher(symbolMap[0], isDecrypt), symbolMap[1]));
                ConsoleOutput.printLnInfo("<End>");
            } catch (Exception e) {
                ConsoleOutput.printLnError("An error occurred while running the cipher: ");
            }
        } catch (Exception e) {
            ConsoleOutput.printLnError("An error occurred: ");
        }
    }
}
