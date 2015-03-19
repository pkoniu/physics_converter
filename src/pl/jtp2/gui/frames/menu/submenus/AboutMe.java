package pl.jtp2.gui.frames.menu.submenus;

import pl.jtp2.log.handler.CustomHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AboutMe extends JFrame{
    private JFrame aboutMeFrame;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public AboutMe() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(AboutMe.class.getName());
        logger.addHandler(handler);

        aboutMeFrame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        JPanel infoPanel = new JPanel();
        //infoPanel.setLayout(new GridLayout(1, 4, 5, 5));
        JLabel nameLabel = new JLabel("Author: Patryk Konior");
        JButton okButton = new JButton("OK");

        okButton.addActionListener(new OkButtonListener());

        mainPanel.add(BorderLayout.SOUTH, okButton);
        mainPanel.add(BorderLayout.CENTER, nameLabel);

        aboutMeFrame.setContentPane(mainPanel);
        aboutMeFrame.setSize(200, 100);
        aboutMeFrame.setLocationRelativeTo(null);
        aboutMeFrame.setVisible(true);

        handler.publish(new LogRecord(Level.INFO, "aboutme frame set"));
    }

    private class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            aboutMeFrame.setVisible(false);
            handler.publish(new LogRecord(Level.INFO, "OK-button in aboutMe frame pressed"));
        }
    }
    //todo: feedback about the app, email library
}
