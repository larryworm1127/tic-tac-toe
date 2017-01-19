package org.eom.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInput {

    private BufferedReader fileIn;

    public FileInput(String filePath) {
        init(filePath);
    }

    public void init(String filePath) {
        try {
            fileIn = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("***Cannot open " + filePath + "***");
        }
    }

    public String readLine() throws IOException {
        return fileIn.readLine();
    }

    public void closeFile() throws IOException {
        fileIn.close();
    }
}
