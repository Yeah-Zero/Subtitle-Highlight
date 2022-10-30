package Yeah_Zero.Subtitle_Highlight.Configure;

import Yeah_Zero.Subtitle_Highlight.FormattingCode.ColorCode;
import Yeah_Zero.Subtitle_Highlight.Mixin.SimpleOptionMixin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.gui.entries.MultiElementListEntry;
import me.shedaniel.clothconfig2.gui.entries.NestedListListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Configuration {
    public static final File 文件 = new File(FabricLoader.getInstance().getConfigDir().toFile().getPath(), "字幕高亮.json");
    public static Setting 配置项;
    public static Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

    public static void 保存() {
        try (FileWriter 写入 = new FileWriter(文件, StandardCharsets.UTF_8, false)) {
            写入.write(gson.toJson(配置项));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void 加载() {
        if (!文件.exists() || !文件.isFile()) {
            文件.delete();
            try {
                文件.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            配置项 = new Setting();
            保存();
        } else {
            try (FileReader 读取 = new FileReader(文件, StandardCharsets.UTF_8)) {
                char[] 内容 = new char[(int) 文件.length()];
                读取.read(内容);
                try {
                    配置项 = gson.fromJson(new String(内容).trim(), Setting.class);
                } catch (JsonSyntaxException e) {
                    配置项 = new Setting();
                    保存();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static Screen 屏幕(Screen 上级界面) {
        ConfigBuilder 构建器 = ConfigBuilder.create().setParentScreen(上级界面).setTitle(Text.translatable("subtitle-highlight.configure.title")).setDoesConfirmSave(false).setSavingRunnable(Configuration::保存).setDefaultBackgroundTexture(new Identifier("minecraft", "textures/block/white_concrete.png"));
        构建器.setGlobalized(true);
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.general")).addEntry(构建器.entryBuilder().startBooleanToggle(Text.translatable("options.showSubtitles"), MinecraftClient.getInstance().options.getShowSubtitles().getValue()).setTooltip(Text.translatable("subtitle-highlight.configure.general.option.tooltip")).setDefaultValue(((SimpleOptionMixin<Boolean>) (Object) MinecraftClient.getInstance().options.getShowSubtitles()).获取默认值()).setSaveConsumer((新值) -> {
            MinecraftClient.getInstance().options.getShowSubtitles().setValue(新值);
            MinecraftClient.getInstance().options.write();
        }).build()).addEntry(构建器.entryBuilder().startLongField(Text.translatable("subtitle-highlight.configure.general.remove_delay"), 配置项.最长持续时间).setTooltip(Text.translatable("subtitle-highlight.configure.general.remove_delay.tooltip_1"), Text.translatable("subtitle-highlight.configure.general.remove_delay.tooltip_2")).setDefaultValue(3000).setMin(0).setSaveConsumer((新值) -> {
            配置项.最长持续时间 = 新值;
        }).build()).addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.general.start_ratio"), 配置项.起始比例).setTooltip(Text.translatable("subtitle-highlight.configure.general.start_ratio.tooltip_1"), Text.translatable("subtitle-highlight.configure.general.start_ratio.tooltip_2")).setDefaultValue(1).setMax(1).setMin(0).setSaveConsumer((新值) -> {
            配置项.起始比例 = 新值;
        }).build()).addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle-highlight.configure.general.end_ratio"), 配置项.终止比例).setTooltip(Text.translatable("subtitle-highlight.configure.general.end_ratio.tooltip_1"), Text.translatable("subtitle-highlight.configure.general.end_ratio.tooltip_2")).setDefaultValue(0.29411764705882354).setMax(1).setMin(0).setSaveConsumer((新值) -> {
            配置项.终止比例 = 新值;
        }).build()).setDescription(new MutableText[]{Text.translatable("subtitle-highlight.configure.general.description")});
        SubCategoryBuilder 基本颜色设置 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle-highlight.configure.general.color")).setTooltip(Text.translatable("subtitle-highlight.configure.general.color.tooltip")).setExpanded(true);
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.ambient"), ColorCode.class, 配置项.基本颜色设置.环境).setDefaultValue(ColorCode.深蓝色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.环境 = 新值;
        }).build());
        SubCategoryBuilder 方块 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle-highlight.configure.general.color.block")).setExpanded(true);
        方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.block.generic"), ColorCode.class, 配置项.基本颜色设置.方块.通用).setDefaultValue(ColorCode.白色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.方块.通用 = 新值;
        }).build());
        方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.block.interact"), ColorCode.class, 配置项.基本颜色设置.方块.互动).setDefaultValue(ColorCode.天蓝色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.方块.互动 = 新值;
        }).build());
        方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.block.run"), ColorCode.class, 配置项.基本颜色设置.方块.运作).setDefaultValue(ColorCode.湖蓝色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.方块.运作 = 新值;
        }).build());
        方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.block.dangerous"), ColorCode.class, 配置项.基本颜色设置.方块.危险方块).setDefaultValue(ColorCode.红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.方块.危险方块 = 新值;
        }).build());
        方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.block.crop"), ColorCode.class, 配置项.基本颜色设置.方块.农作物).setDefaultValue(ColorCode.深绿色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.方块.农作物 = 新值;
        }).build());
        方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.block.other"), ColorCode.class, 配置项.基本颜色设置.方块.方块_其它).setDefaultValue(ColorCode.深灰色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.方块.方块_其它 = 新值;
        }).build());
        基本颜色设置.add(方块.build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.enchant"), ColorCode.class, 配置项.基本颜色设置.魔咒).setDefaultValue(ColorCode.紫色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.魔咒 = 新值;
        }).build());
        SubCategoryBuilder 实体 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle-highlight.configure.general.color.entity")).setExpanded(true);
        SubCategoryBuilder 生物 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle-highlight.configure.general.color.entity.mob")).setExpanded(true);
        SubCategoryBuilder 玩家 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.player")).setExpanded(true);
        玩家.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.player.attack"), ColorCode.class, 配置项.基本颜色设置.实体.生物.玩家.攻击).setDefaultValue(ColorCode.金色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.玩家.攻击 = 新值;
        }).build());
        玩家.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.player.hurt"), ColorCode.class, 配置项.基本颜色设置.实体.生物.玩家.受伤).setDefaultValue(ColorCode.深红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.玩家.受伤 = 新值;
        }).build());
        玩家.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.player.other"), ColorCode.class, 配置项.基本颜色设置.实体.生物.玩家.玩家_其它).setDefaultValue(ColorCode.白色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.玩家.玩家_其它 = 新值;
        }).build());
        生物.add(玩家.build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.passive"), ColorCode.class, 配置项.基本颜色设置.实体.生物.被动生物).setDefaultValue(ColorCode.绿色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.被动生物 = 新值;
        }).build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.neutral"), ColorCode.class, 配置项.基本颜色设置.实体.生物.中立生物).setDefaultValue(ColorCode.黄色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.中立生物 = 新值;
        }).build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.hostile"), ColorCode.class, 配置项.基本颜色设置.实体.生物.敌对生物).setDefaultValue(ColorCode.红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.敌对生物 = 新值;
        }).build());
        生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.mob.boss"), ColorCode.class, 配置项.基本颜色设置.实体.生物.头目生物).setDefaultValue(ColorCode.粉红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.生物.头目生物 = 新值;
        }).build());
        实体.add(生物.build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.vehicle"), ColorCode.class, 配置项.基本颜色设置.实体.载具).setDefaultValue(ColorCode.灰色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.载具 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.projectile"), ColorCode.class, 配置项.基本颜色设置.实体.弹射物).setDefaultValue(ColorCode.金色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.弹射物 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.explosive"), ColorCode.class, 配置项.基本颜色设置.实体.爆炸物).setDefaultValue(ColorCode.红色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.爆炸物 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.decoration"), ColorCode.class, 配置项.基本颜色设置.实体.装饰品).setDefaultValue(ColorCode.灰色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.装饰品 = 新值;
        }).build());
        实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.entity.other"), ColorCode.class, 配置项.基本颜色设置.实体.实体_其它).setDefaultValue(ColorCode.深灰色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.实体.实体_其它 = 新值;
        }).build());
        基本颜色设置.add(实体.build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.item"), ColorCode.class, ColorCode.白色).build());
        基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle-highlight.configure.general.color.other"), ColorCode.class, 配置项.基本颜色设置.其它).setDefaultValue(ColorCode.白色).setSaveConsumer((新值) -> {
            配置项.基本颜色设置.其它 = 新值;
        }).build());
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.general")).addEntry(基本颜色设置.build());
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.custom")).addEntry(new NestedListListEntry<>(Text.translatable("subtitle-highlight.configure.custom.list"), 配置项.自定义列表, true, Optional::empty, (新值) -> {
            配置项.自定义列表 = (ArrayList<Setting.Custom>) 新值;
        }, List::of, 构建器.entryBuilder().getResetButtonKey(), true, true, (元素, 条目) -> {
            Setting.Custom 自定义项 = 元素 == null ? 配置项.new Custom() : 元素;
            return new MultiElementListEntry<>(Text.translatable("subtitle-highlight.configure.custom.entry"), 自定义项, List.of(构建器.entryBuilder().startStrField(Text.translatable("subtitle-highlight.configure.custom.entry.translation_key"), 自定义项.本地化键名).setTooltip(Text.translatable("subtitle-highlight.configure.custom.entry.translation_key.tooltip")).setDefaultValue("").setSaveConsumer((新值) -> {
                自定义项.本地化键名 = 新值;
            }).build(), 构建器.entryBuilder().startColorField(Text.translatable("subtitle-highlight.configure.custom.entry.color"), 自定义项.颜色).setTooltip(Text.translatable("subtitle-highlight.configure.custom.entry.color.tooltip")).setDefaultValue(TextColor.fromRgb(0xffffff)).setSaveConsumer((新值) -> {
                自定义项.颜色 = TextColor.fromRgb(新值);
            }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("subtitle-highlight.configure.custom.entry.obfuscated"), 自定义项.随机).setDefaultValue(false).setSaveConsumer((新值) -> {
                自定义项.随机 = 新值;
            }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("subtitle-highlight.configure.custom.entry.bold"), 自定义项.粗体).setDefaultValue(false).setSaveConsumer((新值) -> {
                自定义项.粗体 = 新值;
            }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("subtitle-highlight.configure.custom.entry.strikethrough"), 自定义项.删除线).setDefaultValue(false).setSaveConsumer((新值) -> {
                自定义项.删除线 = 新值;
            }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("subtitle-highlight.configure.custom.entry.underline"), 自定义项.下划线).setDefaultValue(false).setSaveConsumer((新值) -> {
                自定义项.下划线 = 新值;
            }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("subtitle-highlight.configure.custom.entry.italic"), 自定义项.斜体).setDefaultValue(false).setSaveConsumer((新值) -> {
                自定义项.斜体 = 新值;
            }).build()), true);
        }));
        构建器.getOrCreateCategory(Text.translatable("subtitle-highlight.configure.custom")).setDescription(new MutableText[]{Text.translatable("subtitle-highlight.configure.custom.description")});
        return 构建器.build();
    }
}
