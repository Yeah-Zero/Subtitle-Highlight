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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ScreenAPI implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            try {
                ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("subtitle_highlight.configure.title")).setDoesConfirmSave(false).setSavingRunnable(Manager::save).setDefaultBackgroundTexture(Identifier.of("minecraft", "textures/block/white_concrete.png"));
                builder.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.general")).addEntry(builder.entryBuilder().startBooleanToggle(Text.translatable("options.showSubtitles"), MinecraftClient.getInstance().options.getShowSubtitles().getValue()).setTooltip(Text.translatable("subtitle_highlight.configure.general.option.tooltip")).setDefaultValue(false).setSaveConsumer((newValue) -> {
                    MinecraftClient.getInstance().options.getShowSubtitles().setValue(newValue);
                    MinecraftClient.getInstance().options.write();
                }).build()).addEntry(builder.entryBuilder().startLongField(Text.translatable("subtitle_highlight.configure.general.remove_delay"), Manager.settings.maxDuration).setTooltip(Text.translatable("subtitle_highlight.configure.general.remove_delay.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.remove_delay.tooltip_2"), Text.translatable("subtitle_highlight.configure.general.remove_delay.tooltip_3")).setDefaultValue(3000).setMin(0).setSaveConsumer((newValue) -> {
                    Manager.settings.maxDuration = newValue;
                }).build()).addEntry(builder.entryBuilder().startDoubleField(Text.translatable("subtitle_highlight.configure.general.start_ratio"), Manager.settings.startRatio).setTooltip(Text.translatable("subtitle_highlight.configure.general.start_ratio.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.start_ratio.tooltip_2")).setDefaultValue(1).setMax(1).setMin(0).setSaveConsumer((newValue) -> {
                    Manager.settings.startRatio = newValue;
                }).build()).addEntry(builder.entryBuilder().startDoubleField(Text.translatable("subtitle_highlight.configure.general.end_ratio"), Manager.settings.endRatio).setTooltip(Text.translatable("subtitle_highlight.configure.general.end_ratio.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.end_ratio.tooltip_2")).setDefaultValue(0.29411764705882354).setMax(1).setMin(0).setSaveConsumer((newValue) -> {
                    Manager.settings.endRatio = newValue;
                }).build()).addEntry(builder.entryBuilder().startIntField(Text.translatable("subtitle_highlight.configure.general.background_color"), Manager.settings.backgroundColor).setTooltip(Text.translatable("subtitle_highlight.configure.general.background_color.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.background_color.tooltip_2")).setDefaultValue(0xcc000000).setMin(0).setMax(0xffffffff).setSaveConsumer((newValue) -> {
                    Manager.settings.backgroundColor = newValue;
                }).build()).addEntry(builder.entryBuilder().startFloatField(Text.translatable("subtitle_highlight.configure.general.scale"), Manager.settings.scale).setTooltip(Text.translatable("subtitle_highlight.configure.general.scale.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.scale.tooltip_2")).setDefaultValue(1).setSaveConsumer((newValue) -> {
                    Manager.settings.scale = newValue;
                }).build()).addEntry(builder.entryBuilder().startFloatField(Text.translatable("subtitle_highlight.configure.general.bottom_margin"), Manager.settings.bottomMargin).setTooltip(Text.translatable("subtitle_highlight.configure.general.bottom_margin.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.bottom_margin.tooltip_2")).setDefaultValue(30).setSaveConsumer((newValue) -> {
                    Manager.settings.bottomMargin = newValue;
                }).build()).addEntry(builder.entryBuilder().startFloatField(Text.translatable("subtitle_highlight.configure.general.side_margin"), Manager.settings.sideMargin).setTooltip(Text.translatable("subtitle_highlight.configure.general.side_margin.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.side_margin.tooltip_2")).setDefaultValue(1).setSaveConsumer((newValue) -> {
                    Manager.settings.sideMargin = newValue;
                }).build()).addEntry(builder.entryBuilder().startBooleanToggle(Text.translatable("subtitle_highlight.configure.general.ikun"), Manager.settings.ikunEasterEgg).setTooltip(Text.translatable("subtitle_highlight.configure.general.ikun.tooltip_1"), Text.translatable("subtitle_highlight.configure.general.ikun.tooltip_2")).setDefaultValue(true).setSaveConsumer((newValue) -> {
                    Manager.settings.ikunEasterEgg = newValue;
                }).build()).setDescription(new MutableText[]{Text.translatable("subtitle_highlight.configure.general.description")});
                SubCategoryBuilder colorSettings = builder.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color")).setTooltip(Text.translatable("subtitle_highlight.configure.general.color.tooltip")).setExpanded(true);
                colorSettings.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.ambient"), ColorCode.class, Manager.settings.colorSettings.ambient).setDefaultValue(ColorCode.DARK_BLUE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.ambient = newValue;
                }).build());
                SubCategoryBuilder block = builder.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.block")).setExpanded(true);
                block.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.generic"), ColorCode.class, Manager.settings.colorSettings.block.generic).setDefaultValue(ColorCode.WHITE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.block.generic = newValue;
                }).build());
                block.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.interact"), ColorCode.class, Manager.settings.colorSettings.block.interact).setDefaultValue(ColorCode.AQUA).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.block.interact = newValue;
                }).build());
                block.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.working"), ColorCode.class, Manager.settings.colorSettings.block.working).setDefaultValue(ColorCode.DARK_AQUA).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.block.working = newValue;
                }).build());
                block.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.dangerous"), ColorCode.class, Manager.settings.colorSettings.block.dangerous).setDefaultValue(ColorCode.RED).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.block.dangerous = newValue;
                }).build());
                block.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.crop"), ColorCode.class, Manager.settings.colorSettings.block.crop).setDefaultValue(ColorCode.DARK_GREEN).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.block.crop = newValue;
                }).build());
                block.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.block.other"), ColorCode.class, Manager.settings.colorSettings.block.other).setDefaultValue(ColorCode.DARK_GRAY).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.block.other = newValue;
                }).build());
                colorSettings.add(block.build());
                colorSettings.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.enchant"), ColorCode.class, Manager.settings.colorSettings.enchant).setDefaultValue(ColorCode.DARK_PURPLE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.enchant = newValue;
                }).build());
                SubCategoryBuilder entity = builder.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.entity")).setExpanded(true);
                SubCategoryBuilder mob = builder.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.entity.mob")).setExpanded(true);
                SubCategoryBuilder player = builder.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player")).setExpanded(true);
                player.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player.attack"), ColorCode.class, Manager.settings.colorSettings.entity.mob.player.attack).setDefaultValue(ColorCode.GOLD).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.mob.player.attack = newValue;
                }).build());
                player.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player.hurt"), ColorCode.class, Manager.settings.colorSettings.entity.mob.player.hurt).setDefaultValue(ColorCode.DARK_RED).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.mob.player.hurt = newValue;
                }).build());
                player.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.player.other"), ColorCode.class, Manager.settings.colorSettings.entity.mob.player.other).setDefaultValue(ColorCode.WHITE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.mob.player.other = newValue;
                }).build());
                mob.add(player.build());
                mob.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.passive"), ColorCode.class, Manager.settings.colorSettings.entity.mob.passive).setDefaultValue(ColorCode.GREEN).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.mob.passive = newValue;
                }).build());
                mob.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.neutral"), ColorCode.class, Manager.settings.colorSettings.entity.mob.neutral).setDefaultValue(ColorCode.YELLOW).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.mob.neutral = newValue;
                }).build());
                mob.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.hostile"), ColorCode.class, Manager.settings.colorSettings.entity.mob.hostile).setDefaultValue(ColorCode.RED).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.mob.hostile = newValue;
                }).build());
                mob.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.mob.boss"), ColorCode.class, Manager.settings.colorSettings.entity.mob.boss).setDefaultValue(ColorCode.LIGHT_PURPLE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.mob.boss = newValue;
                }).build());
                entity.add(mob.build());
                entity.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.vehicle"), ColorCode.class, Manager.settings.colorSettings.entity.vehicle).setDefaultValue(ColorCode.GRAY).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.vehicle = newValue;
                }).build());
                entity.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.projectile"), ColorCode.class, Manager.settings.colorSettings.entity.projectile).setDefaultValue(ColorCode.GOLD).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.projectile = newValue;
                }).build());
                entity.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.explosive"), ColorCode.class, Manager.settings.colorSettings.entity.explosive).setDefaultValue(ColorCode.RED).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.explosive = newValue;
                }).build());
                entity.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.decoration"), ColorCode.class, Manager.settings.colorSettings.entity.decoration).setDefaultValue(ColorCode.GRAY).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.decoration = newValue;
                }).build());
                entity.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.entity.other"), ColorCode.class, Manager.settings.colorSettings.entity.other).setDefaultValue(ColorCode.DARK_GRAY).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.entity.other = newValue;
                }).build());
                colorSettings.add(entity.build());
                SubCategoryBuilder item = builder.entryBuilder().startSubCategory(Text.translatable("subtitle_highlight.configure.general.color.item")).setExpanded(true);
                item.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.weapon"), ColorCode.class, Manager.settings.colorSettings.item.weapon).setDefaultValue(ColorCode.GOLD).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.item.weapon = newValue;
                }).build());
                item.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.armor"), ColorCode.class, Manager.settings.colorSettings.item.armor).setDefaultValue(ColorCode.DARK_GREEN).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.item.armor = newValue;
                }).build());
                item.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.tool"), ColorCode.class, Manager.settings.colorSettings.item.tool).setDefaultValue(ColorCode.BLUE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.item.tool = newValue;
                }).build());
                item.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.item.other"), ColorCode.class, Manager.settings.colorSettings.item.other).setDefaultValue(ColorCode.WHITE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.item.other = newValue;
                }).build());
                colorSettings.add(item.build());
                colorSettings.add(builder.entryBuilder().startEnumSelector(Text.translatable("subtitle_highlight.configure.general.color.other"), ColorCode.class, Manager.settings.colorSettings.other).setDefaultValue(ColorCode.WHITE).setEnumNameProvider(ColorCode::colorTranslation).setSaveConsumer((newValue) -> {
                    Manager.settings.colorSettings.other = newValue;
                }).build());
                builder.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.general")).addEntry(colorSettings.build());
                builder.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.custom")).addEntry(new NestedListListEntry<>(Text.translatable("subtitle_highlight.configure.custom.list"), Manager.settings.customList, true, Optional::empty, (newValue) -> {
                    Manager.settings.customList = (ArrayList<Settings.Custom>) newValue;
                }, List::of, builder.entryBuilder().getResetButtonKey(), true, true, (element, entry) -> {
                    Settings.Custom custom = element == null ? Manager.settings.new Custom() : element;
                    return new MultiElementListEntry<>(Text.translatable("subtitle_highlight.configure.custom.entry"), custom, List.of(builder.entryBuilder().startStrField(Text.translatable("subtitle_highlight.configure.custom.entry.translation_key"), custom.translationKey).setTooltip(Text.translatable("subtitle_highlight.configure.custom.entry.translation_key.tooltip")).setDefaultValue("").setSaveConsumer((newValue) -> {
                        custom.translationKey = newValue;
                    }).build(), builder.entryBuilder().startIntField(Text.translatable("subtitle_highlight.configure.custom.entry.color"), custom.color.getRgb()).setTooltip(Text.translatable("subtitle_highlight.configure.custom.entry.color.tooltip_1"), Text.translatable("subtitle_highlight.configure.custom.entry.color.tooltip_2")).setDefaultValue(0xffffff).setMin(0).setMax(0xffffff).setSaveConsumer((newValue) -> {
                        custom.color = TextColor.fromRgb(newValue);
                    }).build(), builder.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.obfuscated"), custom.obfuscated).setDefaultValue(false).setSaveConsumer((newValue) -> {
                        custom.obfuscated = newValue;
                    }).build(), builder.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.bold"), custom.bold).setDefaultValue(false).setSaveConsumer((newValue) -> {
                        custom.bold = newValue;
                    }).build(), builder.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.strikethrough"), custom.strikethrough).setDefaultValue(false).setSaveConsumer((newValue) -> {
                        custom.strikethrough = newValue;
                    }).build(), builder.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.underline"), custom.underline).setDefaultValue(false).setSaveConsumer((newValue) -> {
                        custom.underline = newValue;
                    }).build(), builder.entryBuilder().startBooleanToggle(Text.translatable("formatting_code.italic"), custom.italic).setDefaultValue(false).setSaveConsumer((newValue) -> {
                        custom.italic = newValue;
                    }).build()), true);
                }));
                builder.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.custom")).setDescription(new MutableText[]{Text.translatable("subtitle_highlight.configure.custom.description")});
                
                builder.getOrCreateCategory(Text.translatable("subtitle_highlight.configure.share")).addEntry(builder.entryBuilder().startTextDescription(Text.translatable("subtitle_highlight.configure.share.description")).build()).addEntry(builder.entryBuilder().startBooleanToggle(Text.translatable("subtitle_highlight.configure.share.export"), false).setDefaultValue(false).setSaveConsumer((value) -> {
                    if (value) {
                        try {
                            File exportFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "subtitle_highlight_export.json");
                            Manager.exportConfig(exportFile);
                            MinecraftClient.getInstance().player.sendMessage(Text.translatable("subtitle_highlight.configure.share.export.success", exportFile.getAbsolutePath()), false);
                        } catch (Exception e) {
                            MinecraftClient.getInstance().player.sendMessage(Text.translatable("subtitle_highlight.configure.share.export.error"), false);
                            e.printStackTrace();
                        }
                    }
                }).build()).addEntry(builder.entryBuilder().startBooleanToggle(Text.translatable("subtitle_highlight.configure.share.import"), false).setDefaultValue(false).setSaveConsumer((value) -> {
                    if (value) {
                        try {
                            File importFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "subtitle_highlight_import.json");
                            if (importFile.exists()) {
                                Manager.importConfig(importFile);
                                MinecraftClient.getInstance().player.sendMessage(Text.translatable("subtitle_highlight.configure.share.import.success"), false);
                                MinecraftClient.getInstance().setScreen(getModConfigScreenFactory().create(parent));
                            } else {
                                MinecraftClient.getInstance().player.sendMessage(Text.translatable("subtitle_highlight.configure.share.import.error"), false);
                            }
                        } catch (Exception e) {
                            MinecraftClient.getInstance().player.sendMessage(Text.translatable("subtitle_highlight.configure.share.import.error"), false);
                            e.printStackTrace();
                        }
                    }
                }).build());
                
                return builder.build();
            } catch (NullPointerException e) {
                Manager.settings = new Settings();
                Manager.save();
                return getModConfigScreenFactory().create(parent);
            }
        };
    }
}