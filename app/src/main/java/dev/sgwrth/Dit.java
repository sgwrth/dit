package dev.sgwrth;

import dev.sgwrth.cli.*;
import dev.sgwrth.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

class Dit {
    static void main(String[] args) {
        
        FileLister.listFiles("src/")
            .stream()
            .map(path -> {
                try {
                    return Files.readString(path, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                }
            })
            .forEach(System.out::println);

        if (args.length == 0) {
            MsgPrinter.printMsg(InfoMessages.VERSION);
            return;
        }

        if (ArgChecker.isVersionOrHelp(ArgChecker.getArgType(args[0]))) {
            MsgPrinter.printVersionOrHelp(args[0]);
            return;
        }

        final var langArg = ArgChecker.getArgType(args[0]);

        if (!ArgChecker.isValidLang(langArg)) {
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
