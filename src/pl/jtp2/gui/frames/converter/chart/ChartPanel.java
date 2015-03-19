package pl.jtp2.gui.frames.converter.chart;

import pl.jtp2.gui.listeners.converter.chart.ChartPanelListener;
import pl.jtp2.log.handler.CustomHandler;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ChartPanel extends JPanel {
    public JComboBox<String> particleComboBox;
    public JComboBox<String> typeComboBox;

    public JTextField startFromField;
    public JTextField endAtField;

    public JButton checkDataButton;
    public JButton createChartButton;
    public JButton saveToCSVButton;
    private JButton closeButton;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public ChartPanel() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(ChartPanel.class.getName());
        logger.addHandler(handler);

        setLayout(new BorderLayout(20, 20));
        setPreferredSize(new Dimension(550, 200));

        // initialize components
        endAtField = new JTextField();
        endAtField.setPreferredSize(new Dimension(300, 30));
        endAtField.setActionCommand("endatfield");

        startFromField = new JTextField();
        startFromField.setPreferredSize(new Dimension(300, 30));
        startFromField.setActionCommand("startfromfield");

        checkDataButton = new JButton("Check data");
        createChartButton = new JButton("Create chart");
        saveToCSVButton = new JButton("Save to *.csv");
        closeButton = new JButton("Close");

        checkDataButton.setEnabled(false);
        createChartButton.setEnabled(false);
        saveToCSVButton.setEnabled(false);

        String[] particles = {"<choose particle>", "electron", "proton", "helium", "carbon", "lithium"};
        particleComboBox = new JComboBox<String>(particles);
        particleComboBox.setPreferredSize(new Dimension(150, 30));
        particleComboBox.setSelectedIndex(0);
        particleComboBox.setActionCommand("particleComboBox");

        String[] types = {"<choose chart type>", "E(V)", "V(E)"};
        typeComboBox = new JComboBox<String>(types);
        typeComboBox.setPreferredSize(new Dimension(150, 30));
        typeComboBox.setSelectedIndex(0);
        typeComboBox.setActionCommand("typeComboBox");

        // top
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());
        top.add(new JLabel("Chart creator. Provide correct input (confirm with [enter]) and press create chart."));

        // center
        JLabel start = new JLabel("Start from: ");
        JLabel end = new JLabel("End at: ");
        start.setPreferredSize(new Dimension(70, 30));
        end.setPreferredSize(new Dimension(70, 30));
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());
        center.add(start);
        center.add(startFromField);
        center.add(particleComboBox);
        center.add(end);
        center.add(endAtField);
        center.add(typeComboBox);

        // bottom
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 4, 5, 5));
        bottom.add(checkDataButton);
        bottom.add(createChartButton);
        bottom.add(saveToCSVButton);
        bottom.add(closeButton);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        endAtField.addActionListener(new ChartPanelListener(this));
        startFromField.addActionListener(new ChartPanelListener(this));
        checkDataButton.addActionListener(new ChartPanelListener(this));
        createChartButton.addActionListener(new ChartPanelListener(this));
        saveToCSVButton.addActionListener(new ChartPanelListener(this));
        closeButton.addActionListener(new ChartPanelListener(this));
        typeComboBox.addActionListener(new ChartPanelListener(this));
        particleComboBox.addActionListener(new ChartPanelListener(this));

        handler.publish(new LogRecord(Level.INFO, "ChartPanel set"));
    }
}
