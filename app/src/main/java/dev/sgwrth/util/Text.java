package dev.sgwrth.util;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Text {
    public static Optional<List<String>> getLines(Path filepath) {
        Optional<FileReader> fileReaderOpt = Text.getFileReader(filepath);

        if (fileReaderOpt.isEmpty()) {
            MsgPrinter.printMsg(ErrorMsgs.NO_FILEREADER);
            return Optional.empty();
        }

        try (BufferedReader br = new BufferedReader(fileReaderOpt.get())) {
            return Optional.of(Text.writeLines(br));
        } catch (IOException e) {
            MsgPrinter.printMsg(ErrorMsgs.BUFFERED_READER);
            return Optional.empty();
        }
    }

    public static Optional<FileReader> getFileReader(Path filepath) {
        try {
             return Optional.of(new FileReader(filepath.toString()));
        } catch (FileNotFoundException e) {
            MsgPrinter.printMsg(ErrorMsgs.NO_FILEREADER);
            return Optional.empty();
        }
    }

    public static List<String> writeLines(BufferedReader br) {
        List<String> lines = new ArrayList<String>();
        String line;
        int lineNumber = 1;

        try {
            while ((line = br.readLine()) != null) {
                lines.add(Integer.toString(lineNumber) + "    " + line);
                lineNumber++;
            }
        } catch (IOException e) {
            MsgPrinter.printMsg(ErrorMsgs.READING_LINE_WITH_BR);
        }
        
        return lines;
    }

    public static boolean containsString(String line, String searchString) {
        return line.contains(searchString);
    }

    public static List<String> pathAsStrings(Path filepath) {
        final var pathParts = new ArrayList<String>();
        for (Path part : filepath) {
            pathParts.add(part.toString());
        }
        return pathParts;
    }

    public static Optional<String> getPackageLine(Path filepath) {
        final var fileReaderOpt = Text.getFileReader(filepath);

        if (fileReaderOpt.isEmpty()) {
            return Optional.empty();
        }

        try (BufferedReader br = new BufferedReader(fileReaderOpt.get())) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("package ")) {
                    return Optional.of(line);
                }
            }
        } catch (IOException e) {
            Optional.empty();
        }

        return Optional.of("");
    }

    public static boolean containsPackageLine(Path filepath) {
        final var fileReaderOpt = Text.getFileReader(filepath);

        if (fileReaderOpt.isEmpty()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(fileReaderOpt.get())) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("package ")) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }
}
