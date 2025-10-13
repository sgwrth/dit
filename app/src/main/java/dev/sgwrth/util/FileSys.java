package dev.sgwrth.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSys {
    public static int makeDirs(Path filepath) {
        try {
            Files.createDirectories(filepath);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
