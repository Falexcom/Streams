import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameInstaller {

    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();

        String baseDir = "C://Users/Falexcom/Documents/JavaCore/Games";

        String[] directories = {
                baseDir + "/src",
                baseDir + "/res",
                baseDir + "/savegames",
                baseDir + "/temp",
                baseDir + "/src/main",
                baseDir + "/src/test",
                baseDir + "/res/drawables",
                baseDir + "/res/vectors",
                baseDir + "/res/icons"
        };

        String[] files = {
                baseDir + "/src/main/Main.java",
                baseDir + "/src/main/Utils.java",
                baseDir + "/temp/temp.txt"
        };

        for (String dir : directories) {
            File directory = new File(dir);
            if (directory.mkdir()) {
                log.append("Directory created: ").append(dir).append("\n");
            } else {
                log.append("Failed to create directory: ").append(dir).append("\n");
            }
        }

        for (String filePath : files) {
            File file = new File(filePath);
            try {
                if (file.createNewFile()) {
                    log.append("File created: ").append(filePath).append("\n");
                } else {
                    log.append("Failed to create file: ").append(filePath).append("\n");
                }
            } catch (IOException e) {
                log.append("Exception creating file ").append(filePath).append(": ").append(e.getMessage()).append("\n");
            }
        }

        try (FileWriter writer = new FileWriter(baseDir + "/temp/temp.txt")) {
            writer.write(log.toString());
        } catch (IOException e) {
            System.out.println("Error writing log file: " + e.getMessage());
        }
    }
}