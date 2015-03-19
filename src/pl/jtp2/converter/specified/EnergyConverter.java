package pl.jtp2.converter.specified;

import pl.jtp2.converter.Converter;
import pl.jtp2.log.handler.CustomHandler;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class EnergyConverter extends Converter { //Singleton
    private static volatile EnergyConverter eConv = null;

    private double energy;
    private String unit;

    private static CustomHandler handler = null;
    private static Logger logger = null;

    public static EnergyConverter getInstance() {
        if(eConv == null) {
            synchronized (EnergyConverter.class) {
                if(eConv == null) {
                    eConv = new EnergyConverter();
                }
            }
        }
        return eConv;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setEnergy(double e) {
        double joules = Double.parseDouble(prop.getProperty("joules"));
        if(unit.equalsIgnoreCase("kev")) this.energy = e * joules * Double.parseDouble(prop.getProperty("kilo_prefix"));
        else if(unit.equalsIgnoreCase("mev")) this.energy = e * joules * Double.parseDouble(prop.getProperty("mega_prefix"));
        else if(unit.equalsIgnoreCase("ev")) this.energy = e * joules;
    }

    public double convert() {
        logger.entering(getClass().getName(), "converting energy");
        double mc2 = moleculeMass * speedOfLight * speedOfLight;
        double sqrt1 = mc2 / (energy + mc2);
        handler.publish(new LogRecord(Level.INFO, "Converting energy"));
        logger.exiting(getClass().getName(), "converting energy");
        return Math.sqrt(((sqrt1*sqrt1) - 1)*(-1));
    }

    private EnergyConverter() {
        super.setProperties();
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(EnergyConverter.class.getName());
        logger.addHandler(handler);
    }
}

