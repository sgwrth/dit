package dev.sgwrth.cli;

import java.nio.file.*;
import java.util.List;

public class Output {
    public static void printOccurancesIfAny(Path path, List<String> occurances) {
        if (occurances.size() > 0) {
            Output.printNewlineAndFilename(path);
            Output.printUnderline(path);
            occurances.stream()
                .forEach(System.out::println);
        }
    }

    public static void printNewlineAndFilename(Path path) {
        System.out.println("\n" + path.toString());
    }

    public static void printUnderline(Path path) {
        final var pathString = path.toString();

        for (int i = 0; i < pathString.length(); i++) {
            System.out.print("-");

            if (i == pathString.length() - 1) {
                System.out.print("\n");
            }
        }
    }
}
