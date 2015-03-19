package pl.jtp2.gui.frames.menu.submenus;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class LogPanel extends JPanel{ //singleton
    private static volatile LogPanel logPanelInstance = null;

    private JFrame logFrame;
    private JTextArea logArea;
    private final static String crlf = "\n";

    public static LogPanel getInstance() {
        if(logPanelInstance == null) {
            synchronized (LogPanel.class) {
                if(logPanelInstance == null) {
                    logPanelInstance = new LogPanel();
                }
            }
        }
        return logPanelInstance;
    }

    private LogPanel() {
        JPanel logPanel = new JPanel();
        logPanel.setBorder(new TitledBorder(new EtchedBorder(), "Program log"));
        logArea = new JTextArea(20,20);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        logPanel.add(logScrollPane);

        logFrame = new JFrame("Logs");
        logFrame.add(logPanel);
        logFrame.pack();
        logFrame.setLocationRelativeTo(null);
        logFrame.setVisible(false);
    }

    public void showLogPanel() {
        logFrame.setVisible(true);
    }

    public void appendNewLog(String log) {
        logArea.append(log + crlf);
    }
}
