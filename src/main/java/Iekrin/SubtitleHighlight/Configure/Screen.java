package Iekrin.SubtitleHighlight.Configure;

import Iekrin.SubtitleHighlight.Mixin.SimpleOptionMixin;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class Screen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (上级界面) -> {
            ConfigBuilder 构建器 = ConfigBuilder.create().setParentScreen(上级界面).setTitle(Text.translatable("subtitle-highlight.configure.title")).setDoesConfirmSave(false).setSavingRunnable(Configuration::保存);
            构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.category.general"))
                    .addEntry(构建器.entryBuilder().startBooleanToggle(Text.translatable("options.showSubtitles"), MinecraftClient.getInstance().options.getShowSubtitles().getValue()).setDefaultValue(((SimpleOptionMixin<Boolean>) (Object) MinecraftClient.getInstance().options.getShowSubtitles()).获取默认值()).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.option.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.option.tooltip_2")).setSaveConsumer((新值) -> {
                        MinecraftClient.getInstance().options.getShowSubtitles().setValue(新值);
                        MinecraftClient.getInstance().options.write();
                    }).build())
                    .addEntry(构建器.entryBuilder().startLongField(Text.translatable("subtitle-highlight.configure.category.general.remove_delay"), Configuration.最长持续时间).setDefaultValue(3000).setMin(0).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_2")).setSaveConsumer((新值) -> {
                        Configuration.最长持续时间 = 新值;
                    }).build())
                    .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.start_ratio"), Configuration.起始比例).setDefaultValue(1).setMax(1).setMin(0).setTooltip(Text.translatable("")).setSaveConsumer((新值) -> {
                        Configuration.起始比例 = 新值;
                    }).build())
                    .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.end_ratio"), Configuration.终止比例).setDefaultValue(75 / 255d).setMax(1).setMin(0).setTooltip(Text.translatable("")).setSaveConsumer((新值) -> {
                        Configuration.终止比例 = 新值;
                    }).build())
                    .setDescription(new MutableText[]{Text.translatable("subtitle-highlight.configure.category.general.description")});
            构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.category.custom"))
                    .addEntry(构建器.entryBuilder().startLongField(Text.translatable("subtitle-highlight.configure.category.general.remove_delay"), Configuration.最长持续时间).setDefaultValue(3000).setMin(0).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_2")).setSaveConsumer((新值) -> {
                        Configuration.最长持续时间 = 新值;
                    }).build());
            return 构建器.build();
        };
    }
}
