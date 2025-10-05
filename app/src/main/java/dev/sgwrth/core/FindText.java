package dev.sgwrth.core;

import dev.sgwrth.cli.*;
import dev.sgwrth.util.*;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FindText {
    public static void printFinds(List<Path> filepaths, String searchString) {
        for (final var filepath : filepaths) {
            final var linesOpt = Text.getLines(filepath);

            if (linesOpt.isEmpty()) {
                MsgPrinter.printMsg(ErrorMessages.GETTING_LINES);
                return;
            }

            final var occurances = linesOpt.get().stream()
                .filter(line -> Text.containsString(line, searchString))
                .collect(Collectors.toList());
            Output.printOccurancesIfAny(filepath, occurances);
        }
    }
}
