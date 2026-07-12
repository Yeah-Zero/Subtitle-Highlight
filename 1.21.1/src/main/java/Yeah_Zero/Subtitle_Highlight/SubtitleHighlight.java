package Yeah_Zero.Subtitle_Highlight;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubtitleHighlight implements ModInitializer {
	public static final String MOD_ID = "subtitle_highlight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Subtitle Highlight loaded!");
	}
}