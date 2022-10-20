package Iekrin.SubtitleHighlight.Initializer;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
    public static final Logger 记录器 = LoggerFactory.getLogger("示例模组");

	@Override
	public void onInitialize() {
        记录器.info("Hello Fabric world!");
	}
}
