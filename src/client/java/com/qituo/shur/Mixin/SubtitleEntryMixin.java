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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // 性能日志记录器
    private static final Logger PERF_LOGGER = LoggerFactory.getLogger("shur-performance");
    
    // 性能统计变量
    private static long totalTime = 0;
    private static int callCount = 0;
    private static long maxTime = 0;
    private static long minTime = Long.MAX_VALUE;
    private static long lastLogTime = 0;
    private static final long LOG_INTERVAL = 10000; // 每10秒输出一次统计

    @Inject(at = @At("RETURN"), method = "getText()Lnet/minecraft/text/Text;", cancellable = true)
    private void colorizeSubtitle(CallbackInfoReturnable<Text> cir) {
        long startTime = System.nanoTime();
        
        try {
            MutableText subtitleText = ((MutableText) this.text).formatted(Formatting.RESET);
            if (subtitleText.getContent() instanceof TranslatableTextContent) {
                // 检查自定义字幕列表
                long customCheckStart = System.nanoTime();
                for (Settings.Custom custom : Manager.settings.customList) {
                    if (((TranslatableTextContent) subtitleText.getContent()).getKey().equals(custom.translationKey)) {
                        cir.setReturnValue(subtitleText.setStyle(subtitleText.getStyle().withColor(custom.color).withObfuscated(custom.obfuscated).withBold(custom.bold).withStrikethrough(custom.strikethrough).withUnderline(custom.underline).withItalic(custom.italic)));
                        return;
                    }
                }
                long customCheckTime = System.nanoTime() - customCheckStart;
                if (customCheckTime > 100000) { // 超过100微秒记录
                    PERF_LOGGER.warn("Custom list check took {}us for {} entries", customCheckTime / 1000, Manager.settings.customList.size());
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
            long apiStart = System.nanoTime();
            Text processedText = com.qituo.shur.api.SubtitleAPI.processSubtitle(subtitleText, Manager.settings);
            long apiTime = System.nanoTime() - apiStart;
            if (apiTime > 100000) {
                PERF_LOGGER.warn("SubtitleAPI.processSubtitle took {}us", apiTime / 1000);
            }
            
            // 应用其他类型的颜色
            com.qituo.shur.Util.ColorCode colorCode = SubtitleTypeLoader.getColor("", "other");
            if (colorCode != null) {
                processedText = ((MutableText) processedText).formatted(colorCode.getFormatting());
            } else {
                processedText = ((MutableText) processedText).formatted(Manager.settings.colorSettings.other.getFormatting());
            }
            
            cir.setReturnValue(processedText);
        } finally {
            long elapsed = System.nanoTime() - startTime;
            
            // 统计信息
            synchronized (SubtitleEntryMixin.class) {
                totalTime += elapsed;
                callCount++;
                maxTime = Math.max(maxTime, elapsed);
                minTime = Math.min(minTime, elapsed);
                
                // 定期输出统计
                long now = System.currentTimeMillis();
                if (now - lastLogTime >= LOG_INTERVAL) {
                    if (callCount > 0) {
                        double avgTime = (double) totalTime / callCount / 1000; // 转换为微秒
                        PERF_LOGGER.info("Subtitle colorize stats - Calls: {}, Avg: {:.2f}us, Min: {}us, Max: {}us", 
                            callCount, avgTime, minTime / 1000, maxTime / 1000);
                    }
                    // 重置统计
                    totalTime = 0;
                    callCount = 0;
                    maxTime = 0;
                    minTime = Long.MAX_VALUE;
                    lastLogTime = now;
                }
                
                // 记录耗时超过1毫秒的调用
                if (elapsed > 1000000) {
                    PERF_LOGGER.warn("colorizeSubtitle took {}ms - potential performance issue", elapsed / 1000000);
                }
            }
        }
    }
    
    private void applyColor(CallbackInfoReturnable<Text> cir, MutableText subtitleText, com.qituo.shur.Util.ColorCode dataColor, com.qituo.shur.Util.ColorCode configColor) {
        if (dataColor != null) {
            cir.setReturnValue(subtitleText.formatted(dataColor.getFormatting()));
        } else {
            cir.setReturnValue(subtitleText.formatted(configColor.getFormatting()));
        }
    }
}
