package console;

import java.util.HashMap;
import java.util.Map;

import org.jline.reader.LineReader;

import command.Command;

public class Console {
    private final LineReader reader;
    private final Map<String, Command> commands = new HashMap<>();

    public Console(LineReader reader) {
        this.reader = reader;
    }

    public void register(Command cmd) {
        commands.put(cmd.name(), cmd);
        commands.put(cmd.secondaryName(), cmd);

    }

    public void start() {
        while (true) {
            String input = reader.readLine("> ").trim();
            if (input.isEmpty())
                continue;

            String[] parts = input.split("\\s+");

            String name = parts[0];
            Command cmd = commands.get(name);

            String[] args = java.util.Arrays.copyOfRange(parts, 1, parts.length);

            if (cmd == null) {
                System.out.println("Okänt kommando: " + name);
                continue;
            }
            System.out.println(cmd.description());
            cmd.execute(args);

        }
    }
}
