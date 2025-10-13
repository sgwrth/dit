package dev.sgwrth.core;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.langs.*;
import dev.sgwrth.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.*;

public class FileMakerJava implements FileMaker {
    @Override
    public void makeSourceFile(Path dirpath, String className) {
        final var filepath = Path.of(dirpath + className + ".java");
        final var fileContent = String.format("""
        public class %s {

        }""", className);

        try {
            Files.writeString(filepath, fileContent, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean containsMainClass(Path filepath) {
        final var fileReaderOpt = Text.getFileReader(filepath);    

        if (fileReaderOpt.isEmpty()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(fileReaderOpt.get())) {
            String line;
            while ((line = br.readLine()) != null) {
                if (FileMakerJava.containsMainClassCode(line)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static boolean containsMainClassCode(String line) {
        return (line.contains(" void main(")) ? true : false;
    }

    public static Optional<Path> getMainClassFilepath(String dir) {
        final var langJava = new LangJava();
        final var fileExtension = langJava.getExtension();

        try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
            final var filepaths = stream
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(fileExtension))
                .filter(path -> FileMakerJava.containsMainClass(path))
                .collect(Collectors.toList());

            if (filepaths.size() != 1) {
                return Optional.empty();
            }
            
            return Optional.of(filepaths.get(0));
        } catch (IOException e) {
            MsgPrinter.printMsg(ErrorMsgs.FILEPATH_LIST);
            return Optional.empty();
        }
    }

    public static Path getMainClassDir(Path fullPath) {
        final var pathAsStrings = Text.pathAsStrings(fullPath);
        pathAsStrings.removeLast();
        return Path.of(String.join("/", pathAsStrings));
    }
}
