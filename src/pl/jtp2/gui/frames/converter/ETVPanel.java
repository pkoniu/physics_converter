package pl.jtp2.gui.frames.converter;

import pl.jtp2.gui.listeners.converter.ETVPanelListener;
import pl.jtp2.log.handler.CustomHandler;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ETVPanel extends JPanel {
    public JComboBox<String> energyComboBox;
    public JComboBox<String> particleComboBox;

    public JTextField energyTextField;
    public JTextField velocityTextField;

    public JButton convert;
    public JButton save;
    public JButton reset;
    public JButton close;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public ETVPanel() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(ETVPanel.class.getName());
        logger.addHandler(handler);

        setLayout(new BorderLayout(20, 20));
        setPreferredSize(new Dimension(550, 200));

        // initialize components
        velocityTextField = new JTextField();
        velocityTextField.setPreferredSize(new Dimension(500, 30));
        velocityTextField.setActionCommand("velocityTextField");

        energyTextField = new JTextField();
        energyTextField.setPreferredSize(new Dimension(500, 30));
        energyTextField.setActionCommand("energyTextField");

        convert = new JButton("Convert");
        save = new JButton("Save");
        reset = new JButton("Reset");
        close = new JButton("close");

        // top
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(2, 1, 5, 5));
        top.add(new JLabel("Energy to velocity converter. Provide correct input (confirm with [enter]) and press convert."));
        top.add(getComboPanel());

        // center
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());
        center.add(new JLabel("K ="));
        center.add(energyTextField);
        center.add(new JLabel("V ="));
        center.add(velocityTextField);

        // bottom
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 4, 5, 5));
        bottom.add(convert);
        bottom.add(save);
        bottom.add(reset);
        bottom.add(close);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        convert.addActionListener(new ETVPanelListener(this));
        save.addActionListener(new ETVPanelListener(this));
        reset.addActionListener(new ETVPanelListener(this));
        close.addActionListener(new ETVPanelListener(this));
        energyTextField.addActionListener(new ETVPanelListener(this));
        particleComboBox.addActionListener(new ETVPanelListener(this));
        energyComboBox.addActionListener(new ETVPanelListener(this));

        handler.publish(new LogRecord(Level.INFO, "ETV panel set"));
    }

    private JPanel getComboPanel() {
        // create combo boxes
        String[] particles = {"<choose particle>", "electron", "proton", "helium", "carbon", "lithium"};
        particleComboBox = new JComboBox<String>(particles);
        particleComboBox.setSelectedIndex(0);
        particleComboBox.setActionCommand("particleComboBox");

        String[] energyUnits = {"<choose energy unit>", "MeV", "keV", "eV"};
        energyComboBox = new JComboBox<String>(energyUnits);
        energyComboBox.setSelectedIndex(0);
        energyComboBox.setActionCommand("energyComboBox");

        // panel with combos
        JPanel topCombos = new JPanel();
        topCombos.setLayout(new GridLayout(1, 1, 5, 5));
        topCombos.add(particleComboBox);
        topCombos.add(energyComboBox);
        handler.publish(new LogRecord(Level.INFO, "combopanel in etv panel set"));
        return topCombos;
    }

}
