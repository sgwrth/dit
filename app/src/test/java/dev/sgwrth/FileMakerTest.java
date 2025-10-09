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
    @Test void fileMakesCreatesSourceFile() {
        Path filepath = Path.of("../testdata");
        final var className = "Foo";
        FileMaker fm = new FileMakerJava();
        fm.makeSourceFile(className);
        Path fullFilepath = Path.of(filepath + "/Foo.java");
        List<String> lines = null;

        try {
            lines = Files.readAllLines(fullFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(lines.get(0), "public class Foo {");
    }
}
