package pl.jtp2.gui.listeners.menu;

import pl.jtp2.gui.frames.converter.chart.ChartPanel;
import pl.jtp2.gui.frames.converter.ETVPanel;
import pl.jtp2.gui.frames.converter.VTEPanel;
import pl.jtp2.gui.frames.menu.MainFrame;
import pl.jtp2.gui.frames.menu.submenus.AboutMe;
import pl.jtp2.gui.frames.menu.submenus.LogPanel;
import pl.jtp2.gui.frames.menu.submenus.StatisticsPanel;
import pl.jtp2.statistics.UserStatistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemListener implements ActionListener{
    private MainFrame jFrame;
    private UserStatistics stats = UserStatistics.getInstance();
    public MenuItemListener(MainFrame jFrame) {
        this.jFrame = jFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(cmd.equals("velocity > energy")) {
            stats.addAction("vte panel");
            reload(new VTEPanel());
        } else if(cmd.equals("energy > velocity")) {
            stats.addAction("etv panel");
            reload(new ETVPanel());
        } else if(cmd.equals("chart")) {
            stats.addAction("chart panel");
            reload(new ChartPanel());
        } else if(cmd.equalsIgnoreCase("exit")) {
            System.exit(0);
        } else if(cmd.equalsIgnoreCase("about")) {
            stats.addAction("about panel");
            new AboutMe();
        } else if(cmd.equalsIgnoreCase("showlog")) {
            stats.addAction("showlog panel");
            LogPanel logPanelInstance = LogPanel.getInstance();
            logPanelInstance.showLogPanel();
        } else if(cmd.equalsIgnoreCase("statistics")) {
            stats.addAction("stats panel");
            new StatisticsPanel();
        } else if(cmd.equals("exit")) {
            System.exit(0);
        }
    }

    private void reload(Component c) {
        JPanel jPanel = (JPanel) jFrame.getContentPane();
        jPanel.removeAll();
        jPanel.add(c);
        jFrame.setContentPane(jPanel);
        jPanel.revalidate();
        jPanel.repaint();
        jFrame.jPanel = jPanel;
    }
}
