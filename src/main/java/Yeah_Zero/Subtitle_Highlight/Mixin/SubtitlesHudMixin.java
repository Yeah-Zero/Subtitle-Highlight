package Yeah_Zero.Subtitle_Highlight.Mixin;

import Yeah_Zero.Subtitle_Highlight.Configure.Configuration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Iterator;

import static net.minecraft.client.gui.hud.SubtitlesHud.SubtitleEntry;

@Environment(EnvType.CLIENT)
@Mixin(SubtitlesHud.class)
public class SubtitlesHudMixin {
    private static double 持续时间比例;

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", ordinal = 0))
    private Object 获取字幕条目(Iterator 实例) {
        SubtitleEntry 字幕条目 = (SubtitleEntry) 实例.next();
        持续时间比例 = (Util.getMeasuringTimeMs() - 字幕条目.getTime()) / (double) (Configuration.配置项.最长持续时间);
        return 字幕条目;
    }

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud$SubtitleEntry;getTime()J"))
    private long 字幕时间重定向(SubtitleEntry 实例) {
        return 实例.getTime() - 3000 + Configuration.配置项.最长持续时间;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At("STORE"), ordinal = 7)
    private int 方向显示颜色注入(int 原始赋值) {
        return MathHelper.floor(MathHelper.clampedLerp(255 * Configuration.配置项.起始比例, 255 * Configuration.配置项.终止比例, 持续时间比例));
    }

    @ModifyArgs(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud;drawTextWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"))
    private void 字幕显示颜色注入(Args 参数列表) {
        Text 文字 = 参数列表.get(2);
        参数列表.set(2, Text.literal(文字.getString()).setStyle(文字.getStyle().withColor((TextColor) null)));
        int 红, 绿, 蓝;
        if (文字.getStyle().getColor() != null) {
            红 = (文字.getStyle().getColor().getRgb() >> 16) & 255;
            绿 = (文字.getStyle().getColor().getRgb() >> 8) & 255;
            蓝 = 文字.getStyle().getColor().getRgb() & 255;
        } else {
            红 = 绿 = 蓝 = 255;
        }
        int 红色剩余 = MathHelper.floor(MathHelper.clampedLerp(红 * Configuration.配置项.起始比例, 红 * Configuration.配置项.终止比例, 持续时间比例));
        int 绿色剩余 = MathHelper.floor(MathHelper.clampedLerp(绿 * Configuration.配置项.起始比例, 绿 * Configuration.配置项.终止比例, 持续时间比例));
        int 蓝色剩余 = MathHelper.floor(MathHelper.clampedLerp(蓝 * Configuration.配置项.起始比例, 蓝 * Configuration.配置项.终止比例, 持续时间比例));
        参数列表.set(5, (红色剩余 << 16 | 绿色剩余 << 8 | 蓝色剩余) - 16777216);
    }
}
