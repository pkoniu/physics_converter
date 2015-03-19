package pl.jtp2.converter;

import pl.jtp2.log.handler.CustomHandler;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public abstract class Converter {
    protected double energy;
    protected double speed;
    protected String molecule;
    protected double moleculeMass;
    protected double result;
    protected Properties prop = new Properties();
    protected double speedOfLight;
    protected double joules;

    private static CustomHandler handler = null;
    private static Logger logger = null;

    public void setProperties() {
        try {
            InputStream propInput = new FileInputStream("lib/data.properties");
            prop.load(propInput);
        } catch (FileNotFoundException fnfe) {
            handler.publish(new LogRecord(Level.WARNING, "data.properties not found: " + fnfe.getMessage()));
        } catch (IOException ioe) {
            handler.publish(new LogRecord(Level.WARNING, "IOException while setting properties in converter: " + ioe.getMessage()));
        }
        handler = CustomHandler.getInstance();
        logger = Logger.getLogger(Converter.class.getName());
        logger.addHandler(handler);
        speedOfLight = Double.parseDouble(prop.getProperty("speedOfLight"));
        joules = Double.parseDouble(prop.getProperty("joules"));
        handler.publish(new LogRecord(Level.INFO, "properties set"));
    }

    public void setMoleculeMass(String molecule) {
        if(molecule.equalsIgnoreCase("proton")) this.moleculeMass = Double.parseDouble(prop.getProperty("proton_mass"));
        else if(molecule.equalsIgnoreCase("electron")) this.moleculeMass = Double.parseDouble(prop.getProperty("electron_mass"));
        else if(molecule.equalsIgnoreCase("helium")) this.moleculeMass = Double.parseDouble(prop.getProperty("helium_mass"));
        else if(molecule.equalsIgnoreCase("lithium")) this.moleculeMass = Double.parseDouble(prop.getProperty("lithium_mass"));
        else if(molecule.equalsIgnoreCase("carbon")) this.moleculeMass = Double.parseDouble(prop.getProperty("carbon_mass"));
        handler.publish(new LogRecord(Level.INFO, "molecule mass set"));
    }

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public void saveToFile() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh-mm");
        Date date = new Date();
        try {
            File file = new File("results_" + dateFormat.format(date) + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Energy = " +   "\t" + getEnergy() + " J\n");
            bw.write("Molecule = " + "\t\t\t" + getMoleculeMass() + " kg\n");
            bw.write("Velocity = " +         "\t\t\t" + result);
            bw.close();
        } catch (FileNotFoundException e) {
            handler.publish(new LogRecord(Level.WARNING, "FileNotFoundException while saving the converting result " + e.getMessage()));
        } catch (IOException e) {
            handler.publish(new LogRecord(Level.WARNING, "IOException while saving the converting result " + e.getMessage()));
        }
    }

    public double getEnergy() {
        return energy;
    }

    public double getMoleculeMass() {
        return moleculeMass;
    }
}
