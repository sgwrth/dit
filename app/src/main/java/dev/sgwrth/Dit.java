package dev.sgwrth;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.*;
import dev.sgwrth.util.*;

public class Dit {
    public static void main(String[] args) {
        if (args.length == 0) {
            MsgPrinter.printMsg(InfoMessages.VERSION);
            return;
        }

        if (ArgChecker.isVersionOrHelp(args[0])) {
            MsgPrinter.printVersionOrHelp(args[0]);
            return;
        }

        final var langOpt = ArgChecker.getLang(args[0]);

        if (!ArgChecker.isValidLang(langOpt)) {
            MsgPrinter.printMsg(ErrorMessages.INVALID_LANG);
            return;
        }

        final var lang = langOpt.get();

        if (args.length == 1) {
            MsgPrinter.printMsg(ErrorMessages.MISSING_ACTION);
            return;
        }

        final var actionArg = args[1];

        if (!ArgChecker.isValidAction(actionArg)) {
            MsgPrinter.printMsg(ErrorMessages.INVALID_ACTION);
            return;
        }

        if (ArgChecker.getArgType(actionArg) == ArgType.FIND) {
            if (args.length == 2) {
                MsgPrinter.printMsg(ErrorMessages.MISSING_PATH);
                return;
            }

            if (args.length == 3) {
                MsgPrinter.printMsg(ErrorMessages.MISSING_SEARCHSTRING);
                return;
            }

            final var searchPath = args[2];
            final var searchString = args[3];
            final var filepaths = FileLister.getFilepaths(lang, searchPath);
            
            if (filepaths.isEmpty()) {
                MsgPrinter.printMsg(InfoMessages.EXITING);
                return;
            }

            FindText.printFinds(filepaths.get(), searchString);
        }
    }
}
