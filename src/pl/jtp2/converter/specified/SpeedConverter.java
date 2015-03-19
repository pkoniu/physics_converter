package pl.jtp2.converter.specified;

import pl.jtp2.converter.Converter;
import pl.jtp2.log.handler.CustomHandler;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class SpeedConverter extends Converter { //Singleton
    private static volatile SpeedConverter sConv = null;

    private double speed;

    private static CustomHandler handler = null;
    private static Logger logger = null;

    public static SpeedConverter getInstance() {
        if(sConv == null) {
            synchronized (SpeedConverter.class) {
                if(sConv == null) {
                    sConv = new SpeedConverter();
                }
            }
        }
        return sConv;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double convert() {
        logger.entering(getClass().getName(), "converting speed");
        double mc2 = moleculeMass * speedOfLight * speedOfLight;
        double fraction = 1 / (1 - speed*speed);
        double tmp = fraction - 1;
        handler.publish(new LogRecord(Level.INFO, "Converting speed"));
        logger.exiting(getClass().getName(), "converting speed");
        return (tmp*mc2) * 0.1610305958132E13;
    }

    private SpeedConverter() {
        super.setProperties();
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(SpeedConverter.class.getName());
        logger.addHandler(handler);
    }
}
