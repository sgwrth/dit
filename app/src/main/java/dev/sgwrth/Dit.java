package dev.sgwrth;

import dev.sgwrth.cli.*;

class Dit {
    static void main(String[] args) {
        if (ArgChecker.isVersionOrHelp(ArgChecker.getArgType(args))) {
            MsgPrinter.printVersionOrHelp(args);
            return;
        }
    }
}
