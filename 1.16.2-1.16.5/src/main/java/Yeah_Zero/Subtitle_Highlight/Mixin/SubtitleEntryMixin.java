package Yeah_Zero.Subtitle_Highlight.Mixin;

import Yeah_Zero.Subtitle_Highlight.Configure.Configuration;
import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
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
    private static final String[] 友好生物 = {"allay", "axolotl", "bat", "camel", "cat", "chicken", "cod", "cow", "donkey", "fox", "frog", "glow_squid", "horse", "mooshroom", "mule", "ocelot", "parrot", "pig", "puffer_fish", "rabbit", "salmon", "sheep", "skeleton_horse", "snow_golem", "squid", "strider", "tadpole", "tropical_fish", "turtle", "villager", "wandering_trader", "zombie_horse"};
    private static final String[] 中立生物 = {"bee", "dolphin", "enderman", "goat", "iron_golem", "llama", "panda", "piglin", "polar_bear", "spider", "wolf", "zombified_piglin"};
    private static final String[] 敌对生物 = {"blaze", "creeper", "drowned", "elder_guardian", "endermite", "evoker", "ghast", "guardian", "hoglin", "husk", "illusioner", "magma_cube", "phantom", "piglin_brute", "pillager", "ravager", "shulker", "silverfish", "skeleton", "slime", "stray", "vex", "vindicator", "warden", "witch", "wither_skeleton", "zoglin", "zombie", "zombie_villager"};
    private static final String[] 头目生物 = {"ender_dragon", "wither"};
    private static final String[] 载具 = {"boat", "minecart"};
    private static final String[] 弹射物 = {"arrow", "egg", "ender_eye", "ender_pearl", "potion", "shulker_bullet", "snowball"};
    private static final String[] 爆炸物 = {"firework_rocket", "lightning_bolt", "tnt"};
    private static final String[] 装饰品 = {"armor_stand", "glow_item_frame", "item_frame", "painting"};
    private static final String[] 受伤 = {"big_fall", "burn", "death", "explode", "extinguish_fire", "hurt", "small_fall", "freeze_hurt", "hurt_drown", "hurt_on_fire"};
    private static final String[] 互动 = {"anvil", "barrel", "bell", "big_dripleaf", "button", "cake", "chest", "comparator", "composter", "door", "enchantment_table", "end_portal_frame", "fence_gate", "grindstone", "growing_plant", "honey_block", "lever", "note_block", "pressure_plate", "pumpkin", "respawn_anchor", "sculk_sensor", "shulker_box", "smithing_table", "sweet_berry_bush", "trapdoor", "tripwire"};
    private static final String[] 运作 = {"amethyst_block", "beacon", "beehive", "blastfurnace", "brewing_stand", "bubble_column", "candle", "conduit", "dispenser", "end_portal", "furnace", "iron_trapdoor", "piston", "portal", "redstone_torch", "sculk", "sculk_catalyst", "smoker", "water"};
    private static final String[] 危险方块 = {"campfire", "fire", "lava", "pointed_dripstone", "sculk_shrieker"};
    private static final String[] 农作物 = {"chorus_flower", "frogspawn"};
    private static final String[] 武器 = {"crossbow", "trident"};
    private static final String[] 防具 = {"armor", "shield"};
    private static final String[] 工具 = {"axe", "bottle", "bucket", "bundle", "flintandsteel", "hoe", "goat_horn", "lodestone_compass", "shears", "shovel", "spyglass", "totem"};
    @Shadow
    @Final
    private Text text;

    @Inject(at = @At("RETURN"), method = "getText()Lnet/minecraft/text/Text;", cancellable = true)
    private void 字幕着色(CallbackInfoReturnable<Text> 可返回回调信息) {
        MutableText 字幕文本 = ((MutableText) this.text).formatted(Formatting.RESET);
        if (字幕文本 instanceof TranslatableText) {
            for (Settings.Custom 元素 : Configuration.配置项.自定义列表) {
                if (((TranslatableText) 字幕文本).getKey().equals(元素.本地化键名)) {
                    if (元素.随机) {
                        字幕文本 = 字幕文本.formatted(Formatting.OBFUSCATED);
                    }
                    if (元素.删除线) {
                        字幕文本 = 字幕文本.formatted(Formatting.STRIKETHROUGH);
                    }
                    可返回回调信息.setReturnValue(字幕文本.setStyle(字幕文本.getStyle().withColor(元素.颜色).withBold(元素.粗体).withUnderline(元素.下划线).withItalic(元素.斜体)));
                    return;
                }
            }
            String[] 键名分割 = ((TranslatableText) 字幕文本).getKey().split("\\.");
            if (键名分割[0].equals("subtitles")) {
                switch (键名分割[1]) {
                    case "ambient", "weather" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.环境.获取格式代码()));
                        return;
                    }
                    case "block" -> {
                        if (键名分割[2].equals("generic")) {
                            可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.通用.获取格式代码()));
                            return;
                        }
                        for (String 元素 : 互动) {
                            if (键名分割[2].equals(元素)) {
                                if (键名分割[2].equals("anvil") && 键名分割[3].equals("land")) {
                                    可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.危险方块.获取格式代码()));
                                    return;
                                }
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.互动.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 运作) {
                            if (键名分割[2].equals(元素)) {
                                if (键名分割[2].equals("beacon") && 键名分割[3].equals("power_select")) {
                                    可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.互动.获取格式代码()));
                                    return;
                                }
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.运作.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 危险方块) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.危险方块.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 农作物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.农作物.获取格式代码()));
                                return;
                            }
                        }
                        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.方块_其它.获取格式代码()));
                        return;
                    }
                    case "enchant", "particle" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.魔咒.获取格式代码()));
                        return;
                    }
                    case "entity" -> {
                        if (键名分割[2].equals("generic") || 键名分割[2].equals("player")) {
                            if (键名分割[3].equals("attack")) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.玩家.攻击.获取格式代码()));
                                return;
                            }
                            for (String 元素 : 受伤) {
                                if (键名分割[3].equals(元素)) {
                                    可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.玩家.受伤.获取格式代码()));
                                    return;
                                }
                            }
                            可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.玩家.玩家_其它.获取格式代码()));
                            return;
                        }
                        for (String 元素 : 友好生物) {
                            if (键名分割[2].equals(元素)) {
                                if (键名分割[2].equals("chicken") && Configuration.配置项.iKun彩蛋) {
                                    可返回回调信息.setReturnValue(new TranslatableText("subtitles.entity.kun." + 键名分割[3]).setStyle(字幕文本.getStyle().withColor(TextColor.fromFormatting(Formatting.GRAY)).withBold(true)));
                                    return;
                                }
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.被动生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 中立生物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.中立生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 敌对生物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.敌对生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 头目生物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.头目生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 载具) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.载具.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 弹射物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.弹射物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 爆炸物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.爆炸物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 装饰品) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.装饰品.获取格式代码()));
                                return;
                            }
                        }
                        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.实体_其它.获取格式代码()));
                        return;
                    }
                    case "event" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.实体.生物.敌对生物.获取格式代码()));
                        return;
                    }
                    case "item" -> {
                        for (String 元素 : 武器) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.物品.武器.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 防具) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.物品.防具.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : 工具) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.物品.工具.获取格式代码()));
                                return;
                            }
                        }
                        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.物品.物品_其它.获取格式代码()));
                        return;
                    }
                    case "ui" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.方块.互动.获取格式代码()));
                        return;
                    }
                }
            }
        }
        可返回回调信息.setReturnValue(字幕文本.formatted(Configuration.配置项.基本颜色设置.其它.获取格式代码()));
    }
}