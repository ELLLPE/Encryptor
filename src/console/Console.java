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

    private int layeredCommands(String[] args) {
        int o = 0;
        int i = 0;
        while (o < args.length) {
            if (args[o].startsWith("-")) {
                System.out.println("Found layered command: " + args[o]);
                i++;
            }
            o++;
        }
        return i;

    }

    public void start() {
        while (true) {
            String input = reader.readLine("> ").trim();
            if (input.isEmpty())
                continue;

            String[] parts = input.split("\\s+");
            String[] args = java.util.Arrays.copyOfRange(parts, 1, parts.length);

            String name = parts[0];
            Command cmd = commands.get(name);

            if (cmd == null) {
                System.out.println("Unknown Command: " + name);
                continue;
            }

            System.out.println(cmd.description());
            cmd.execute(args);

        }
    }
}
