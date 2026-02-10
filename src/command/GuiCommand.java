package command;

import EncryptionFunctions.Gui;

public class GuiCommand implements Command {
    public String name() {
        return "-g";
    }

    public String description() {
        return "Starts the GUI version of Encryptor";
    }

    public void execute(String[] args) {
        Gui.graphicalInterface();
    }

}
