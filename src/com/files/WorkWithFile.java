package com.files;


import com.exeption.EncoderExeption;
import com.exeption.FileExeption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorkWithFile {
    private final String pathToLoadFile;
    private final String pathToCreateFile;

    public WorkWithFile(String pathToLoadFile, String pathToCreateFile) {
        this.pathToLoadFile = pathToLoadFile;
        this.pathToCreateFile = pathToCreateFile;
    }

    public String loadTextOfFile(String fileName) throws FileExeption {
        Path path = Path.of(pathToLoadFile + fileName);
        if (!Files.isRegularFile(path)) {

            throw new FileExeption("The file is not find, path: " + path);

        }
        String textOfFile = "";
        try {
            textOfFile = Files.readString(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return textOfFile;
    }

    public void createFileWriteToText(StringBuilder writeText, String nameOfFile) {
        if (Files.isRegularFile(Path.of(pathToCreateFile + nameOfFile))) {
            try {
                Files.delete(Path.of(pathToCreateFile + nameOfFile));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
        String textString = writeText.toString();
        try {
            Files.createFile(Path.of(pathToCreateFile + nameOfFile));
            Files.write(Path.of(pathToCreateFile + nameOfFile), textString.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
