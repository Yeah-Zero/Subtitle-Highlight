package Iekrin.SubtitleHighlight.Configure;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.text.Text;

public class Screen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (上级界面) -> {
            ConfigBuilder 构建器 = ConfigBuilder.create().setParentScreen(上级界面).setTitle(Text.translatable("subtitle-highlight.configure.title")).setDoesConfirmSave(false).setSavingRunnable(Configuration::保存);
            构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure_screen.category.general"))
                    .addEntry(构建器.entryBuilder().startLongField(Text.translatable("subtitle-highlight.configure.category.general.remove_delay"), Configuration.最长持续时间).setDefaultValue(3000).setMin(0).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_2")).setSaveConsumer((新值) -> {
                        Configuration.最长持续时间 = 新值;
                    }).build())
                    .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.start_ratio"), Configuration.起始比例).setDefaultValue(1).setMax(1).setMin(0).setTooltip(Text.translatable("")).setSaveConsumer((新值) -> {
                        Configuration.起始比例 = 新值;
                    }).build())
                    .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.end_ratio"), Configuration.终止比例).setDefaultValue(75 / 255d).setMax(1).setMin(0).setTooltip(Text.translatable("")).setSaveConsumer((新值) -> {
                        Configuration.终止比例 = 新值;
                    }).build());
            return 构建器.build();
        };
    }
}
