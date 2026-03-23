
import console.Console;
import java.io.IOException;

import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

public class Main {

    static Highlighter greenHighlighter = new Highlighter() {
        @Override
        public AttributedString highlight(LineReader reader, String buffer) {
            // Apply green color to the entire buffer
            return new AttributedString(buffer, AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
        }
    };

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder().name("Encryptor Terminal")
                .encoding(java.nio.charset.Charset.forName("UTF-8")).build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).highlighter(greenHighlighter).build();

        Console console = new Console(reader);

        console.register(new command.ExitCommand());
        console.register(new command.GuiCommand());
        console.register(new command.SeedCommand());
        console.register(new command.GenerateSeedCommand());
        console.register(new command.ClearTerminalCommand());
        console.register(new command.RunEncryptorCipherCommand(reader));
        console.register(new command.OperationalStatusCommand());

        console.start();
    }
}