package Iekrin.SubtitleHighlight.Mixin;

import Iekrin.SubtitleHighlight.Configure.Configuration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Iterator;

import static net.minecraft.client.gui.hud.SubtitlesHud.SubtitleEntry;

@Environment(EnvType.CLIENT)
@Mixin(SubtitlesHud.class)
public class SubtitlesHudMixin {
    private static double 持续时间比例;

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", ordinal = 0))
    private @NotNull Object 获取字幕条目(@NotNull Iterator 实例) {
        SubtitleEntry 字幕条目 = (SubtitleEntry) 实例.next();
        持续时间比例 = (Util.getMeasuringTimeMs() - 字幕条目.getTime()) / (double) (Configuration.最长持续时间);
        return 字幕条目;
    }

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud$SubtitleEntry;getTime()J"))
    private long 字幕时间重定向(@NotNull SubtitleEntry 实例) {
        return 实例.getTime() - 3000 + Configuration.最长持续时间;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At("STORE"), ordinal = 7)
    private int 方向显示颜色注入(int p) {
        return MathHelper.floor(MathHelper.clampedLerp(255 * Configuration.起始比例, 255 * Configuration.终止比例, 持续时间比例));
    }

    @ModifyArgs(method = "render(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"), slice = @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"), to = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V")))
    private void 字幕显示颜色注入(@NotNull Args 参数列表) {
        Text 文字 = 参数列表.get(1);
        参数列表.set(1, Text.literal(文字.getString()).setStyle(Style.EMPTY.withColor((TextColor) null).withBold(文字.getStyle().isBold()).withItalic(文字.getStyle().isItalic()).withUnderline(文字.getStyle().isUnderlined()).withStrikethrough(文字.getStyle().isStrikethrough()).withObfuscated(文字.getStyle().isObfuscated()).withClickEvent(文字.getStyle().getClickEvent()).withHoverEvent(文字.getStyle().getHoverEvent()).withInsertion(文字.getStyle().getInsertion()).withFont(文字.getStyle().getFont())));
        int 红, 绿, 蓝;
        if (文字.getStyle().getColor() != null) {
            红 = (文字.getStyle().getColor().getRgb() >> 16) & 255;
            绿 = (文字.getStyle().getColor().getRgb() >> 8) & 255;
            蓝 = 文字.getStyle().getColor().getRgb() & 255;
        } else {
            红 = 绿 = 蓝 = 255;
        }
        int 红色剩余 = MathHelper.floor(MathHelper.clampedLerp(红 * Configuration.起始比例, 红 * Configuration.终止比例, 持续时间比例));
        int 绿色剩余 = MathHelper.floor(MathHelper.clampedLerp(绿 * Configuration.起始比例, 绿 * Configuration.终止比例, 持续时间比例));
        int 蓝色剩余 = MathHelper.floor(MathHelper.clampedLerp(蓝 * Configuration.起始比例, 蓝 * Configuration.终止比例, 持续时间比例));
        参数列表.set(4, (红色剩余 << 16 | 绿色剩余 << 8 | 蓝色剩余) - 16777216);
    }
}
