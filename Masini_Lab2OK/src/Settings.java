import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static Settings instance;
    private final String repoType;
    private final String repoFile;
    private final String repoFile2;

    private Settings(String repoType, String repoFile,String repoFile2) {
        this.repoType = repoType;
        this.repoFile = repoFile;
        this.repoFile2=repoFile2;
    }

    public String getRepoFile() {
        return repoFile;
    }
    public String getRepoFile2()
    {
        return repoFile2;
    }

    public String getRepoType() {
        return repoType;
    }

    private static Properties loadSettings() {
        try (FileReader fr = new FileReader("settings.properties")) {
            Properties settings = new Properties();
            settings.load(fr);
            return settings;
        } catch (IOException e) {
            // Tratează sau afișează detaliile excepției pentru a diagnostica problema
            e.printStackTrace();
            throw new RuntimeException("Error loading settings from settings.properties", e);
        }
    }

    public static synchronized Settings getInstance() throws IOException {

        if (instance == null) {
            synchronized (Settings.class) {
                if (instance == null) {
                    Properties properties = loadSettings();
                    instance = new Settings(properties.getProperty("Repository"), properties.getProperty("RepoFile"),properties.getProperty("RepoFile2"));
                }
            }
        }
        return instance;
    }
}