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
        var isVersionOrHelp = ArgChecker.getArgType(helpArg);
        assertEquals(ArgType.HELP, isVersionOrHelp);

        final var versionArg = new String[] {"--version"};
        isVersionOrHelp = ArgChecker.getArgType(versionArg);
        assertEquals(ArgType.VERSION, isVersionOrHelp);

        final var noArgs = new String[0];
        isVersionOrHelp = ArgChecker.getArgType(noArgs);
        assertEquals(ArgType.NONE, isVersionOrHelp);
    }
}
