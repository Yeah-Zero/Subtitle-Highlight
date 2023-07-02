package Yeah_Zero.Subtitle_Highlight.Mixin;

import Yeah_Zero.Subtitle_Highlight.Configure.Manager;
import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import Yeah_Zero.Subtitle_Highlight.Util.SplitKeyArrays;
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
    @Shadow
    @Final
    private Text text;

    @Inject(at = @At("RETURN"), method = "getText()Lnet/minecraft/text/Text;", cancellable = true)
    private void 字幕着色(CallbackInfoReturnable<Text> 可返回回调信息) {
        MutableText 字幕文本 = ((MutableText) this.text).formatted(Formatting.RESET);
        if (字幕文本 instanceof TranslatableText) {
            for (Settings.Custom 元素 : Manager.配置项.自定义列表) {
                if (((TranslatableText) 字幕文本).getKey().equals(元素.本地化键名)) {
                    可返回回调信息.setReturnValue(字幕文本.setStyle(字幕文本.getStyle().withColor(元素.颜色).withObfuscated(元素.随机).withBold(元素.粗体).withStrikethrough(元素.删除线).withUnderline(元素.下划线).withItalic(元素.斜体)));
                    return;
                }
            }
            String[] 键名分割 = ((TranslatableText) 字幕文本).getKey().split("\\.");
            if (键名分割[0].equals("subtitles")) {
                switch (键名分割[1]) {
                    case "ambient", "weather" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.环境.获取格式代码()));
                        return;
                    }
                    case "block" -> {
                        if (键名分割[2].equals("generic")) {
                            可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.通用.获取格式代码()));
                            return;
                        }
                        for (String 元素 : SplitKeyArrays.互动) {
                            if (键名分割[2].equals(元素)) {
                                if ((键名分割[2].equals("anvil") && 键名分割[3].equals("land")) || (键名分割[2].equals("tripwire") && 键名分割[3].equals("click"))) {
                                    可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.危险方块.获取格式代码()));
                                    return;
                                }
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.互动.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.工作) {
                            if (键名分割[2].equals(元素)) {
                                if ((键名分割[2].equals("beacon") && 键名分割[3].equals("power_select")) || (键名分割[2].equals("beehive") && 键名分割[3].equals("shear"))) {
                                    可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.互动.获取格式代码()));
                                    return;
                                }
                                if (键名分割[2].equals("pointed_dripstone") && (键名分割[3].startsWith("drip_lava") || 键名分割[3].equals("land"))) {
                                    可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.危险方块.获取格式代码()));
                                    return;
                                }
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.运作.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.危险方块) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.危险方块.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.农作物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.农作物.获取格式代码()));
                                return;
                            }
                        }
                        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.方块_其它.获取格式代码()));
                        return;
                    }
                    case "chiseled_bookshelf", "ui" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.方块.互动.获取格式代码()));
                        return;
                    }
                    case "enchant", "particle" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.魔咒.获取格式代码()));
                        return;
                    }
                    case "entity" -> {
                        if (键名分割[2].equals("generic") || 键名分割[2].equals("player")) {
                            if (键名分割[3].equals("attack")) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.玩家.攻击.获取格式代码()));
                                return;
                            }
                            for (String 元素 : SplitKeyArrays.受伤) {
                                if (键名分割[3].equals(元素)) {
                                    可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.玩家.受伤.获取格式代码()));
                                    return;
                                }
                            }
                            可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.玩家.玩家_其它.获取格式代码()));
                            return;
                        }
                        for (String 元素 : SplitKeyArrays.友好生物) {
                            if (键名分割[2].equals(元素)) {
                                if (键名分割[2].equals("chicken") && Manager.配置项.iKun彩蛋) {
                                    可返回回调信息.setReturnValue(new TranslatableText("subtitles.entity.kun." + 键名分割[3]).setStyle(字幕文本.getStyle().withColor(TextColor.fromFormatting(Formatting.GRAY)).withBold(true)));
                                    return;
                                }
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.被动生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.中立生物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.中立生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.敌对生物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.敌对生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.头目生物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.头目生物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.载具) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.载具.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.弹射物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.弹射物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.爆炸物) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.爆炸物.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.装饰品) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.装饰品.获取格式代码()));
                                return;
                            }
                        }
                        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.实体_其它.获取格式代码()));
                        return;
                    }
                    case "event" -> {
                        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.实体.生物.敌对生物.获取格式代码()));
                        return;
                    }
                    case "item" -> {
                        for (String 元素 : SplitKeyArrays.武器) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.物品.武器.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.防具) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.物品.防具.获取格式代码()));
                                return;
                            }
                        }
                        for (String 元素 : SplitKeyArrays.工具) {
                            if (键名分割[2].equals(元素)) {
                                可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.物品.工具.获取格式代码()));
                                return;
                            }
                        }
                        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.物品.物品_其它.获取格式代码()));
                        return;
                    }
                }
            }
        }
        可返回回调信息.setReturnValue(字幕文本.formatted(Manager.配置项.基本颜色设置.其它.获取格式代码()));
    }
}
