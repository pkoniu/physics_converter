package pl.jtp2.statistics;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by pkoniu on 6/9/2014.
 */
public class CustomOutputStream extends OutputStream{
    private JTextArea statArea;

    public CustomOutputStream(JTextArea statArea) {
        this.statArea = statArea;
    }

    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        statArea.append(String.valueOf((char) b));
    }
}
