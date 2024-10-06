import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }


    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, List<String> filePaths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filePaths) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry entry = new ZipEntry(new File(filePath).getName());
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    System.out.println("Error zipping file: " + filePath + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating zip file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String saveDir = "C://Users/Falexcom/Documents/JavaCore/Games/savegames";
        GameProgress game1 = new GameProgress(100, 2, 10, 150.5);
        GameProgress game2 = new GameProgress(80, 3, 15, 200.0);
        GameProgress game3 = new GameProgress(50, 5, 20, 300.5);

        List<String> saveFiles = new ArrayList<>();
        saveFiles.add(saveDir + "/save1.dat");
        saveFiles.add(saveDir + "/save2.dat");
        saveFiles.add(saveDir + "/save3.dat");

        saveGame(saveFiles.get(0), game1);
        saveGame(saveFiles.get(1), game2);
        saveGame(saveFiles.get(2), game3);

        zipFiles(saveDir + "/saves.zip", saveFiles);

        for (String filePath : saveFiles) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Deleted file: " + filePath);
            } else {
                System.out.println("Failed to delete file: " + filePath);
            }
        }
    }
}