package dev.sgwrth.util;

import dev.sgwrth.cli.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSys {
    public static int makeDirs(Path filepath) {
        try {
            Files.createDirectories(filepath);
            return 0;
        } catch (IOException e) {
            MsgPrinter.printMsg(ErrorMsgs.CREATING_DIRS);
            return 1;
        }
    }
}
