package dev.sgwrth;

import dev.sgwrth.cli.*;
import dev.sgwrth.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

class Dit {
    static void main(String[] args) {
        final var searchString = "standardcharsets";
        final var filepaths = FileLister.getFilepaths("src/");
        for (final var path : filepaths) {
            final var lines = Text.getLines(path);
            final var occurances = lines.stream()
                .filter(line -> Text.containsString(line, searchString))
                .collect(Collectors.toList());
            Text.printOccurancesIfAny(path, occurances);
            
        }

        if (args.length == 0) {
            MsgPrinter.printMsg(InfoMessages.VERSION);
            return;
        }

        if (ArgChecker.isVersionOrHelp(args[0])) {
            MsgPrinter.printVersionOrHelp(args[0]);
            return;
        }

        if (!ArgChecker.isValidLang(args[0])) {
            MsgPrinter.printMsg(ErrorMessages.INVALID_LANG);
            return;
        }

        if (args.length == 1) {
            MsgPrinter.printMsg(ErrorMessages.MISSING_ACTION);
            return;
        }

        // final var actionArg = ArgChecker.getArgType(args[1]);

    }
}
