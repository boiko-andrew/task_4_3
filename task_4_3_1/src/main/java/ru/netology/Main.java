package ru.netology;

import ru.netology.logger.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void createDirectory(String path) {
        String message;
        boolean result;
        Logger logger = Logger.getInstance();

        File file = new File(path);
        if (file.exists()) {
            message = "Directory \"" + path + "\" already exists.";
        } else {
            result = file.mkdir();
            if (result) {
                message = "Directory \"" + path + "\" created.";
            } else {
                message = "Directory \"" + path + "\" creation error." +
                        "Game installation aborted";
            }
        }
        logger.log(message);
    }

    public static void createFile(String fullName) {
        String message;
        boolean result = true;
        Logger logger = Logger.getInstance();

        Path fullPath = Paths.get(fullName);
        String name = fullPath.getFileName().toString();
        String path = fullPath.getParent().toString().replace("\\", "\\\\");

        File file = new File(path, name);
        if (file.exists()) {
            message = "File \"" + fullName + "\" already exists.";
        } else {
            file = new File(path, name);
            try {
                result = file.createNewFile();
            } catch (IOException e) {
                result = false;
            } finally {
                if (result) {
                    message = "File \"" + fullName + "\" created.";
                } else {
                    message = "File \"" + fullName + "\" creation error." +
                            "Game installation aborted";
                }
            }
        }
        logger.log(message);
    }

    public static void saveLog(String logFullFileName) {
        String message;
        Logger logger = Logger.getInstance();

        File file = new File(logFullFileName);
        if (file.exists() && file.isFile()) {
            try (FileWriter writer = new FileWriter(file, false)) {
                writer.write(logger.getLog());
                message = "Log writing is successful.";
            } catch (IOException e) {
                System.out.println(e.getMessage());
                message = "Log writing failed.";
            }
        } else {
            message = "Log writing failed.";
        }
        logger.log(message);
    }

    public static void main(String[] args) {
        String gamesPath = "D://Games";
        String[] dirs = {"//src", "//res", "//savegames", "//temp",
                "//src//main", "//src//test", "//res//drawables", "//res//vectors", "//res//icons"};
        String[] files = {"//src//main//Main.java", "//src/main/Utils.java",
                "//temp//temp.txt"};
        String logFullFileName = gamesPath + "//temp//temp.txt";

        for (String dir : dirs) {
            createDirectory(gamesPath + dir);
        }

        for (String file : files) {
            createFile(gamesPath + file);
        }

        saveLog(logFullFileName);
    }
}