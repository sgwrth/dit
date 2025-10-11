package dev.sgwrth.core;

import dev.sgwrth.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

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

    public static Optional<Path> getMainClassFilepath(Path filepath) {
        final var fileReaderOpt = Text.getFileReader(filepath);    

        if (fileReaderOpt.isEmpty()) {
            return Optional.empty();
        }

        try (BufferedReader br = new BufferedReader(fileReaderOpt.get())) {
            String line;
            while ((line = br.readLine()) != null) {
                if (FileMakerJava.containsMainClassCode(line)) {
                    return Optional.of(filepath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.empty();
    }

    public static boolean containsMainClassCode(String line) {
        return (line.contains(" main(")) ? true : false;
    }

    public static Optional<String> getPackageStatement(Path filepath) {
        final var mainClassFilepathOpt = FileMakerJava.getMainClassFilepath(filepath);

        if (mainClassFilepathOpt.isEmpty()) {
            return Optional.empty();
        }

        final var pathAsStrings = Text.pathAsStrings(mainClassFilepathOpt.get());
        int highestIdx = pathAsStrings.size() - 1;
        final var packageStmt = String.format("package %s.%s;",
            pathAsStrings.get(highestIdx - 2), pathAsStrings.get(highestIdx - 1));
        return Optional.of(packageStmt);
    }
}
