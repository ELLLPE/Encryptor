package command;

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

    public void execute(String[] args) {

    }
}
