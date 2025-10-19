package dev.sgwrth.core;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.langs.*;
import dev.sgwrth.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.*;

public class FileMakerJava implements FileMaker {
    public static void makeSourceFile(
            Path dirpath,
            String className,
            String packageLine,
            String targetDir
    ) {
        final var classNameWithoutExt = FileMakerJava.cropExtension(className);
        final var filepath = Path.of(dirpath + "/" + classNameWithoutExt + ".java");
        String fileContent = "";

        if (packageLine.equals("")) {
            fileContent = String.format("""
            public class %s {

            }""", classNameWithoutExt);
        } else {
            final var concatenatedPackageLine
                = FileMakerJava.concatPackageLine(packageLine, targetDir);
            fileContent = String.format("""
            %s

            public class %s {

            }""", concatenatedPackageLine, classNameWithoutExt);
        }

        try {
            Files.writeString(filepath, fileContent, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean containsMainClass(Path filepath) {
        final var fileReaderOpt = Text.getFileReader(filepath);    

        if (fileReaderOpt.isEmpty()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(fileReaderOpt.get())) {
            String line;
            while ((line = br.readLine()) != null) {
                if (FileMakerJava.containsMainClassCode(line)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static boolean containsMainClassCode(String line) {
        String regex = "^\\s+.*\\s+main\\(.*\\)\\s*\\{\\s*$";
        return line.matches(regex);
    }

    public static Optional<Path> getMainClassFilepath(String dir) {
        final var langJava = new LangJava();
        final var fileExtension = langJava.getExtension();

        try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
            final var filepaths = stream
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(fileExtension))
                .filter(path -> FileMakerJava.containsMainClass(path))
                .collect(Collectors.toList());

            if (filepaths.size() != 1) {
                return Optional.empty();
            }
            
            return Optional.of(filepaths.get(0));
        } catch (IOException e) {
            MsgPrinter.printMsg(ErrorMsgs.FILEPATH_LIST + " ('" + dir + "')");
            return Optional.empty();
        }
    }

    public static Path getMainClassDir(Path fullPath) {
        final var pathAsStrings = Text.pathAsStrings(fullPath);
        pathAsStrings.removeLast();
        return Path.of(String.join("/", pathAsStrings));
    }

    public static String cropExtension(String filename) {
        if (filename.contains(".")) {
            String[] filenameParts = filename.split("\\.");
            return filenameParts[0];
        }
        return filename;
    }

    public static String pathToPackageStmt(String path) {
        if (path.substring(path.length() - 1).equals("/")) {
            path = path.substring(0, path.length() - 1);
        }

        path = path.replace('/', '.');

        return path + ";";
    }

    public static String concatPackageLine(String packageLine, String subDir) {
        final var packageLineWithPeriod = packageLine.replace(";", ".");
        final var subDirWithPeriod = FileMakerJava.pathToPackageStmt(subDir);
        return packageLineWithPeriod + subDirWithPeriod;
    }
}
