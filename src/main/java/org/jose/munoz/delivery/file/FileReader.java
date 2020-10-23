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

    protected Queue<String> queue;

    /**
     * method to start reading the file
     */
    public Queue<String> read(int id) {
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        queue = new LinkedList<String>();
        String filename = String.format("in%s.txt", id);
        File file = new File(propertiesLoader.getProperty("file.input.path") + filename);

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
            System.out.println(String.format(propertiesLoader.getProperty("reading.info"), id, filename));
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                queue.add(buffer);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format(propertiesLoader.getProperty("error.input.file.not.found"), id, filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queue;
    }
}
