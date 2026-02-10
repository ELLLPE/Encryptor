package command;

public interface Command {
    String name();

    String secondaryName();

    String description();

    void execute(String[] args);
}
