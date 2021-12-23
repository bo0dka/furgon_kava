package ua.edu.nulp.kava.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileOps {
    public static File getDbConfigFile() {
        return new File("db.config");
    }

    public static boolean writeFile(File file, String content) {
        return writeFile(file, content, false);
    }

    public static boolean writeFile(File file, String content, boolean append) {
        try (
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), StandardCharsets.UTF_8));
        ) {
            bw.write(content);

            return true;
        } catch (Exception ex) {
            System.err.println("Error while writing file " + file.getAbsolutePath() + ": " + ex.getMessage());
        }

        return false;
    }

    public static List<String> getFileLines(File file) {
        try {
            return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            System.err.println("Error while reading file " + file.getAbsolutePath() + ": " + ex.getMessage());
        }

        return null;
    }

    public static String getFileContent(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            System.err.println("Error while reading file " + file.getAbsolutePath() + ": " + ex.getMessage());
        }

        return null;
    }
}
