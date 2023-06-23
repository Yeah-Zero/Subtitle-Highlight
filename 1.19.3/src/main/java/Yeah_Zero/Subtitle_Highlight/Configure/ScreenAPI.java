package Yeah_Zero.Subtitle_Highlight.Configure;

import Yeah_Zero.Subtitle_Highlight.Util.ColorCode;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.gui.entries.MultiElementListEntry;
import me.shedaniel.clothconfig2.gui.entries.NestedListListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ScreenAPI implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (上级界面) -> {
            try {
                ConfigBuilder 构建器 = ConfigBuilder.create().setParentScreen(上级界面).setTitle(Text.translatable("subtitle_highlight.configure.title")).setDoesConfirmSave(false).setSavingRunnable(Manager::保存).setDefaultBackgroundTexture(new Identifier("minecraft", "textures/block/white_concrete.png"));
                构建器.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.general")).addEntry(构建器.entryBuilder().startBooleanToggle(Text.translatable("options.showSubtitles"), MinecraftClient.getInstance().options.getShowSubtitles().getValue()).setTooltip(Text.translatable("subtitle_highlight.configure.general.option.tooltip")).setDefaultValue(false).setSaveConsumer((新值) -> {
                    MinecraftClient.getInstance().options.getShowSubtitles().setValue(新值);
                    MinecraftClient.getInstance().options.write();
                }).build()).addEntry(构建器.entryBuilder().startLongField(Text.translatable("subtitle_highlight.configure.general.remove_delay"), Manager.配置项.最长持续时间).setTooltip(Text.translatable("subtitle_highlight.configure.general.remove_delay.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.remove_delay.tooltip_2")).setDefaultValue(3000).setMin(0).setSaveConsumer((新值) -> {
                    Manager.配置项.最长持续时间 = 新值;
                }).build()).addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle_highlight.configure.general.start_ratio"), Manager.配置项.起始比例).setTooltip(Text.translatable("subtitle_highlight.configure.general.start_ratio.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.start_ratio.tooltip_2")).setDefaultValue(1).setMax(1).setMin(0).setSaveConsumer((新值) -> {
                    Manager.配置项.起始比例 = 新值;
                }).build()).addEntry(构建器.entryBuilder().startDoubleField(Text.translatable("subtitle_highlight.configure.general.end_ratio"), Manager.配置项.终止比例).setTooltip(Text.translatable("subtitle_highlight.configure.general.end_ratio.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.end_ratio.tooltip_2")).setDefaultValue(0.29411764705882354).setMax(1).setMin(0).setSaveConsumer((新值) -> {
                    Manager.配置项.终止比例 = 新值;
                }).build()).addEntry(构建器.entryBuilder().startAlphaColorField(Text.translatable("subtitle_highlight.configure.general.background_color"), Manager.配置项.背景颜色).setTooltip(Text.translatable("subtitle_highlight.configure.general.background_color.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.background_color.tooltip_2")).setDefaultValue(0xcc000000).setSaveConsumer((新值) -> {
                    Manager.配置项.背景颜色 = 新值;
                }).build()).addEntry(构建器.entryBuilder().startFloatField(Text.translatable("subtitle_highlight.configure.general.scale"), Manager.配置项.缩放).setTooltip(Text.translatable("subtitle_highlight.configure.general.scale.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.scale.tooltip_2")).setDefaultValue(1).setSaveConsumer((新值) -> {
                    Manager.配置项.缩放 = 新值;
                }).build()).addEntry(构建器.entryBuilder().startFloatField(Text.translatable("subtitle_highlight.configure.general.bottom_margin"), Manager.配置项.底部边距).setTooltip(Text.translatable("subtitle_highlight.configure.general.bottom_margin.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.bottom_margin.tooltip_2")).setDefaultValue(30).setSaveConsumer((新值) -> {
                    Manager.配置项.底部边距 = 新值;
                }).build()).addEntry(构建器.entryBuilder().startFloatField(Text.translatable("subtitle_highlight.configure.general.side_margin"), Manager.配置项.侧边边距).setTooltip(Text.translatable("subtitle_highlight.configure.general.side_margin.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.side_margin.tooltip_2")).setDefaultValue(1).setSaveConsumer((新值) -> {
                    Manager.配置项.侧边边距 = 新值;
                }).build()).addEntry(构建器.entryBuilder().startBooleanToggle(Text.translatable("subtitle_highlight.configure.general.ikun"), Manager.配置项.iKun彩蛋).setTooltip(Text.translatable("subtitle_highlight.configure.general.ikun.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.ikun.tooltip_2")).setDefaultValue(true).setSaveConsumer((新值) -> {
                    Manager.配置项.iKun彩蛋 = 新值;
                }).build()).setDescription(new MutableText[]{Text.translatable("subtitle_highlight.configure.general.description")});
                SubCategoryBuilder 基本颜色设置 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color")).setTooltip(Text.translatable("subtitle_highlight.configure.general.color.tooltip")).setExpanded(true);
                基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.ambient"), ColorCode.class, Manager.配置项.基本颜色设置.环境).setDefaultValue(ColorCode.深蓝色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.环境 = 新值;
                }).build());
                SubCategoryBuilder 方块 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.block")).setExpanded(true);
                方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.generic"), ColorCode.class, Manager.配置项.基本颜色设置.方块.通用).setDefaultValue(ColorCode.白色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.方块.通用 = 新值;
                }).build());
                方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.interact"), ColorCode.class, Manager.配置项.基本颜色设置.方块.互动).setDefaultValue(ColorCode.天蓝色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.方块.互动 = 新值;
                }).build());
                方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.working"), ColorCode.class, Manager.配置项.基本颜色设置.方块.运作).setDefaultValue(ColorCode.湖蓝色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.方块.运作 = 新值;
                }).build());
                方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.dangerous"), ColorCode.class, Manager.配置项.基本颜色设置.方块.危险方块).setDefaultValue(ColorCode.红色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.方块.危险方块 = 新值;
                }).build());
                方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.crop"), ColorCode.class, Manager.配置项.基本颜色设置.方块.农作物).setDefaultValue(ColorCode.深绿色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.方块.农作物 = 新值;
                }).build());
                方块.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.other"), ColorCode.class, Manager.配置项.基本颜色设置.方块.方块_其它).setDefaultValue(ColorCode.深灰色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.方块.方块_其它 = 新值;
                }).build());
                基本颜色设置.add(方块.build());
                基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.enchant"), ColorCode.class, Manager.配置项.基本颜色设置.魔咒).setDefaultValue(ColorCode.紫色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.魔咒 = 新值;
                }).build());
                SubCategoryBuilder 实体 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.entity")).setExpanded(true);
                SubCategoryBuilder 生物 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.entity.mob")).setExpanded(true);
                SubCategoryBuilder 玩家 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player")).setExpanded(true);
                玩家.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player.attack"), ColorCode.class, Manager.配置项.基本颜色设置.实体.生物.玩家.攻击).setDefaultValue(ColorCode.金色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.生物.玩家.攻击 = 新值;
                }).build());
                玩家.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player.hurt"), ColorCode.class, Manager.配置项.基本颜色设置.实体.生物.玩家.受伤).setDefaultValue(ColorCode.深红色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.生物.玩家.受伤 = 新值;
                }).build());
                玩家.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player.other"), ColorCode.class, Manager.配置项.基本颜色设置.实体.生物.玩家.玩家_其它).setDefaultValue(ColorCode.白色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.生物.玩家.玩家_其它 = 新值;
                }).build());
                生物.add(玩家.build());
                生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.passive"), ColorCode.class, Manager.配置项.基本颜色设置.实体.生物.被动生物).setDefaultValue(ColorCode.绿色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.生物.被动生物 = 新值;
                }).build());
                生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.neutral"), ColorCode.class, Manager.配置项.基本颜色设置.实体.生物.中立生物).setDefaultValue(ColorCode.黄色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.生物.中立生物 = 新值;
                }).build());
                生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.hostile"), ColorCode.class, Manager.配置项.基本颜色设置.实体.生物.敌对生物).setDefaultValue(ColorCode.红色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.生物.敌对生物 = 新值;
                }).build());
                生物.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.boss"), ColorCode.class, Manager.配置项.基本颜色设置.实体.生物.头目生物).setDefaultValue(ColorCode.粉红色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.生物.头目生物 = 新值;
                }).build());
                实体.add(生物.build());
                实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.vehicle"), ColorCode.class, Manager.配置项.基本颜色设置.实体.载具).setDefaultValue(ColorCode.灰色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.载具 = 新值;
                }).build());
                实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.projectile"), ColorCode.class, Manager.配置项.基本颜色设置.实体.弹射物).setDefaultValue(ColorCode.金色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.弹射物 = 新值;
                }).build());
                实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.explosive"), ColorCode.class, Manager.配置项.基本颜色设置.实体.爆炸物).setDefaultValue(ColorCode.红色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.爆炸物 = 新值;
                }).build());
                实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.decoration"), ColorCode.class, Manager.配置项.基本颜色设置.实体.装饰品).setDefaultValue(ColorCode.灰色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.装饰品 = 新值;
                }).build());
                实体.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.other"), ColorCode.class, Manager.配置项.基本颜色设置.实体.实体_其它).setDefaultValue(ColorCode.深灰色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.实体.实体_其它 = 新值;
                }).build());
                基本颜色设置.add(实体.build());
                SubCategoryBuilder 物品 = 构建器.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.item")).setExpanded(true);
                物品.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.weapon"), ColorCode.class, Manager.配置项.基本颜色设置.物品.武器).setDefaultValue(ColorCode.金色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.物品.武器 = 新值;
                }).build());
                物品.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.armor"), ColorCode.class, Manager.配置项.基本颜色设置.物品.防具).setDefaultValue(ColorCode.深绿色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.物品.防具 = 新值;
                }).build());
                物品.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.tool"), ColorCode.class, Manager.配置项.基本颜色设置.物品.工具).setDefaultValue(ColorCode.蓝色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.物品.工具 = 新值;
                }).build());
                物品.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.other"), ColorCode.class, Manager.配置项.基本颜色设置.物品.物品_其它).setDefaultValue(ColorCode.白色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.物品.物品_其它 = 新值;
                }).build());
                基本颜色设置.add(物品.build());
                基本颜色设置.add(构建器.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.other"), ColorCode.class, Manager.配置项.基本颜色设置.其它).setDefaultValue(ColorCode.白色).setEnumNameProvider(ColorCode::颜色代码翻译).setSaveConsumer((新值) -> {
                    Manager.配置项.基本颜色设置.其它 = 新值;
                }).build());
                构建器.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.general")).addEntry(基本颜色设置.build());
                构建器.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.custom")).addEntry(new NestedListListEntry<>(Text.translatable("subtitle_highlight.configure.custom.list"), Manager.配置项.自定义列表, true, Optional::empty, (新值) -> {
                    Manager.配置项.自定义列表 = (ArrayList<Settings.Custom>) 新值;
                }, List::of, 构建器.entryBuilder().getResetButtonKey(), true, true, (元素, 条目) -> {
                    Settings.Custom 自定义项 = 元素 == null ? Manager.配置项.new Custom() : 元素;
                    return new MultiElementListEntry<>(Text.translatable("subtitle_highlight.configure.custom.entry"), 自定义项, List.of(构建器.entryBuilder().startStrField(Text.translatable("subtitle_highlight.configure.custom.entry.translation_key"), 自定义项.本地化键名).setTooltip(Text.translatable("subtitle_highlight.configure.custom.entry.translation_key.tooltip")).setDefaultValue("").setSaveConsumer((新值) -> {
                        自定义项.本地化键名 = 新值;
                    }).build(), 构建器.entryBuilder().startColorField(Text.translatable("subtitle_highlight.configure.custom.entry.color"), 自定义项.颜色).setTooltip(Text.translatable("subtitle_highlight.configure.custom.entry.color.tooltip_1"), Text.translatable("subtitle_highlight.configure.custom.entry.color.tooltip_2")).setDefaultValue(TextColor.fromRgb(0xffffff)).setSaveConsumer((新值) -> {
                        自定义项.颜色 = TextColor.fromRgb(新值);
                    }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.obfuscated"), 自定义项.随机).setDefaultValue(false).setSaveConsumer((新值) -> {
                        自定义项.随机 = 新值;
                    }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.bold"), 自定义项.粗体).setDefaultValue(false).setSaveConsumer((新值) -> {
                        自定义项.粗体 = 新值;
                    }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.strikethrough"), 自定义项.删除线).setDefaultValue(false).setSaveConsumer((新值) -> {
                        自定义项.删除线 = 新值;
                    }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.underline"), 自定义项.下划线).setDefaultValue(false).setSaveConsumer((新值) -> {
                        自定义项.下划线 = 新值;
                    }).build(), 构建器.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.italic"), 自定义项.斜体).setDefaultValue(false).setSaveConsumer((新值) -> {
                        自定义项.斜体 = 新值;
                    }).build()), true);
                }));
                构建器.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.custom")).setDescription(new MutableText[]{Text.translatable("subtitle_highlight.configure.custom.description")});
                return 构建器.build();
            } catch (NullPointerException e) {
                Manager.配置项 = new Settings();
                Manager.保存();
                return getModConfigScreenFactory().create(上级界面);
            }
        };
    }
}
