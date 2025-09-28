package dev.sgwrth.cli;

public class MsgPrinter {
    public static void printMsg(String msg) {
        System.out.println(msg);
    }

    public static void printVersionOrHelp(String[] args) {
        final var argType = ArgChecker.getArgType(args);

        if (argType == ArgType.VERSION) {
            printMsg(InfoMessages.VERSION);
            return;
        }

        if (argType == ArgType.HELP) {
            printMsg(HelpMessages.GENERAL_HELP);
            return;
        }
    }
}
