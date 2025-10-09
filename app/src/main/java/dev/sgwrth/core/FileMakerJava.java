package dev.sgwrth.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileMakerJava implements FileMaker {
    @Override
    public void makeSourceFile(String name) {
        final var filepath = Path.of("../testdata/" + name + ".java");
        final var fileContent = String.format("""
        public class %s {

        }""", name);

        try {
            Files.writeString(filepath, fileContent, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
