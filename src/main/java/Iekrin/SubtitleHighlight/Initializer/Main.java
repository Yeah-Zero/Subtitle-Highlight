package Iekrin.SubtitleHighlight.Initializer;

import Iekrin.SubtitleHighlight.Configure.Configuration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main implements ModInitializer {
    public static final Logger 记录器 = LoggerFactory.getLogger("字幕高亮");

    @Override
    public void onInitialize() {
        try {
            if (!Configuration.文件.exists()) {
                Configuration.文件.createNewFile();
            } else if (Configuration.文件.isDirectory()) {
                Configuration.文件.delete();
                Configuration.文件.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Configuration.读取();
    }
}
