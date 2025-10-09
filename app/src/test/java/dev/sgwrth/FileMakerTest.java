package dev.sgwrth;

import dev.sgwrth.core.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileMakerTest {
    @Test void fileMakesCreatesBasicJavaSourceFile() {
        FileMaker fm = new FileMakerJava();
        fm.makeSourceFile("Foo");
        Path fullFilepath = Path.of("../testdata/Foo.java");
        List<String> lines = null;

        try {
            lines = Files.readAllLines(fullFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(lines.get(0), "public class Foo {");
        
        // Clean up.
        try {
            Files.delete(fullFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
