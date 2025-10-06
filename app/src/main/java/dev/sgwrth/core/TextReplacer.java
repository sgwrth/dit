package dev.sgwrth;

import java.io.IOException;
import java.nio.file.*;

public class TextReplacer {
    public static void replace(Path filepath, String target, String replacement) {
        try {
            final String oldContent = Files.readString(filepath);
            final String newContent = oldContent.replace(target, replacement);
            Files.writeString(filepath, newContent);
        } catch (IOException e) {
            System.out.println("Error replacing text.");
        }
    }
}
