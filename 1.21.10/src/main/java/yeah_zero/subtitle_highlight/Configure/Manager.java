package Yeah_Zero.Subtitle_Highlight.Configure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Manager {
    public static final File file = new File(FabricLoader.getInstance().getConfigDir().toFile().getPath(), "subtitle_highlight.json");
    public static Settings settings;
    public static Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

    public static void save() {
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, false)) {
            writer.write(gson.toJson(settings));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        if (!file.exists() || !file.isFile()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            settings = new Settings();
            save();
        } else {
            try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
                char[] content = new char[(int) file.length()];
                reader.read(content);
                try {
                    settings = gson.fromJson(new String(content).trim(), Settings.class);
                } catch (JsonSyntaxException e) {
                    settings = new Settings();
                    save();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void exportConfig(File exportFile) {
        try (FileWriter writer = new FileWriter(exportFile, StandardCharsets.UTF_8, false)) {
            writer.write(gson.toJson(settings));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void importConfig(File importFile) {
        if (!importFile.exists() || !importFile.isFile()) {
            throw new IllegalArgumentException("Import file does not exist or is not a file");
        }
        try (FileReader reader = new FileReader(importFile, StandardCharsets.UTF_8)) {
            char[] content = new char[(int) importFile.length()];
            reader.read(content);
            try {
                settings = gson.fromJson(new String(content).trim(), Settings.class);
                save();
            } catch (JsonSyntaxException e) {
                throw new IllegalArgumentException("Invalid configuration file format", e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}