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

    @Test void argCheckerValidatesLang() {
        final var cArg = new String[] {"-c"};
        final var cArgType = ArgChecker.getArgType(cArg[0]);
        var isValidLang = ArgChecker.isValidLang(cArgType);
        assertEquals(true, isValidLang);

        final var cppArg = new String[] {"-cpp"};
        final var cppArgType = ArgChecker.getArgType(cppArg[0]);
        isValidLang = ArgChecker.isValidLang(cppArgType);
        assertEquals(true, isValidLang);

        final var invalidLangArg = new String[] {"foo"};
        final var invalidLangArgType = ArgChecker.getArgType(invalidLangArg[0]);
        isValidLang = ArgChecker.isValidLang(invalidLangArgType);
        assertEquals(false, isValidLang);
    }
}
