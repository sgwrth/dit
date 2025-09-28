package dev.sgwrth.cli;

import dev.sgwrth.cli.*;

public class ArgChecker {
    public static ArgType getArgType(String arg) {
        if (arg.equals("--version") || arg.equals("-v")) {
            return ArgType.VERSION;
        }

        if (arg.equals("--help") || arg.equals("-h")) {
            return ArgType.HELP;
        }

        if (arg.equals("-c")) {
            return ArgType.C;
        }

        if (arg.equals("-cpp")) {
            return ArgType.CPP;
        }

        return ArgType.OTHER;
    }

    public static Boolean isVersionOrHelp(ArgType argType) {
        return switch(argType) {
            case ArgType.VERSION -> true;
            case ArgType.HELP -> true;
            default -> false;
        };
    }

    public static Boolean isValidLang(ArgType argType) {
        return switch(argType) {
            case ArgType.C -> true;
            case ArgType.CPP -> true;
            default -> false;
        };
    }
}
