package ru.netology;

import ru.netology.game_progress.GameProgress;

import java.io.*;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final GameProgress[] gameProgresses = {
            new GameProgress(17, 28, 3, 47.3),
            new GameProgress(3, 14, 1, 23.2),
            new GameProgress(21, 35, 5, 87.9)
    };

    public static void saveGame(String[] datFullFileNames) {
        for (int i = 0; i < 3; i++) {
            try (FileOutputStream fos = new FileOutputStream(datFullFileNames[i]);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(gameProgresses[i]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void zipFiles(String zipFullFileName, String[] datFullFileNames) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFullFileName))) {
            for (String datFullFileName : datFullFileNames) {
                try (FileInputStream fis = new FileInputStream(datFullFileName)) {
                    String fileName = Paths.get(datFullFileName).getFileName().toString();
                    ZipEntry entry = new ZipEntry(fileName);
                    zipOut.putNextEntry(entry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zipOut.write(buffer);
                    zipOut.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        for (String datFullFileName : datFullFileNames) {
            File file = new File(datFullFileName);
            try {
                file.delete();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String[] datFullFileNames = {
                "D://Games//savegames//gameProgressOne.dat",
                "D://Games//savegames//gameProgressTwo.dat",
                "D://Games//savegames//gameProgressThree.dat",
        };
        String zipFullFileName = "D://Games//savegames/zip.zip";

        saveGame(datFullFileNames);
        zipFiles(zipFullFileName, datFullFileNames);
    }
}