package pl.jtp2.main;

import pl.jtp2.gui.frames.errors.NoPropFile;
import pl.jtp2.gui.frames.menu.MainFrame;
import pl.jtp2.log.handler.CustomHandler;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        CustomHandler handler = CustomHandler.getInstance();
        logger.addHandler(handler);
        handler.publish(new LogRecord(Level.INFO, "Starting the program."));

        File propFile = new File("lib/data.properties");
        if(!propFile.exists()) {
            new NoPropFile();
        } else {
            new MainFrame();
        }
    }
}
