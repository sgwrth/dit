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
    /*
    @Test void fileMakesCreatesBasicJavaSourceFile() {
        FileMaker fm = new FileMakerJava();
        Path dirpath = Path.of("../testdata/");
        final var className = "Foo";
        fm.makeSourceFile(dirpath, className);
        List<String> lines = null;
        Path fullPath = Path.of(dirpath + "/" + className + ".java");

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
    */

    @Test void findMainClassWithPackageStmt() {
        final var dirpath = Path.of("../testdata/src/main/java/dev/sgwrth");
        final var fullPath = dirpath.resolve("Foo.java");
        final var packageAndClass = "package dev.sgwrth;\n\npublic class Foo {\n";
        final var mainClass = "\tpublic static void main(String[] args) {\n";
        final var closingBraces = "\t}\n}";
        final String fileContents = packageAndClass + mainClass + closingBraces;

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
        final var publicClass = "public class Foo {\n";
        final var mainClass = "\tpublic static void main(String[] args) {\n";
        final var closingBraces = "\t}\n}";
        final String fileContents = publicClass + mainClass + closingBraces;

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

    @Test void getMainClassDir() {
        final var fullPath = Path.of("../testdata/src/main/java/dev/sgwrth/Foo.java");
        final var mainClassDir = FileMakerJava.getMainClassDir(fullPath);
        final var dir = Path.of("../testdata/src/main/java/dev/sgwrth");
        assertEquals(dir, mainClassDir);
    }

    @Test void getFilenameWithoutExtension() {
        final var filename = "Foo.java";
        final var filenameWithoutExt = FileMakerJava.cropExtension(filename);
        assertEquals("Foo", filenameWithoutExt);
    }

    @Test void convertPathToPackageStmt() {
        final var path = "foo/bar/";
        final var packageStmt = FileMakerJava.pathToPackageStmt(path);
        assertEquals("foo.bar;", packageStmt);
    }
}
