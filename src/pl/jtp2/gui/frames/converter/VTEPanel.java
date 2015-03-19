package pl.jtp2.gui.frames.converter;

import pl.jtp2.gui.listeners.converter.VTEPanelListener;
import pl.jtp2.log.handler.CustomHandler;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class VTEPanel extends JPanel{
    public JComboBox<String> particleComboBox;

    public JTextField energyTextField;
    public JTextField velocityTextField;

    public JButton convert;
    public JButton save;
    public JButton reset;
    public JButton close;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public VTEPanel() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(VTEPanel.class.getName());
        logger.addHandler(handler);

        setLayout(new BorderLayout(20, 20));
        setPreferredSize(new Dimension(550, 200));

        // initialize components
        velocityTextField = new JTextField();
        velocityTextField.setPreferredSize(new Dimension(500, 30));

        energyTextField = new JTextField();
        energyTextField.setPreferredSize(new Dimension(500, 30));
        energyTextField.setActionCommand("energytextfield");

        convert = new JButton("Convert");
        save = new JButton("Save");
        reset = new JButton("Reset");
        close = new JButton("close");

        // action listeners
        this.convert.addActionListener(new VTEPanelListener(this));

        // top
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(2, 1, 5, 5));
        top.add(new JLabel("Velocity to energy converter. Please provide correct input (confirm with [enter]) and press convert."));
        top.add(getComboPanel());

        // center
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());
        center.add(new JLabel("V ="));
        center.add(energyTextField);
        center.add(new JLabel("K ="));
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

        particleComboBox.addActionListener(new VTEPanelListener(this));
        energyTextField.addActionListener(new VTEPanelListener(this));
        convert.addActionListener(new VTEPanelListener(this));
        save.addActionListener(new VTEPanelListener(this));
        reset.addActionListener(new VTEPanelListener(this));
        close.addActionListener(new VTEPanelListener(this));

        handler.publish(new LogRecord(Level.INFO, "vte panel set"));
    }

    private JPanel getComboPanel() {
        // create combo boxes
        String[] particles = {"<choose particle>", "electron", "proton", "helium", "carbon", "lithium"};
        particleComboBox = new JComboBox<String>(particles);
        particleComboBox.setSelectedIndex(0);
        particleComboBox.setActionCommand("particleComboBox");

        // panel with combos
        JPanel top_combos = new JPanel();
        top_combos.setLayout(new GridLayout(1, 2, 5, 5));
        top_combos.add(particleComboBox);

        handler.publish(new LogRecord(Level.INFO, "combopanel in vtepanel set"));
        return top_combos;
    }
}
