package Iekrin.SubtitleHighlight.Initializer;

import Iekrin.SubtitleHighlight.Configure.Configuration;
import Iekrin.SubtitleHighlight.Configure.Setting;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        try {
            if (!Configuration.文件.exists()) {
                Configuration.文件.createNewFile();
                Configuration.配置项 = new Setting();
                Configuration.保存();
            } else if (Configuration.文件.isDirectory()) {
                Configuration.文件.delete();
                Configuration.文件.createNewFile();
                Configuration.配置项 = new Setting();
                Configuration.保存();
            } else {
                Configuration.读取();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
