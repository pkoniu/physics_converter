package pl.jtp2.log.handler;

import pl.jtp2.gui.frames.menu.submenus.LogPanel;

import java.util.logging.*;


public class CustomHandler extends Handler {
    private LogPanel logPanelInstance = null;
    private Formatter formatter = null;
    private Level level = null;

    private static volatile CustomHandler customHandlerInstance = null;

    private CustomHandler() {
        configure();
        logPanelInstance = LogPanel.getInstance();
    }

    public static CustomHandler getInstance() {
        if(customHandlerInstance == null) {
            synchronized (CustomHandler.class) {
                if(customHandlerInstance == null) {
                    customHandlerInstance = new CustomHandler();
                }
            }
        }
        return customHandlerInstance;
    }

    private void configure() {
        setFormatter(new SimpleFormatter());
    }

    public synchronized void publish(LogRecord record) {
        String msg = null;
        msg = getFormatter().format(record);
        logPanelInstance.appendNewLog(msg);
    }

    public void flush() {

    }

    public void close() {

    }
}
