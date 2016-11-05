package it.andreasilva.executearuound;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
    public static String process(String fileName, BufferedReaderProcessor readerProcessor) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return readerProcessor.process(reader);
        }
    }
}
