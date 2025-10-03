package dev.sgwrth.util;

import dev.sgwrth.cli.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;

public class FileLister {
    public static Optional<List<Path>> getFilepaths(String langArg, String dir) {
        final var fileExtension = Text.getExtension(langArg);

        if (fileExtension.isEmpty()) {
            MsgPrinter.printMsg(ErrorMessages.FILE_EXTENSION);
            return Optional.empty();
        }

        try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
            return Optional.of(stream
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(fileExtension.get()))
                .collect(Collectors.toList()));
        } catch (IOException e) {
            MsgPrinter.printMsg(ErrorMessages.FILEPATH_LIST);
            return Optional.empty();
        }
    }
}
