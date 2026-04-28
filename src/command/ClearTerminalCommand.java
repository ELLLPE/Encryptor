package command;

import java.io.IOException;

public class ClearTerminalCommand implements Command {
    public String name() {
        return "-c";
    }

    public String description() {
        return "Clears the terminal";
    }

    public void execute(String[] args) {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String secondaryName() {
        return "--clear";
    }

}
