package org.eom.main;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileEditor {

    private BufferedWriter fileOut;

    public FileEditor(String filePath) {
        init(filePath);
    }

    public void init(String filePath) {
        try {
            fileOut = new BufferedWriter(new FileWriter(filePath, true));
        } catch (IOException e) {
            System.out.println("Cannot create file: " + filePath);
        }
    }

    public void write(String content) throws IOException {
        fileOut.write(content);
        fileOut.newLine();
    }

    public void closeFile() throws IOException {
        fileOut.close();
    }
}
