package pl.jtp2.gui.frames.errors;

import pl.jtp2.log.handler.CustomHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class WrongInput extends JPanel {
    private JFrame wrongInputFrame;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public WrongInput() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(WrongInput.class.getName());
        logger.addHandler(handler);

        wrongInputFrame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel errorLabel = new JLabel("Please provide correct input!");
        JButton okButton = new JButton("OK");

        okButton.addActionListener(new OkButtonListener());

        mainPanel.add(BorderLayout.SOUTH, okButton);
        mainPanel.add(BorderLayout.CENTER, errorLabel);

        wrongInputFrame.setContentPane(mainPanel);
        wrongInputFrame.setSize(200,100);
        wrongInputFrame.setLocationRelativeTo(null);
        wrongInputFrame.setVisible(true);

        handler.publish(new LogRecord(Level.INFO, "wronginput frame set"));
    }

    private class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            wrongInputFrame.setVisible(false);
            handler.publish(new LogRecord(Level.INFO, "OK-button in wronginput frame pressed"));
        }
    }
}
