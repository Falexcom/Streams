import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GameLoader {

    public static void openZip(String zipFilePath, String extractToPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String filePath = extractToPath + File.separator + entry.getName();
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            System.out.println("Error unzipping file: " + e.getMessage());
        }
    }

    public static GameProgress openProgress(String filePath) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game progress: " + e.getMessage());
        }
        return gameProgress;
    }

    public static void main(String[] args) {
        String zipFilePath = "C://Users/Falexcom/Documents/JavaCore/Games/savegames/saves.zip";
        String extractToPath = "C://Users/Falexcom/Documents/JavaCore/Games/savegames";

        openZip(zipFilePath, extractToPath);

        String saveFilePath = extractToPath + "/save2.dat";
        GameProgress gameProgress = openProgress(saveFilePath);

        if (gameProgress != null) {
            System.out.println(gameProgress);
        }
    }
}