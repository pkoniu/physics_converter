package pl.jtp2.gui.listeners.converter;

import pl.jtp2.converter.specified.EnergyConverter;
import pl.jtp2.gui.frames.converter.ETVPanel;
import pl.jtp2.gui.frames.errors.WrongInput;
import pl.jtp2.log.handler.CustomHandler;
import pl.jtp2.statistics.UserStatistics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class ETVPanelListener implements ActionListener{
    private EnergyConverter eConv;
    private ETVPanel jframe;
    private UserStatistics stats = UserStatistics.getInstance();

    private double energy;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public ETVPanelListener(ETVPanel jframe) {
        this.jframe = jframe;
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(ETVPanelListener.class.getName());
        logger.addHandler(handler);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionSource = e.getActionCommand();
        eConv = EnergyConverter.getInstance();
        eConv.setProperties();

        if (actionSource.equalsIgnoreCase("particlecombobox")) {
            stats.addAction("particle combobox; etv panel");
            JComboBox moleculeSelection = (JComboBox) e.getSource();
            eConv.setMolecule((String) moleculeSelection.getSelectedItem());
            eConv.setMoleculeMass(eConv.getMolecule());
            jframe.reset.setEnabled(true);
            handler.publish(new LogRecord(Level.INFO, "particle set"));
        } else if (actionSource.equalsIgnoreCase("energycombobox")) {
            stats.addAction("energy combobox; etv panel");
            JComboBox moleculeSelection = (JComboBox) e.getSource();
            eConv.setUnit((String) moleculeSelection.getSelectedItem());
            jframe.reset.setEnabled(true);
            handler.publish(new LogRecord(Level.INFO, "energy unit set"));
        } else if(actionSource.equalsIgnoreCase("energytextfield")) {
            try {
                stats.addAction("energy textfield; etv panel");
                energy = Double.parseDouble(jframe.energyTextField.getText());
                jframe.reset.setEnabled(true);
                eConv.setEnergy(energy);
            } catch (NumberFormatException nfe) {
                handler.publish(new LogRecord(Level.WARNING, "user didn't provide double in energytextfield"));
                new WrongInput();
            } catch (NullPointerException npe) {
                handler.publish(new LogRecord(Level.WARNING, "nullpointer caused by wrong user input"));
                new WrongInput();
            }
            handler.publish(new LogRecord(Level.INFO, "user provided correct input in energy text field"));
        } else if(actionSource.equalsIgnoreCase("convert")) {
            try {
                stats.addAction("convert button; etv panel");
                double result = eConv.convert();
                jframe.velocityTextField.setText(String.valueOf(result));
                jframe.save.setEnabled(true);
                jframe.reset.setEnabled(true);
            } catch (NullPointerException npexc) {
                handler.publish(new LogRecord(Level.WARNING, "nullpointer caused by wrong or missing data in etvpanel"));
                new WrongInput();
            }
            handler.publish(new LogRecord(Level.INFO, "convert button pressed"));
        } else if(actionSource.equalsIgnoreCase("save")) {
            stats.addAction("save button; etv panel");
            eConv.saveToFile();
            handler.publish(new LogRecord(Level.INFO, "save button pressed"));
        } else if(actionSource.equalsIgnoreCase("reset")) {
            stats.addAction("reset button; etv panel");
            jframe.particleComboBox.setSelectedIndex(0);
            jframe.energyComboBox.setSelectedIndex(0);
            jframe.velocityTextField.setText("0");
            jframe.energyTextField.setText("0");
            handler.publish(new LogRecord(Level.INFO, "reset button pressed"));
        } else if(actionSource.equalsIgnoreCase("close")) {
            System.exit(0);
        }
    }
}
