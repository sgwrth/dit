package dev.sgwrth.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Text {
    public static String getText(Path filepath) {
        try {
            return Files.readString(filepath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
