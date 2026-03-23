package cipherCore;

public class CommonVariables {
        // i believe that this class is quite self-explanatory

        public static boolean debug = false;

        public static boolean pictureInGUI = false;

        // used for alphabet related functions
        public static char[] alphabet = {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        ' ', 'e', 'a', 'n', 't', 'r', 's', 'i', 'l', 'E',
                        'A', 'N', 'T', 'R', 'S', 'I', 'L', 'd', 'D', 'o',
                        'O', 'm', 'M', 'k', 'K', '.', 'g', 'G', 'u', 'U',
                        ',', 'h', 'H', 'f', 'F', 'v', 'V', 'ä', 'Ä', 'ö',
                        'Ö', 'å', 'Å', '-', 'p', 'P', 'j', 'J', '!', '?',
                        'y', 'Y', 'c', 'C', '"', 'b', 'B', '\'', ':', ';',
                        '(', ')', '+', '=', '/', '@', '&', '%', '*', '<',
                        '>', '[', ']', '{', '}', '|', '\\', 'q', 'Q', 'w',
                        'W', '$', '_', 'x', 'X', 'z', 'Z', '#', '^', '~'
        };

        public static char[] seedNumberValue = {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        public static int[] rotorSpeedPossibleValues = { 0, 0, 1, 3, 9 };

        public static int switchScheduleAmount = 20;
        public static int alphabetLength = 100;

}
