package dev.sgwrth.util;

import dev.sgwrth.cli.*;
import dev.sgwrth.core.*;
import dev.sgwrth.core.langs.*;
import java.lang.RuntimeException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;

public class FileLister {
    public static Optional<List<Path>> getFilepaths(Language lang, String dir) {
        final var fileExtension = lang.getExtension();

        try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
            return Optional.of(stream
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(fileExtension))
                .collect(Collectors.toList()));
        } catch (IOException e) {
            MsgPrinter.printMsg(ErrorMsgs.FILEPATH_LIST + "('" + dir + "')");
            return Optional.empty();
        }
    }

}
