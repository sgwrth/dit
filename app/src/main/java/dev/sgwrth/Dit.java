package dev.sgwrth;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.*;
import dev.sgwrth.core.langs.*;
import dev.sgwrth.util.*;
import java.util.Optional;

// Barfoo
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

        final Optional<Language> langOpt = ArgChecker.getLang(args[0]);

        if (!ArgChecker.isValidLang(langOpt)) {
            MsgPrinter.printMsg(ErrorMessages.INVALID_LANG);
            return;
        }

        final Language lang = langOpt.get();

        if (args.length == 1) {
            MsgPrinter.printMsg(ErrorMessages.MISSING_ACTION);
            return;
        }

        final String actionArg = args[1];

        if (!ArgChecker.isValidAction(actionArg)) {
            MsgPrinter.printMsg(ErrorMessages.INVALID_ACTION);
            System.out.println(actionArg);
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

            final String searchPath = args[2];
            final String searchStr = args[3];
            final var filepathsOpt = FileLister.getFilepaths(lang, searchPath);

            if (filepathsOpt.isEmpty()) {
                MsgPrinter.printMsg(InfoMessages.EXITING);
                return;
            }

            FindText.printFinds(filepathsOpt.get(), searchStr);
        }

        if (ArgChecker.getArgType(actionArg) == ArgType.REPLACE) {
            if (args.length == 2) {
                MsgPrinter.printMsg(ErrorMessages.MISSING_PATH);
                return;
            }

            if (args.length == 3) {
                MsgPrinter.printMsg(ErrorMessages.MISSING_FIND_STR);
                return;
            }

            if (args.length == 4) {
                MsgPrinter.printMsg(ErrorMessages.MISSING_REPLACE_STR);
                return;
            }

            final String searchPath = args[2];
            final String findStr = args[3];
            final String replaceStr = args[4];
            final var filepathsOpt = FileLister.getFilepaths(lang, searchPath);

            if (filepathsOpt.isEmpty()) {
                MsgPrinter.printMsg(InfoMessages.EXITING);
                return;
            }

            filepathsOpt.get().stream()
                .forEach(filepath -> TextReplacer.replace(filepath, findStr, replaceStr));

            FindText.printFinds(filepathsOpt.get(), replaceStr);
        }
    }
}
