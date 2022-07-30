package io.github.ss3rg3.ping.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static String loadAsString(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(String content, String path) {
        try {
            Path configFile = Paths.get(path);
            Files.writeString(configFile, content,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
