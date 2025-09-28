package dev.sgwrth;

import dev.sgwrth.cli.*;

class Dit {
    static void main(String[] args) {
        if (args.length == 0) {
            MsgPrinter.printMsg(InfoMessages.VERSION);
            return;
        }

        if (ArgChecker.isVersionOrHelp(ArgChecker.getArgType(args[0]))) {
            MsgPrinter.printVersionOrHelp(args[0]);
            return;
        }

        if (!ArgChecker.isValidLang(ArgChecker.getArgType(args[0]))) {
            MsgPrinter.printMsg(ErrorMessages.INVALID_LANG);
            return;
        }
    }
}
