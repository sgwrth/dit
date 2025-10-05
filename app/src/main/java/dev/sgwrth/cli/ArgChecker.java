package dev.sgwrth.cli;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.langs.*;
import java.util.Optional;

public class ArgChecker {
    public static ArgType getArgType(String arg) {
        arg = arg.toLowerCase();

        if (arg.equals("--version") || arg.equals("-v")) {
            return ArgType.VERSION;
        }

        if (arg.equals("--help") || arg.equals("-h")) {
            return ArgType.HELP;
        }

        if (arg.equals("-f") || arg.equals("--find")) {
            return ArgType.FIND;
        }

        return ArgType.OTHER;
    }

    public static Optional<Language> getLang(String arg) {
        if (arg.equals("-c") || arg.equals("--c")) {
            return Optional.of(new LangC());
        }

        if (arg.equals("-cpp")
                || arg.equals("--cpp")
                || arg.equals("-c++")
                || arg.equals("--c++")
        ) {
            return Optional.of(new LangCpp());
        }

        if (arg.equals("-j") || arg.equals("--java")) {
            return Optional.of(new LangJava());
        }

        return Optional.empty();
    }

    public static Boolean isVersionOrHelp(String arg) {
        final var argType = ArgChecker.getArgType(arg);
        return switch (argType) {
            case ArgType.VERSION -> true;
            case ArgType.HELP -> true;
            default -> false;
        };
    }

    public static Boolean isValidLang(Optional<Language> langOpt) {
        if (langOpt.isEmpty()) {
            return false;
        }

        return switch (langOpt.get()) {
            case LangC c -> true;
            case LangCpp cpp -> true;
            case LangJava j -> true;
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
