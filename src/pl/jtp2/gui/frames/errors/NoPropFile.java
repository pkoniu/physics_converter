package pl.jtp2.gui.frames.errors;

import pl.jtp2.log.handler.CustomHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class NoPropFile extends JFrame {
    private JFrame noPropFileFrame;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public NoPropFile() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(NoPropFile.class.getName());
        logger.addHandler(handler);

        noPropFileFrame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel errorLabel = new JLabel("No data.properties file found! Please provide it in lib folder");
        JButton okButton = new JButton("OK");

        okButton.addActionListener(new OkButtonListener());

        mainPanel.add(BorderLayout.SOUTH, okButton);
        mainPanel.add(BorderLayout.CENTER, errorLabel);

        noPropFileFrame.setContentPane(mainPanel);
        noPropFileFrame.setSize(200, 100);
        noPropFileFrame.setLocationRelativeTo(null);
        noPropFileFrame.setVisible(true);

        handler.publish(new LogRecord(Level.INFO, "nopropfile frame set"));
    }

    private class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            noPropFileFrame.setVisible(false);
            handler.publish(new LogRecord(Level.INFO, "OK-button in nopropfile frame pressed"));
        }
    }
}
