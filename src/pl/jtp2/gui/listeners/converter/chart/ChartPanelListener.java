package pl.jtp2.gui.listeners.converter.chart;

import pl.jtp2.chart.ChartCreator;
import pl.jtp2.exceptions.WrongDataException;
import pl.jtp2.gui.frames.converter.chart.ChartPanel;
import pl.jtp2.gui.frames.errors.WrongInput;
import pl.jtp2.gui.listeners.converter.chart.helper.ChartUserInputHelper;
import pl.jtp2.log.handler.CustomHandler;
import pl.jtp2.statistics.UserStatistics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ChartPanelListener implements ActionListener {
    private ChartUserInputHelper chartUserInputHelper;
    private ChartCreator chartCreator;
    private ChartPanel chartPanel;

    private UserStatistics stats = UserStatistics.getInstance();

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public ChartPanelListener(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(ChartPanelListener.class.getName());
        logger.addHandler(handler);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionSource = e.getActionCommand();
        chartCreator = ChartCreator.getInstance();
        chartCreator.setProperties();
        chartUserInputHelper = ChartUserInputHelper.getInstance();

        if(actionSource.equalsIgnoreCase("check data")) {
            handler.publish(new LogRecord(Level.INFO, "checkData button pressed"));
            stats.addAction("check data button; chart panel");
            try {
                if (chartCreator.getStartPoint() > chartCreator.getEndPoint()) {
                    throw new WrongDataException();
                }
            } catch (WrongDataException e1) {
                new WrongInput();
                handler.publish(new LogRecord(Level.WARNING, "startPoint is behind endPoint, wrong user input"));
            }
            chartPanel.createChartButton.setEnabled(true);
            chartPanel.saveToCSVButton.setEnabled(true);
        } else if(actionSource.equalsIgnoreCase("create chart")) {
            stats.addAction("create chart button; chart panel");
            chartCreator.setDataForChart();
            chartCreator.createChart();
            handler.publish(new LogRecord(Level.INFO, "createChart button pressed"));
        } else if(actionSource.equalsIgnoreCase("save to *.csv")) {
            stats.addAction("save to *.csv button; chart panel");
            chartCreator.setDataForChart();
            chartCreator.saveDataToCSV();
            handler.publish(new LogRecord(Level.INFO, "saveData button pressed"));
        } else if(actionSource.equalsIgnoreCase("close")) {
            System.exit(0);
        } else if(actionSource.equalsIgnoreCase("particlecombobox")) {
            stats.addAction("particle combobox; chart panel");
            JComboBox moleculeSelection = (JComboBox) e.getSource();
            chartCreator.setMoleculeMass((String) moleculeSelection.getSelectedItem());
            handler.publish(new LogRecord(Level.INFO, "particle set"));
            chartUserInputHelper.setTmp1(true);
            chartPanel.checkDataButton.setEnabled(chartUserInputHelper.areTrue());
        } else if(actionSource.equalsIgnoreCase("typecombobox")) {
            stats.addAction("type of chart combobox; chart panel");
            JComboBox moleculeSelection = (JComboBox) e.getSource();
            chartCreator.setType((String) moleculeSelection.getSelectedItem());
            handler.publish(new LogRecord(Level.INFO, "type of chart set"));
            chartUserInputHelper.setTmp2(true);
            chartPanel.checkDataButton.setEnabled(chartUserInputHelper.areTrue());
        } else if(actionSource.equalsIgnoreCase("endatfield")) {
            double endAtFieldInput = 0;
            try {
                stats.addAction("end at field; chart panel");
                endAtFieldInput = Double.parseDouble(chartPanel.endAtField.getText());
                if(chartCreator.getType().equalsIgnoreCase("v(e)") && endAtFieldInput > 1) {
                    throw new WrongDataException();
                }
            } catch (Exception exc) {
                handler.publish(new LogRecord(Level.WARNING, "user didn't provide double in endPoint textfield"));
                new WrongInput();
            } catch (WrongDataException e1) {
                handler.publish(new LogRecord(Level.WARNING, "endAtFieldInput over 1"));
                new WrongInput();
            }
            chartCreator.setEndPoint(endAtFieldInput);
            chartUserInputHelper.setTmp3(true);
            chartPanel.checkDataButton.setEnabled(chartUserInputHelper.areTrue());
        } else if(actionSource.equalsIgnoreCase("startfromfield")) {
            try {
                stats.addAction("start from field; chart panel");
                chartCreator.setStartPoint(Double.parseDouble(chartPanel.startFromField.getText()));
            } catch (Exception exc) {
                handler.publish(new LogRecord(Level.WARNING, "user didn't provide double in startPoint textfield"));
                new WrongInput();
            }
            chartUserInputHelper.setTmp4(true);
            chartPanel.checkDataButton.setEnabled(chartUserInputHelper.areTrue());
        }
    }
}
