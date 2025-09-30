package dev.sgwrth.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.*;

public class FileLister {
    public static List<Path> getFilepaths(String dir) {
        Path startDir = Paths.get(dir);
        try (Stream<Path> stream = Files.walk(startDir)) {
            return stream
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
