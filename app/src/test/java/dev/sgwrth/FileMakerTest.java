package dev.sgwrth;

import dev.sgwrth.core.*;
import dev.sgwrth.util.*;
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
        Path dirpath = Path.of("../testdata/");
        final var className = "Foo";
        fm.makeSourceFile(dirpath, className);
        List<String> lines = null;
        Path fullPath = Path.of(dirpath + className + ".java");

        try {
            lines = Files.readAllLines(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(lines.get(0), "public class Foo {");
        
        // Cleanup.
        try {
            Files.delete(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test void findMainClassWithPackageStmt() {
        final var dirpath = Path.of("../testdata/src/main/java/dev/sgwrth");
        final var fullPath = dirpath.resolve("Foo.java");
        final var fileContents = """
        package dev.sgwrth;

        public static void main(String[] args) {

        }
        """;

        try {
            Files.createDirectories(dirpath);
            Files.writeString(fullPath, fileContents, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final var mainClassFilepathOpt = FileMakerJava.getMainClassFilepath("../testdata");
        assertEquals(fullPath, mainClassFilepathOpt.get());

        final var pathAsStrings = Text.pathAsStrings(mainClassFilepathOpt.get());
        assertEquals(pathAsStrings, List.of("..", "testdata", "src", "main",
            "java", "dev", "sgwrth", "Foo.java"));

        final var packageStmtOpt = Text.getPackageLine(mainClassFilepathOpt.get());
        assertEquals("package dev.sgwrth;", packageStmtOpt.get());

        // Partial cleanup.
        try {
            Files.delete(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test void findMainClassWithoutPackageStmt() {
        final var dirpath = Path.of("../testdata/src/main/java/dev/sgwrth");
        final var fullPath = dirpath.resolve("Foo.java");
        final var fileContents = """
        public static void main(String[] args) {

        }
        """;

        try {
            Files.createDirectories(dirpath);
            Files.writeString(fullPath, fileContents, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final var packageStmtOpt = Text.getPackageLine(fullPath);
        assertEquals("", packageStmtOpt.get());

        // Partial cleanup.
        try {
            Files.delete(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
