package Iekrin.SubtitleHighlight.Configure;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Configuration {
    public static final File 文件 = new File(FabricLoader.getInstance().getConfigDir().toFile().getPath(), "字幕高亮.json");
    public static long 最长持续时间 = 3000;
    public static double 起始比例 = 1;
    public static double 终止比例 = 75 / 255d;

    enum 颜色类型 {
        a, b, c, d;
    }

    public static void 保存() {
        try (FileWriter 写入 = new FileWriter(文件, StandardCharsets.UTF_8, false)) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void 读取() {
        try (FileReader 读取 = new FileReader(文件, StandardCharsets.UTF_8)) {
            char[] 内容 = new char[(int) 文件.length()];
            读取.read(内容);
            System.out.println(内容);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
