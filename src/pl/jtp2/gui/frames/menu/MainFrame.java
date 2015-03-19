package pl.jtp2.gui.frames.menu;

import pl.jtp2.gui.listeners.menu.MenuItemListener;
import pl.jtp2.log.handler.CustomHandler;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MainFrame extends JFrame {
    public JPanel jPanel;

    private Logger logger = Logger.getLogger(MainFrame.class.getName());
    private static CustomHandler handler = CustomHandler.getInstance();

    public MainFrame() {
        super("Basic physics converter");

        logger.addHandler(handler);

        setLayout(new BorderLayout());
        jPanel = new JPanel();

        createMenu();

        pack();
        setContentPane(jPanel);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        handler.publish(new LogRecord(Level.INFO, "mainframe set"));
    }

    private void createMenu() {
        JMenuBar jMenuBar = new JMenuBar();

        //======file menu========
        JMenu file = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(new MenuItemListener(this));
        file.add(exit);
        //=======================

        //=====converter menu======
        JMenu converter = new JMenu("Converter");

        JMenuItem energyToVelocity = new JMenuItem("energy > velocity");
        JMenuItem velocityToEnergy = new JMenuItem("velocity > energy");
        JMenuItem chart = new JMenuItem("chart");

        energyToVelocity.addActionListener(new MenuItemListener(this));
        velocityToEnergy.addActionListener(new MenuItemListener(this));
        chart.addActionListener(new MenuItemListener(this));

        converter.add(energyToVelocity);
        converter.add(velocityToEnergy);
        converter.add(chart);
        //========================


        //========debug menu=======
        JMenu debug = new JMenu("Debug");

        JMenuItem showLog = new JMenuItem("ShowLog");
        JMenuItem stats = new JMenuItem("Statistics");

        showLog.addActionListener(new MenuItemListener(this));
        stats.addActionListener(new MenuItemListener(this));

        debug.add(showLog);
        debug.add(stats);
        //=========================

        //=======help menu=========
        JMenu help = new JMenu("Help");

        JMenuItem about = new JMenuItem("About");

        about.addActionListener(new MenuItemListener(this));

        help.add(about);
        //=========================
        jMenuBar.add(file);
        jMenuBar.add(converter);
        jMenuBar.add(debug);
        jMenuBar.add(help);
        this.setJMenuBar(jMenuBar);

        handler.publish(new LogRecord(Level.INFO, "menubar set"));
    }
}
