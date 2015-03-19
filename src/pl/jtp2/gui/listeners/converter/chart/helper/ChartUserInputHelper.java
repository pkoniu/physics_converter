package pl.jtp2.gui.listeners.converter.chart.helper;

import pl.jtp2.log.handler.CustomHandler;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ChartUserInputHelper { //singleton
    private static ChartUserInputHelper chartUserInputHelper = null;

    private boolean tmp1 = false;
    private boolean tmp2 = false;
    private boolean tmp3 = false;
    private boolean tmp4 = false;

    private static Logger logger = null;
    private static CustomHandler handler = null;

    public boolean areTrue() {
        return this.isTmp1() && this.isTmp2() && this.isTmp3() && this.isTmp4();
    }

    public boolean isTmp4() {
        return tmp4;
    }

    public void setTmp4(boolean tmp4) {
        this.tmp4 = tmp4;
        handler.publish(new LogRecord(Level.INFO, "tmp4 set" + tmp4));
    }

    public boolean isTmp3() {
        return tmp3;
    }

    public void setTmp3(boolean tmp3) {
        this.tmp3 = tmp3;
        handler.publish(new LogRecord(Level.INFO, "tmp3 set" + tmp3));
    }

    public boolean isTmp2() {
        return tmp2;
    }

    public void setTmp2(boolean tmp2) {
        this.tmp2 = tmp2;
        handler.publish(new LogRecord(Level.INFO, "tmp2 set" + tmp2));
    }

    public boolean isTmp1() {
        return tmp1;
    }

    public void setTmp1(boolean tmp1) {
        this.tmp1 = tmp1;
        handler.publish(new LogRecord(Level.INFO, "tmp1 set" + tmp1));
    }

    public static ChartUserInputHelper getInstance() {
        if(chartUserInputHelper == null) {
            synchronized (ChartUserInputHelper.class) {
                if(chartUserInputHelper == null) {
                    chartUserInputHelper = new ChartUserInputHelper();
                }
            }
        }
        return chartUserInputHelper;
    }

    private ChartUserInputHelper() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(ChartUserInputHelper.class.getName());
        logger.addHandler(handler);
    }
}
