package pl.jtp2.gui.frames.menu.submenus;

import pl.jtp2.log.handler.CustomHandler;
import pl.jtp2.statistics.CustomOutputStream;
import pl.jtp2.statistics.UserStatistics;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class StatisticsPanel extends JFrame{
    private JTextArea statArea;
    private PrintStream stdOut;

    private JButton buttonStart = new JButton("Show stats");
    private JButton buttonClear = new JButton("Clear");

    private UserStatistics stats = UserStatistics.getInstance();
    private static Logger logger = null;
    private static CustomHandler handler = null;

    public StatisticsPanel() {
        super("User Statistics");

        statArea = new JTextArea(50,10);
        statArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(statArea));

        stdOut = System.out;

        System.setOut(printStream);
        System.setErr(printStream);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        add(buttonStart, constraints);

        constraints.gridx = 1;
        add(buttonClear, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        add(new JScrollPane(statArea), constraints);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                stats.printMap();
            }
        });

        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // clears the text area
                try {
                    statArea.getDocument().remove(0,
                    statArea.getDocument().getLength());
                } catch (BadLocationException ex) {
                    handler.publish(new LogRecord(Level.WARNING, "BadLocationException in Statistics panel : ) " + ex.getMessage()));
                }
            }
        });
        setSize(480, 320);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
