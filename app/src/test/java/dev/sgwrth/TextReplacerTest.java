package dev.sgwrth;

import dev.sgwrth.cli.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextReplacerTest {
    @Test void textReplacerExists() {
        final var textReplacer = new TextReplacer();
        assertNotNull(textReplacer, "TextReplacer should exist");
    }
    
    @Test void textReplacerWorks() {
        final var filepath = Path.of("../testdata/test.txt");
        final String testContent = "All work and no play makes Jack a dull boy.";

        // Creating test file.
        try {
            Files.writeString(filepath, testContent);
        } catch (IOException e) {
            System.out.println("Error writing test file.");
        }

        TextReplacer.replace(filepath, "Jack", "Danny");

        try {
            final String content = Files.readString(filepath);
            assertEquals(content, "All work and no play makes Danny a dull boy.");
        } catch (IOException e) {
            System.out.println("Error reading test file.");
        }

        // Cleaning up.
        try {
            Files.deleteIfExists(filepath);
        } catch (IOException e) {
            System.out.println("Error deleting test file.");
        }
    }
}
