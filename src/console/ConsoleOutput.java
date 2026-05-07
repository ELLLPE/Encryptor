package console;

import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class ConsoleOutput {

    public static String colorize(String message, int color) {

        String text = new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(color))
                .append(message)
                .toAnsi();

        return text;
    }

    public static void print(String message, int color) {
        System.out.print(colorize(message, color));
    }

    public static void printInfo(String message) {
        print(message, AttributedStyle.GREEN);
    }

    public static void printEssentialInfo(String message) {
        print(message, AttributedStyle.BLUE);
    }

    public static void printDebugInfo(String message) {
        print(message, AttributedStyle.MAGENTA);
    }

    public static void printLn(String message, int color) {
        System.out.println(colorize(message, color));
    }

    public static void printLnInfo(String message) {
        printLn(message, AttributedStyle.GREEN);
    }

    public static void printLnDebugInfo(String message) {
        printLn(message, AttributedStyle.MAGENTA);
    }

    public static void printLnError(String message) {
        printLn(message, AttributedStyle.RED);
    }

    public static void printLnWarning(String message) {
        printLn(message, AttributedStyle.YELLOW);
    }

}
