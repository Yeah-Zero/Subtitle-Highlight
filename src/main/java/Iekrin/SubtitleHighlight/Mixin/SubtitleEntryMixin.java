package Iekrin.SubtitleHighlight.Mixin;

import Iekrin.SubtitleHighlight.Configure.Configuration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SubtitlesHud.SubtitleEntry.class)
public class SubtitleEntryMixin {
    private static final String[] 被动生物 = {"allay", "axolotl", "bat", "cat", "chicken", "cod", "cow", "donkey", "fox", "frog", "glow_squid", "horse", "mooshroom", "mule", "ocelot", "parrot", "pig", "puffer_fish", "rabbit", "salmon", "sheep", "skeleton_horse", "snow_golem", "squid", "strider", "tadpole", "tropical_fish", "turtle", "villager", "wandering_trader", "zombie_horse"};
    private static final String[] 中立生物 = {"bee", "dolphin", "enderman", "goat", "iron_golem", "llama", "panda", "piglin", "polar_bear", "spider", "wolf", "zombified_piglin"};
    private static final String[] 敌对生物 = {"blaze", "creeper", "drowned", "elder_guardian", "endermite", "evoker", "ghast", "guardian", "hoglin", "husk", "illusioner", "magma_cube", "phantom", "piglin_brute", "pillager", "ravager", "shulker", "silverfish", "skeleton", "slime", "stray", "vex", "vindicator", "warden", "witch", "wither_skeleton", "zoglin", "zombie", "zombie_villager"};
    private static final String[] 头目生物 = {"ender_dragon", "wither"};
    private static final String[] 载具 = {"boat", "minecart"};
    private static final String[] 弹射物 = {"arrow", "egg", "ender_pearl", "potion", "shulker_bullet", "snowball"};
    private static final String[] 爆炸物 = {"firework_rocket", "lightning_bolt", "tnt"};
    private static final String[] 装饰品 = {"armor_stand", "glow_item_frame", "item_frame", "painting"};
    @Shadow
    @Final
    private Text text;

    @Inject(at = @At("RETURN"), method = "getText()Lnet/minecraft/text/Text;", cancellable = true)
    private void 字幕着色(CallbackInfoReturnable<Text> 可返回回调信息) {
        if (this.text.getContent() instanceof TranslatableTextContent) {
            String[] 键值分割 = ((TranslatableTextContent) this.text.getContent()).getKey().split("\\.");
            if (键值分割[0].equals("subtitles")) {
                switch (键值分割[1]) {
                    case "ambient":
                        break;
                    case "block":
                        break;
                    case "enchant":
                        break;
                    case "entity":
                        if (键值分割[2].equals("player")) {
                            可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.生物.玩家.获取颜色代码())));
                            return;
                        }
                        for (String 元素 : 被动生物) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.生物.被动生物.获取颜色代码())));
                                return;
                            }
                        }
                        for (String 元素 : 中立生物) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.生物.中立生物.获取颜色代码())));
                                return;
                            }
                        }
                        for (String 元素 : 敌对生物) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.生物.敌对生物.获取颜色代码())));
                                return;
                            }
                        }
                        for (String 元素 : 头目生物) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.生物.头目生物.获取颜色代码())));
                                return;
                            }
                        }
                        for (String 元素 : 载具) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.载具.获取颜色代码())));
                                return;
                            }
                        }
                        for (String 元素 : 弹射物) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.弹射物.获取颜色代码())));
                                return;
                            }
                        }
                        for (String 元素 : 爆炸物) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.爆炸物.获取颜色代码())));
                                return;
                            }
                        }
                        for (String 元素 : 装饰品) {
                            if (键值分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.装饰品.获取颜色代码())));
                                return;
                            }
                        }
                        可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.byCode(Configuration.配置项.基本颜色设置.实体.其它.获取颜色代码())));
                        return;
                    case "event":
                        break;
                    case "item":
                        break;
                    case "particle":
                        break;
                    case "ui":
                        break;
                    case "weather":
                        break;
                    default:
                        break;
                }
            }
        }
        可返回回调信息.setReturnValue(this.text);
    }
}
