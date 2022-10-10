package Iekrin.ExampleMod.Configure;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class Configuration {
    private static final File 文件 = new File(FabricLoader.getInstance().getConfigDir().toFile().getPath() + "\\配置文件.json");
    public static String 字符串 = "初始值";

    public static void 保存() {

    }

    public static void 读取() {

    }
}
