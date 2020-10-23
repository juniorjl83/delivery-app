package org.jose.munoz.delivery.file;

import org.jose.munoz.delivery.config.PropertiesLoader;

import java.io.*;

/**
 * File writer class
 *
 * @author Jose Munoz (jose.munoz@gmail.com)
 */
public class FileWriter {

    private final PropertiesLoader propertiesLoader;

    /**
     * Class constructor
     *
     * @param propertiesLoader
     */
    public FileWriter(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    /**
     * method to start writing the file
     */
    public void writeFile(String destiny, int id) {
        PrintWriter writer = null;
        try {
            String filename = String.format("out%s.txt", id);
            writer = new PrintWriter(
                    new java.io.FileWriter(
                            new File(propertiesLoader.getProperty("file.output.path") + filename), true));
            writer.println(destiny);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
