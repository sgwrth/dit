package dev.sgwrth;

import dev.sgwrth.cli.*;
import dev.sgwrth.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

class Dit {
    static void main(String[] args) {
        FileLister.getFilepaths("src/")
            .stream()
            .map(Text::getText)
            .forEach(System.out::println);

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
