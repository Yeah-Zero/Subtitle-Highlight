package Iekrin.SubtitleHighlight.Configure;

import Iekrin.SubtitleHighlight.FormattingCode.ColorCode;
import Iekrin.SubtitleHighlight.Mixin.SimpleOptionMixin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public static Setting 配置项;
    private static Gson gson;

    public static void 保存() {
        try (FileWriter 写入 = new FileWriter(文件, StandardCharsets.UTF_8, false)) {
            写入.write(gson.toJson(配置项));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void 读取() {
        try (FileReader 读取 = new FileReader(文件, StandardCharsets.UTF_8)) {
            char[] 内容 = new char[(int) 文件.length()];
            读取.read(内容);
            gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
            配置项 = gson.fromJson(new String(内容).trim(), Setting.class);
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
                .addEntry(构建器.entryBuilder().startLongField(Text.translatable("subtitle-highlight.configure.category.general.remove_delay"), 配置项.最长持续时间).setDefaultValue(3000).setMin(0).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.remove_delay.tooltip_2")).setSaveConsumer((新值) -> {
                    配置项.最长持续时间 = 新值;
                }).build())
                .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.start_ratio"), 配置项.起始比例).setDefaultValue(1).setMax(1).setMin(0).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.start_ratio.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.start_ratio.tooltip_2")).setSaveConsumer((新值) -> {
                    配置项.起始比例 = 新值;
                }).build())
                .addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.category.general.end_ratio"), 配置项.终止比例).setDefaultValue(0.29411764705882354).setMax(1).setMin(0).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.end_ratio.tooltip_1"), Text.translatable("subtitle-highlight.configure.category.general.end_ratio.tooltip_2")).setSaveConsumer((新值) -> {
                    配置项.终止比例 = 新值;
                }).build()).setDescription(new MutableText[]{Text.translatable("subtitle-highlight.configure.category.general.description")});
        SubCategoryBuilder 基本颜色设置 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle-highlight.configure.category.general.sub_category.color")).setExpanded(true).setTooltip(Text.translatable("subtitle-highlight.configure.category.general.sub_category.color.tooltip"));
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("环境"), ColorCode.class, ColorCode.白色).build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("方块"), ColorCode.class, ColorCode.白色).build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("魔咒"), ColorCode.class, ColorCode.白色).build());
        SubCategoryBuilder 实体 = 构建器.entryBuilder().startSubCategory(Text.translatable("实体")).setExpanded(true);
        SubCategoryBuilder 生物 = 构建器.entryBuilder().startSubCategory(Text.translatable("生物")).setExpanded(true);
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("玩家"), ColorCode.class, 配置项.基本颜色设置.实体.生物.玩家).setDefaultValue(ColorCode.白色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.玩家 = 新值;
        }).build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("被动生物"), ColorCode.class, 配置项.基本颜色设置.实体.生物.被动生物).setDefaultValue(ColorCode.绿色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.被动生物 = 新值;
        }).build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("中立生物"), ColorCode.class, 配置项.基本颜色设置.实体.生物.中立生物).setDefaultValue(ColorCode.黄色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.中立生物 = 新值;
        }).build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("敌对生物"), ColorCode.class, 配置项.基本颜色设置.实体.生物.敌对生物).setDefaultValue(ColorCode.红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.敌对生物 = 新值;
        }).build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("头目生物"), ColorCode.class, 配置项.基本颜色设置.实体.生物.头目生物).setDefaultValue(ColorCode.粉红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.头目生物 = 新值;
        }).build());
        实体.add(生物.build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("载具"), ColorCode.class, 配置项.基本颜色设置.实体.载具).setDefaultValue(ColorCode.灰色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.载具 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("弹射物"), ColorCode.class, 配置项.基本颜色设置.实体.弹射物).setDefaultValue(ColorCode.金色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.弹射物 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("爆炸物"), ColorCode.class, 配置项.基本颜色设置.实体.爆炸物).setDefaultValue(ColorCode.红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.爆炸物 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("装饰品"), ColorCode.class, 配置项.基本颜色设置.实体.装饰品).setDefaultValue(ColorCode.灰色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.装饰品 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("其它"), ColorCode.class, 配置项.基本颜色设置.实体.其它).setDefaultValue(ColorCode.灰色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.其它 = 新值;
        }).build());
        基本颜色设置.add(实体.build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("事件"), ColorCode.class, ColorCode.白色).build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("物品"), ColorCode.class, ColorCode.白色).build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("粒子"), ColorCode.class, ColorCode.白色).build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("用户界面"), ColorCode.class, ColorCode.白色).build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("天气"), ColorCode.class, ColorCode.白色).build());
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.category.general")).addEntry(基本颜色设置.build());
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.category.custom"))
                .addEntry(构建器.entryBuilder().startTextDescription(Text.literal("开发中……")).build());
        return 构建器.build();
    }
}