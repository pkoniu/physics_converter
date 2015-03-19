package pl.jtp2.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.jtp2.log.handler.CustomHandler;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ChartCreator { //Singleton
    private static volatile ChartCreator chartCreator = null;
    private static Logger logger = null;
    private static CustomHandler handler = null;

    private double startPoint;
    private double endPoint;
    private Properties prop = new Properties();
    private String type;
    private double moleculeMass;
    private XYSeries series;
    private double joules;
    private double mega;

    private HashMap<String, String> chartData = new HashMap<String, String>();

    public static ChartCreator getInstance() {
        if(chartCreator == null) {
            synchronized (ChartCreator.class) {
                if(chartCreator == null) {
                    chartCreator = new ChartCreator();
                }
            }
        }
        return chartCreator;
    }

    public double getMoleculeMass() {
        return moleculeMass;
    }

    public void setMoleculeMass(String m) {
        this.moleculeMass = Double.parseDouble(prop.getProperty(m + "_mass"));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProperties() {
        logger.entering(getClass().getName(), "setProp() in ChartCreator");
        try {
            InputStream propInput = new FileInputStream("lib/data.properties");
            prop.load(propInput);
        } catch (FileNotFoundException fnfe) {
            handler.publish(new LogRecord(Level.WARNING, "data.properties not found! " + fnfe.getMessage()));
        } catch (IOException ioe) {
            handler.publish(new LogRecord(Level.WARNING, "IOException " + ioe.getMessage()));
        }
        joules = Double.parseDouble(prop.getProperty("joules"));
        mega = Double.parseDouble(prop.getProperty("mega_prefix"));
        logger.exiting(getClass().getName(), "setProp() in ChartCreator");
    }

    public double getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(double endPoint) {
        this.endPoint = endPoint;
    }

    public double getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(double startPoint) {
        this.startPoint = startPoint;
    }

    public void setDataForChart() {
        series = new XYSeries("Chart");
        double diff = 1.0;
        double result;
        if(type.equalsIgnoreCase("v(e)")) {
            diff = 0.1;
        }
        for(double i = startPoint; i < endPoint; i += diff) {
            result = getResult(i);
            series.add(i, result);
            chartData.put(String.valueOf(i), String.valueOf(result));
        }
    }

    private double getResult(double i) {
        double result;
        double speedOfLight = Double.parseDouble(prop.getProperty("speedOfLight"));

        double mc2 = moleculeMass*speedOfLight*speedOfLight;

        if(type.equalsIgnoreCase("e(v)")) {
            i = i * joules * mega;
            double sqrt1 = mc2/(i + mc2);
            result = Math.sqrt(((sqrt1*sqrt1) - 1)*(-1));
        } else {
            double fraction = 1 / (1 - i*i);
            double tmp = fraction - 1;
            result = (tmp*mc2) * 0.1610305958132E13;
        }
        return result;
    }

    public void createChart() {
        logger.entering(getClass().getName(), "createChart()");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        String title = "", xaxis = "", yaxis = "";

        if(type.equalsIgnoreCase("e(v)")) {
            title = "Energy depending on velocity";
            xaxis = "velocity [m/s]";
            yaxis = "energy [J]";
        } else if(type.equalsIgnoreCase("v(e)")){
            title = "Velocity depending on energy";
            xaxis = "energy [J]";
            yaxis = "velocity [m/s]";
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                title, // Title
                xaxis, // x-axis Label
                yaxis, // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        Date date = new Date();

        try {
            ChartUtilities.saveChartAsJPEG(new File("chart" + dateFormat.format(date) + ".jpg"), chart, 500, 300);
        } catch (IOException e) {
            handler.publish(new LogRecord(Level.WARNING, "IOException" + e.getMessage()));
        }
        logger.exiting(getClass().getName(), "createChart()");
    }

    private ChartCreator() {
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(ChartCreator.class.getName());
        logger.addHandler(handler);
    }

    public void saveDataToCSV() {
        try {
            FileWriter fstream = new FileWriter("data.csv");
            BufferedWriter bw = new BufferedWriter(fstream);
            bw.write("x,y");
            Iterator i = chartData.entrySet().iterator();
            while(i.hasNext()) {
                Map.Entry mEntry = (Map.Entry) i.next();
                bw.write(mEntry.getKey() + "," + mEntry.getValue() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            handler.publish(new LogRecord(Level.WARNING, "ioexception while writing to csv file : " + e.getMessage()));
        }
    }
}
