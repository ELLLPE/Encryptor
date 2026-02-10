package command;

public class ExitCommand implements Command {
    public String name() {
        return "-q";
    }

    public String description() {
        return "Closes the terminal";
    }

    public void execute(String[] args) {
        System.exit(0);
    }
}