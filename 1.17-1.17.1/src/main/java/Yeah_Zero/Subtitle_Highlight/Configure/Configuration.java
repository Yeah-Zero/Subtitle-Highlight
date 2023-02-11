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

public class Configuration {
    public static final File 文件 = new File(FabricLoader.getInstance().getConfigDir().toFile().getPath(), "subtitle_highlight.json");
    public static Settings 配置项;
    public static Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

    public static void 保存() {
        try (FileWriter 写入 = new FileWriter(文件, StandardCharsets.UTF_8, false)) {
            写入.write(gson.toJson(配置项));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void 加载() {
        if (!文件.exists() || !文件.isFile()) {
            文件.delete();
            try {
                文件.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            配置项 = new Settings();
            保存();
        } else {
            try (FileReader 读取 = new FileReader(文件, StandardCharsets.UTF_8)) {
                char[] 内容 = new char[(int) 文件.length()];
                读取.read(内容);
                try {
                    配置项 = gson.fromJson(new String(内容).trim(), Settings.class);
                } catch (JsonSyntaxException e) {
                    配置项 = new Settings();
                    保存();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
