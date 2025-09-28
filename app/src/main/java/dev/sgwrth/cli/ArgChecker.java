package dev.sgwrth.cli;

import dev.sgwrth.cli.*;

public class ArgChecker {
    public static ArgType getArgType(String[] args) {
        if (args.length == 0) {
            return ArgType.NONE;
        }
        if (args[0].equals("--version")) {
            return ArgType.VERSION;
        }
        if (args[0].equals("--help")) {
            return ArgType.HELP;
        }
        return ArgType.OTHER;
    }

    public static Boolean isVersionOrHelp(ArgType argType) {
        return switch(argType) {
            case ArgType.NONE -> false;
            case ArgType.VERSION -> true;
            case ArgType.HELP -> true;
            default -> false;
        };
    }
}
