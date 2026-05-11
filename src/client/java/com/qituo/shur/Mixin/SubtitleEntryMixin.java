package com.qituo.shur.Mixin;

import com.qituo.shur.Configure.Manager;
import com.qituo.shur.Configure.Settings;
import com.qituo.shur.Data.SubtitleTypeLoader;
import com.qituo.shur.Util.SplitKeyArrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net.minecraft.client.gui.hud.SubtitlesHud$SubtitleEntry")
public class SubtitleEntryMixin {
    @Shadow
    @Final
    private Text text;

    @Inject(at = @At("RETURN"), method = "getText()Lnet/minecraft/text/Text;", cancellable = true)
    private void colorizeSubtitle(CallbackInfoReturnable<Text> cir) {
        MutableText subtitleText = ((MutableText) this.text).formatted(Formatting.RESET);
        if (subtitleText.getContent() instanceof TranslatableTextContent) {
            for (Settings.Custom custom : Manager.settings.customList) {
                if (((TranslatableTextContent) subtitleText.getContent()).getKey().equals(custom.translationKey)) {
                    cir.setReturnValue(subtitleText.setStyle(subtitleText.getStyle().withColor(custom.color).withObfuscated(custom.obfuscated).withBold(custom.bold).withStrikethrough(custom.strikethrough).withUnderline(custom.underline).withItalic(custom.italic)));
                    return;
                }
            }
            String[] keyParts = ((TranslatableTextContent) subtitleText.getContent()).getKey().split("\\.");
            if (keyParts[0].equals("subtitles")) {
                switch (keyParts[1]) {
                    case "ambient", "weather" -> {
                        com.qituo.shur.Util.ColorCode colorCode = SubtitleTypeLoader.getColor("", "ambient");
                        if (colorCode != null) {
                            cir.setReturnValue(subtitleText.formatted(colorCode.getFormatting()));
                        } else {
                            cir.setReturnValue(subtitleText.formatted(Manager.settings.colorSettings.ambient.getFormatting()));
                        }
                        return;
                    }
                    case "block" -> {
                        if (keyParts[2].equals("generic")) {
                            com.qituo.shur.Util.ColorCode colorCode = SubtitleTypeLoader.getColor("block", "generic");
                            if (colorCode != null) {
                                cir.setReturnValue(subtitleText.formatted(colorCode.getFormatting()));
                            } else {
                                cir.setReturnValue(subtitleText.formatted(Manager.settings.colorSettings.block.generic.getFormatting()));
                            }
                            return;
                        }
                        String blockKey = keyParts[2];
                        String subKey = keyParts.length > 3 ? keyParts[3] : "";
                        
                        // 使用 HashSet 进行 O(1) 查找
                        if (SplitKeyArrays.interactSet.contains(blockKey)) {
                            if ((blockKey.equals("anvil") && subKey.equals("land")) || (blockKey.equals("tripwire") && subKey.equals("click"))) {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "dangerous"), Manager.settings.colorSettings.block.dangerous);
                            } else {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "interact"), Manager.settings.colorSettings.block.interact);
                            }
                            return;
                        }
                        if (SplitKeyArrays.workingSet.contains(blockKey)) {
                            if ((blockKey.equals("beacon") && subKey.equals("power_select")) || (blockKey.equals("beehive") && subKey.equals("shear"))) {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "interact"), Manager.settings.colorSettings.block.interact);
                            } else if (blockKey.equals("pointed_dripstone") && (subKey.startsWith("drip_lava") || subKey.equals("land"))) {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "dangerous"), Manager.settings.colorSettings.block.dangerous);
                            } else {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "working"), Manager.settings.colorSettings.block.working);
                            }
                            return;
                        }
                        if (SplitKeyArrays.dangerousBlocksSet.contains(blockKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "dangerous"), Manager.settings.colorSettings.block.dangerous);
                            return;
                        }
                        if (SplitKeyArrays.cropsSet.contains(blockKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "crop"), Manager.settings.colorSettings.block.crop);
                            return;
                        }
                        applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("block", "other"), Manager.settings.colorSettings.block.other);
                        return;
                    }
                    case "chiseled_bookshelf", "ui" -> {
                        cir.setReturnValue(subtitleText.formatted(Manager.settings.colorSettings.block.interact.getFormatting()));
                        return;
                    }
                    case "enchant", "particle" -> {
                        com.qituo.shur.Util.ColorCode colorCode = SubtitleTypeLoader.getColor("", "enchant");
                        if (colorCode != null) {
                            cir.setReturnValue(subtitleText.formatted(colorCode.getFormatting()));
                        } else {
                            cir.setReturnValue(subtitleText.formatted(Manager.settings.colorSettings.enchant.getFormatting()));
                        }
                        return;
                    }
                    case "entity" -> {
                        String entityKey = keyParts[2];
                        String subKey = keyParts.length > 3 ? keyParts[3] : "";
                        
                        if (entityKey.equals("generic") || entityKey.equals("player")) {
                            if (subKey.equals("attack")) {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity.mob.player", "attack"), Manager.settings.colorSettings.entity.mob.player.attack);
                            } else if (SplitKeyArrays.hurtSet.contains(subKey)) {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity.mob.player", "hurt"), Manager.settings.colorSettings.entity.mob.player.hurt);
                            } else {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity.mob.player", "other"), Manager.settings.colorSettings.entity.mob.player.other);
                            }
                            return;
                        }
                        
                        // 使用 HashSet 进行 O(1) 查找
                        if (SplitKeyArrays.friendlyMobsSet.contains(entityKey)) {
                            if (entityKey.equals("chicken") && Manager.settings.ikunEasterEgg) {
                                cir.setReturnValue(Text.translatable("subtitles.entity.kun." + subKey).setStyle(subtitleText.getStyle().withColor(TextColor.fromFormatting(Formatting.GRAY)).withBold(true)));
                            } else {
                                applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity.mob", "passive"), Manager.settings.colorSettings.entity.mob.passive);
                            }
                            return;
                        }
                        if (SplitKeyArrays.neutralMobsSet.contains(entityKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity.mob", "neutral"), Manager.settings.colorSettings.entity.mob.neutral);
                            return;
                        }
                        if (SplitKeyArrays.hostileMobsSet.contains(entityKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity.mob", "hostile"), Manager.settings.colorSettings.entity.mob.hostile);
                            return;
                        }
                        if (SplitKeyArrays.bossMobsSet.contains(entityKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity.mob", "boss"), Manager.settings.colorSettings.entity.mob.boss);
                            return;
                        }
                        if (SplitKeyArrays.vehiclesSet.contains(entityKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity", "vehicle"), Manager.settings.colorSettings.entity.vehicle);
                            return;
                        }
                        if (SplitKeyArrays.projectilesSet.contains(entityKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity", "projectile"), Manager.settings.colorSettings.entity.projectile);
                            return;
                        }
                        if (SplitKeyArrays.explosivesSet.contains(entityKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity", "explosive"), Manager.settings.colorSettings.entity.explosive);
                            return;
                        }
                        if (SplitKeyArrays.decorationsSet.contains(entityKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity", "decoration"), Manager.settings.colorSettings.entity.decoration);
                            return;
                        }
                        applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("entity", "other"), Manager.settings.colorSettings.entity.other);
                        return;
                    }
                    case "event" -> {
                        com.qituo.shur.Util.ColorCode colorCode = SubtitleTypeLoader.getColor("entity.mob", "hostile");
                        if (colorCode != null) {
                            cir.setReturnValue(subtitleText.formatted(colorCode.getFormatting()));
                        } else {
                            cir.setReturnValue(subtitleText.formatted(Manager.settings.colorSettings.entity.mob.hostile.getFormatting()));
                        }
                        return;
                    }
                    case "item" -> {
                        String itemKey = keyParts[2];
                        
                        // 使用 HashSet 进行 O(1) 查找
                        if (SplitKeyArrays.weaponsSet.contains(itemKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("item", "weapon"), Manager.settings.colorSettings.item.weapon);
                            return;
                        }
                        if (SplitKeyArrays.armorsSet.contains(itemKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("item", "armor"), Manager.settings.colorSettings.item.armor);
                            return;
                        }
                        if (SplitKeyArrays.toolsSet.contains(itemKey)) {
                            applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("item", "tool"), Manager.settings.colorSettings.item.tool);
                            return;
                        }
                        applyColor(cir, subtitleText, SubtitleTypeLoader.getColor("item", "other"), Manager.settings.colorSettings.item.other);
                        return;
                    }
                }
            }
        }
        // 使用API处理字幕
        Text processedText = com.qituo.shur.api.SubtitleAPI.processSubtitle(subtitleText, Manager.settings);
        
        // 应用其他类型的颜色
        com.qituo.shur.Util.ColorCode colorCode = SubtitleTypeLoader.getColor("", "other");
        if (colorCode != null) {
            processedText = ((MutableText) processedText).formatted(colorCode.getFormatting());
        } else {
            processedText = ((MutableText) processedText).formatted(Manager.settings.colorSettings.other.getFormatting());
        }
        
        cir.setReturnValue(processedText);
    }
    
    private void applyColor(CallbackInfoReturnable<Text> cir, MutableText subtitleText, com.qituo.shur.Util.ColorCode dataColor, com.qituo.shur.Util.ColorCode configColor) {
        if (dataColor != null) {
            cir.setReturnValue(subtitleText.formatted(dataColor.getFormatting()));
        } else {
            cir.setReturnValue(subtitleText.formatted(configColor.getFormatting()));
        }
    }
}