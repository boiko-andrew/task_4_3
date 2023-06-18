package ru.netology;

import ru.netology.game_progress.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static final int BUFFER_SIZE = 8192;

    public static void openZip(String zipFullFileName, String targetPath) {
        byte[] buffer = new byte[BUFFER_SIZE];

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFullFileName))) {
            ZipEntry entry;
            String name;
            while ((entry = zipIn.getNextEntry()) != null) {
                name = entry.getName();
                String targetFullFileName = targetPath + "//" + name;

                FileOutputStream fileOut = new FileOutputStream(targetFullFileName);
                int len;
                while ((len = zipIn.read(buffer)) > 0) {
                    fileOut.write(buffer, 0, len);
                }

                fileOut.flush();
                zipIn.closeEntry();
                fileOut.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String datFullFileName) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(datFullFileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }

    public static void main(String[] args) {
        String zipFullFileName = "D://Games//savegames/zip.zip";
        String targetPath = "D://Games//savegames";
        String datFullFileName = "D://Games//savegames//gameProgressOne.dat";

        openZip(zipFullFileName, targetPath);
        openProgress(datFullFileName);
    }
}