package Iekrin.SubtitleHighlight.Configure;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.text.Text;

public class Screen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (上级界面) -> {
            ConfigBuilder 构建器 = ConfigBuilder.create().setParentScreen(上级界面).setTitle(Text.literal("配置屏幕")).setDoesConfirmSave(false).setSavingRunnable(() -> {
                Configuration.保存();
            });
            构建器.getOrCreateCategory(Text.literal("分类名称")).addEntry(构建器.entryBuilder().startStrField(Text.literal("选项"), Configuration.字符串).setDefaultValue("默认值").setTooltip(Text.literal("提示")).setSaveConsumer((新值) -> {
                Configuration.字符串 = 新值;
            }).build());
            return 构建器.build();
        };
    }
}
