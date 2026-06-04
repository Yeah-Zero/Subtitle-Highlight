package Yeah_Zero.Subtitle_Highlight.api;

import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SubtitleAPI {
    private static final List<SubtitleProcessor> processors = new ArrayList<>();

    /**
     * 注册字幕处理器
     * @param processor 字幕处理器
     */
    public static void registerProcessor(SubtitleProcessor processor) {
        processors.add(processor);
    }

    /**
     * 处理字幕
     * @param text 原始字幕文本
     * @param settings 配置
     * @return 处理后的字幕文本
     */
    public static Text processSubtitle(Text text, Settings settings) {
        Text processedText = text;
        for (SubtitleProcessor processor : processors) {
            processedText = processor.process(processedText, settings);
        }
        return processedText;
    }

    /**
     * 字幕处理器接口
     */
    public interface SubtitleProcessor {
        /**
         * 处理字幕
         * @param text 原始字幕文本
         * @param settings 配置
         * @return 处理后的字幕文本
         */
        Text process(Text text, Settings settings);
    }
}