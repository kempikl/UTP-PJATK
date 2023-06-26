package UTP4_Win2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {
        Path startDir = Paths.get(dirName);
        Path resultFile = Paths.get(resultFileName);

        try {
            Files.createFile(resultFile);

            try (Stream<Path> paths = Files.walk(startDir).filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".txt"))) {
                paths.forEach(path -> {
                    try {
                        List<String> lines = Files.readAllLines(path, Charset.forName("Cp1250"));
                        Files.write(resultFile, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

