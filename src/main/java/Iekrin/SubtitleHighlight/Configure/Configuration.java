package Iekrin.SubtitleHighlight.Configure;

import Iekrin.SubtitleHighlight.Mixin.SimpleOptionMixin;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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

    public static Screen 屏幕(Screen 上级界面) {
        ConfigBuilder 构建器 = ConfigBuilder.create().setParentScreen(上级界面).setTitle(Text.translatable("subtitle-highlight.configure.title")).setDoesConfirmSave(false).setSavingRunnable(Configuration::保存).setDefaultBackgroundTexture(new Identifier("minecraft", "textures/block/white_concrete.png"));
        构建器.setGlobalized(true);
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.category.general"))
                .addEntry(构建器.entryBuilder().startBooleanToggle(Text.translatable("options.showSubtitles"), MinecraftClient.getInstance().options.getShowSubtitles().getValue()).setDefaultValue(((SimpleOptionMixin<Boolean>) (Object) MinecraftClient.getInstance().options.getShowSubtitles()).获取默认值()).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.option.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.option.tooltip_2")).setSaveConsumer((新值) -> {
                    MinecraftClient.getInstance().options.getShowSubtitles().setValue(新值);
                    MinecraftClient.getInstance().options.write();
                }).build())
                .addEntry(构建器.entryBuilder().startLongField(Text.translatable("subtitle-highlight.configure.category.general.remove_delay"), 最长持续时间).setDefaultValue(3000).setMin(0).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_2")).setSaveConsumer((新值) -> {
                    最长持续时间 = 新值;
                }).build())
                .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.start_ratio"), 起始比例).setDefaultValue(1).setMax(1).setMin(0).setTooltip(Text.translatable("")).setSaveConsumer((新值) -> {
                    起始比例 = 新值;
                }).build())
                .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.end_ratio"), 终止比例).setDefaultValue(75 / 255d).setMax(1).setMin(0).setTooltip(Text.translatable("")).setSaveConsumer((新值) -> {
                    终止比例 = 新值;
                }).build()).setDescription(new MutableText[]{Text.translatable("subtitle-highlight.configure.category.general.description")});
        SubCategoryBuilder 基本颜色设置 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle-highlight.configure.category.general.sub_category.color")).setExpanded(true).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.sub_category.color.tooltip"));
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("环境")).build());
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("方块")).build());
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("魔咒")).build());
        SubCategoryBuilder 实体 = 构建器.entryBuilder().startSubCategory(Text.translatable("实体")).setExpanded(true);
        SubCategoryBuilder 生物 = 构建器.entryBuilder().startSubCategory(Text.translatable("生物")).setExpanded(true);
        生物.add(构建器.entryBuilder().startTextDescription(Text.translatable("玩家")).build());
        生物.add(构建器.entryBuilder().startTextDescription(Text.translatable("被动生物")).build());
        生物.add(构建器.entryBuilder().startTextDescription(Text.translatable("中立生物")).build());
        生物.add(构建器.entryBuilder().startTextDescription(Text.translatable("敌对生物")).build());
        生物.add(构建器.entryBuilder().startTextDescription(Text.translatable("头目生物")).build());
        实体.add(生物.build());
        实体.add(构建器.entryBuilder().startTextDescription(Text.translatable("载具")).build());
        实体.add(构建器.entryBuilder().startTextDescription(Text.translatable("弹射物")).build());
        实体.add(构建器.entryBuilder().startTextDescription(Text.translatable("其它")).build());
        基本颜色设置.add(实体.build());
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("事件")).build());
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("物品")).build());
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("粒子")).build());
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("用户界面")).build());
        基本颜色设置.add(构建器.entryBuilder().startTextDescription(Text.translatable("天气")).build());
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.category.general")).addEntry(基本颜色设置.build());
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.category.custom"))
                .addEntry(构建器.entryBuilder().startTextDescription(Text.literal("开发中……")).build());
        return 构建器.build();
    }
}