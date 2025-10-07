package dev.sgwrth;

import dev.sgwrth.cli.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgCheckerTest {
    @Test void argCheckerExists() {
        final var argChecker = new ArgChecker();
        assertNotNull(argChecker, "ArgChecker should exist");
    }

    @Test void argCheckerWorksForVersionAndHelp() {
        final var helpArg = new String[] {"--help"};
        var isVersionOrHelp = ArgChecker.getArgType(helpArg[0]);
        assertEquals(ArgType.HELP, isVersionOrHelp);

        final var versionArg = new String[] {"--version"};
        isVersionOrHelp = ArgChecker.getArgType(versionArg[0]);
        assertEquals(ArgType.VERSION, isVersionOrHelp);

        final var unknownArgs = new String[] {"foo"};
        isVersionOrHelp = ArgChecker.getArgType(unknownArgs[0]);
        assertEquals(ArgType.OTHER, isVersionOrHelp);
    }

    @Test void argCheckerWorksForActions() {
        final var findArgs = new String[] {"-f"};
        var argType = ArgChecker.getArgType(findArgs[0]);
        assertEquals(ArgType.FIND, argType);

        final var replaceArgs = new String[] {"-r"};
        argType = ArgChecker.getArgType(replaceArgs[0]);
        assertEquals(ArgType.REPLACE, argType);
    }

    @Test void argCheckerValidatesLang() {
        final var cArg = new String[] {"-c"};
        var isValidLang = ArgChecker.isValidLang(ArgChecker.getLang(cArg[0]));
        assertEquals(true, isValidLang);

        final var cppArg = new String[] {"-cpp"};
        isValidLang = ArgChecker.isValidLang(ArgChecker.getLang(cppArg[0]));
        assertEquals(true, isValidLang);

        final var invalidLangArg = new String[] {"foo"};
        isValidLang = ArgChecker.isValidLang(ArgChecker.getLang(invalidLangArg[0]));
        assertEquals(false, isValidLang);
    }
}
