package dev.sgwrth.cli;

import dev.sgwrth.cli.*;

public class ArgChecker {
    public static ArgType getArgType(String arg) {
        arg = arg.toLowerCase();

        if (arg.equals("--version") || arg.equals("-v")) {
            return ArgType.VERSION;
        }

        if (arg.equals("--help") || arg.equals("-h")) {
            return ArgType.HELP;
        }

        if (arg.equals("-c") || arg.equals("--c")) {
            return ArgType.C;
        }

        if (arg.equals("-cpp")
                || arg.equals("--cpp")
                || arg.equals("-c++")
                || arg.equals("--c++")
        ) {
            return ArgType.CPP;
        }

        if (arg.equals("-j") || arg.equals("--java")) {
            return ArgType.JAVA;
        }

        if (arg.equals("-f") || arg.equals("--find")) {
            return ArgType.FIND;
        }

        return ArgType.OTHER;
    }

    public static Boolean isVersionOrHelp(String arg) {
        final var argType = ArgChecker.getArgType(arg);
        return switch (argType) {
            case ArgType.VERSION -> true;
            case ArgType.HELP -> true;
            default -> false;
        };
    }

    public static Boolean isValidLang(String arg) {
        final var argType = ArgChecker.getArgType(arg);
        return switch (argType) {
            case ArgType.C -> true;
            case ArgType.CPP -> true;
            case ArgType.JAVA -> true;
            default -> false;
        };
    }
    
    public static Boolean isValidAction(String arg) {
        final var argType = ArgChecker.getArgType(arg);
        return switch (argType) {
            case ArgType.FIND -> true;
            default -> false;
        };
    }
}
