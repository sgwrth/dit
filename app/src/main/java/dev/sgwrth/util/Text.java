package dev.sgwrth.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Text {
    public static String getText(Path filepath) {
        try {
            return Files.readString(filepath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static Optional<List<String>> getLines(Path filepath) {
        Optional<FileReader> fileReaderOpt = Text.getFileReader(filepath);

        if (fileReaderOpt.isEmpty()) {
            System.out.println("Error: no FileReader");
            return Optional.empty();
        }

        try (BufferedReader br = new BufferedReader(fileReaderOpt.get())) {
            var lines = new ArrayList<String>();
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                lines.add(Integer.toString(lineNumber) + "    " + line);
                lineNumber++;
            }
            return Optional.of(lines);
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return Optional.empty();
        }
    }

    public static Optional<FileReader> getFileReader(Path filepath) {
        try {
             return Optional.of(new FileReader(filepath.toString()));
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found");
            return Optional.empty();
        }
    }

    public static boolean containsString(String line, String searchString) {
        return line.toUpperCase().contains(searchString.toUpperCase());
    }

    public static void printOccurancesIfAny(Path path, List<String> occurances) {
        if (occurances.size() > 0) {
            System.out.println("\n" + path.toString());

            for (var i = 0; i < path.toString().length(); i++) {
                System.out.print("-");

                if (i == path.toString().length() - 1) {
                    System.out.print("\n");
                }
            }
            occurances.stream()
                .forEach(System.out::println);
        }
    }
}
