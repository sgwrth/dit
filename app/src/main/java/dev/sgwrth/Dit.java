package dev.sgwrth;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.*;
import dev.sgwrth.core.langs.*;
import dev.sgwrth.util.*;
import java.nio.file.Path;
import java.util.Optional;

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
            MsgPrinter.printMsg(ErrorMsgs.INVALID_LANG);
            return;
        }

        final Language lang = langOpt.get();

        if (args.length == 1) {
            MsgPrinter.printMsg(ErrorMsgs.MISSING_ACTION);
            return;
        }

        final String actionArg = args[1];

        if (!ArgChecker.isValidAction(actionArg)) {
            MsgPrinter.printMsg(ErrorMsgs.INVALID_ACTION + " '" + actionArg + "'");
            return;
        }

        if (ArgChecker.getArgType(actionArg) == ArgType.FIND) {
            switch (args.length) {
                case 2 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_PATH); return; }
                case 3 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_SEARCHSTRING); return; }
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
            switch (args.length) {
                case 2 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_PATH); return; }
                case 3 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_FIND_STR); return; }
                case 4 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_REPLACE_STR); return; }
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
            return;
        }

        if (ArgChecker.getArgType(actionArg) == ArgType.MAKE) {
            switch (args.length) {
                case 2 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_PATH); return; }
                case 3 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_TARGET_DIR); return; }
                case 4 -> { MsgPrinter.printMsg(ErrorMsgs.MISSING_FILENAME); return; }
            }

            final var filepath = args[2];
            final var targetDir = args[3];
            final var filename = args[4];
            final var mainClassFilepathOpt = FileMakerJava.getMainClassFilepath(filepath);

            if (mainClassFilepathOpt.isEmpty()) {
                return;
            }

            final var mainClassDir
                = FileMakerJava.getMainClassDir(mainClassFilepathOpt.get());
            final var fullTargetPath
                = Path.of(mainClassDir.toString() + "/" + targetDir.toString());
            final var makeDirsErrno = FileSys.makeDirs(fullTargetPath);

            if (makeDirsErrno == 1) {
                System.out.println("Error creating directories.");
                return;
            }

            final FileMaker fmj = new FileMakerJava();
            fmj.makeSourceFile(fullTargetPath, filename);
        }
    }
}
