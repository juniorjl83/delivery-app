package org.jose.munoz.delivery.file;

import org.jose.munoz.delivery.config.PropertiesLoader;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * File Reader class
 *
 * @author Jose Munoz (jose.munoz@gmail.com)
 */
public class FileReader {

    protected Queue<String> queue = null;
    private final PropertiesLoader propertiesLoader;

    /**
     * class constructor
     *
     * @param propertiesLoader
     */
    public FileReader(PropertiesLoader propertiesLoader) {
        this.queue = new LinkedList<>();
        this.propertiesLoader = propertiesLoader;
    }

    /**
     * method to start reading the file
     */
    public Queue<String> read(int id) {
        BufferedReader br = null;
        String filename = String.format("in%s.txt", id);
        try {
            System.out.println(String.format(propertiesLoader.getProperty("reading.info"), id, filename));
            br = new BufferedReader(new java.io.FileReader(
                    new File(propertiesLoader.getProperty("file.input.path") + filename)));
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                queue.add(buffer);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format(propertiesLoader.getProperty("error.input.file.not.found"), id, filename));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return queue;
    }
}
