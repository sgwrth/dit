package dev.sgwrth;

import dev.sgwrth.cli.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MsgPrinterTest {
    @Test void msgPrinterExists() {
        final var msgPrinter = new MsgPrinter();
        assertNotNull(msgPrinter, "MsgPrinter should exist");
    }
}
