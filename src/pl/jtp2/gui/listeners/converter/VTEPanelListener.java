package pl.jtp2.gui.listeners.converter;

import pl.jtp2.converter.specified.SpeedConverter;
import pl.jtp2.exceptions.WrongDataException;
import pl.jtp2.gui.frames.converter.VTEPanel;
import pl.jtp2.gui.frames.errors.WrongInput;
import pl.jtp2.log.handler.CustomHandler;
import pl.jtp2.statistics.UserStatistics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class VTEPanelListener implements ActionListener{
    private SpeedConverter sConv;
    private VTEPanel jframe;
    private UserStatistics stats = UserStatistics.getInstance();

    private double speed;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public VTEPanelListener(VTEPanel jframe) {
        this.jframe = jframe;
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(ETVPanelListener.class.getName());
        logger.addHandler(handler);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionSource = e.getActionCommand();
        sConv = SpeedConverter.getInstance();
        sConv.setProperties();

        if (actionSource.equalsIgnoreCase("particlecombobox")) {
            stats.addAction("particle combobox; vte panel");
            JComboBox particleSelection = (JComboBox) e.getSource();
            sConv.setMolecule((String) particleSelection.getSelectedItem());
            sConv.setMoleculeMass(sConv.getMolecule());
            jframe.reset.setEnabled(true);
            handler.publish(new LogRecord(Level.INFO, "particle set"));
        } else if(actionSource.equalsIgnoreCase("energytextfield")) {
            try {
                stats.addAction("speed textfield; vte panel");
                speed = Double.parseDouble(jframe.energyTextField.getText());
                jframe.reset.setEnabled(true);
                sConv.setSpeed(speed);
                if(speed >= 1) {
                    throw new WrongDataException();
                }
            } catch (NumberFormatException nfe) {
                new WrongInput();
                handler.publish(new LogRecord(Level.WARNING, "user didn't provide double in energytextfield"));
            } catch (NullPointerException npe) {
                new WrongInput();
                handler.publish(new LogRecord(Level.WARNING, "nullpointer caused by wrong user input"));
            } catch (WrongDataException e1) {
                new WrongInput();
                handler.publish(new LogRecord(Level.WARNING, "speed value >= 1"));
            }
        } else if(actionSource.equalsIgnoreCase("convert")) {
            try {
                stats.addAction("convert button; vte panel");
                double result = sConv.convert();
                jframe.velocityTextField.setText(String.valueOf(result));
                jframe.save.setEnabled(true);
                jframe.reset.setEnabled(true);
            } catch (NullPointerException npexc) {
                new WrongInput();
                handler.publish(new LogRecord(Level.WARNING, "nullpointer caused by wrong or missing data in vtepanel"));
            }
            handler.publish(new LogRecord(Level.INFO, "convert button pressed"));
        } else if(actionSource.equalsIgnoreCase("save")) {
            stats.addAction("save button; vte panel");
            sConv.saveToFile();
            handler.publish(new LogRecord(Level.INFO, "save button pressed"));
        } else if(actionSource.equalsIgnoreCase("reset")) {
            stats.addAction("reset button; vte panel");
            jframe.particleComboBox.setSelectedIndex(0);
            jframe.velocityTextField.setText("0");
            jframe.energyTextField.setText("0");
            handler.publish(new LogRecord(Level.INFO, "reset button pressed"));
        } else if(actionSource.equalsIgnoreCase("close")) {
            System.exit(0);
        }
    }
}
