package Yeah_Zero.Subtitle_Highlight.Mixin;

import Yeah_Zero.Subtitle_Highlight.Configure.Manager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net.minecraft.client.gui.hud.SubtitlesHud")
public class SubtitlesHudMixin {
    private static double durationRatio;
    
    @Shadow
    @Final
    private MinecraftClient client;

    @Redirect(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", ordinal = 0))
    private Object getSubtitleEntry(java.util.Iterator instance) {
        Object subtitleEntry = instance.next();
        try {
            java.lang.reflect.Field timeField = subtitleEntry.getClass().getDeclaredField("field_1472");
            timeField.setAccessible(true);
            long time = timeField.getLong(subtitleEntry);
            durationRatio = (Util.getMeasuringTimeMs() - time) / (double) (Manager.settings.maxDuration);
        } catch (Exception e) {
            durationRatio = 0.5;
        }
        return subtitleEntry;
    }

    @Redirect(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud$SubtitleEntry;getTime()J"))
    private long redirectSubtitleTime(Object instance) {
        try {
            java.lang.reflect.Field timeField = instance.getClass().getDeclaredField("field_1472");
            timeField.setAccessible(true);
            long time = timeField.getLong(instance);
            return (long) (time - 3000 * this.client.options.getNotificationDisplayTime().getValue() + Manager.settings.maxDuration * this.client.options.getNotificationDisplayTime().getValue());
        } catch (Exception e) {
            return 0;
        }
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At("STORE"), ordinal = 7)
    private int modifyDirectionDisplayColor(int originalValue) {
        return MathHelper.floor(MathHelper.clampedLerp(255 * Manager.settings.startRatio, 255 * Manager.settings.endRatio, durationRatio));
    }

    @ModifyArgs(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"))
    private void modifySubtitleDisplayColor(Args args) {
        Text text = args.get(1);
        args.set(1, Text.literal(text.getString()).setStyle(text.getStyle().withColor((TextColor) null)));
        int red, green, blue;
        if (text.getStyle().getColor() != null) {
            red = (text.getStyle().getColor().getRgb() >> 16) & 255;
            green = (text.getStyle().getColor().getRgb() >> 8) & 255;
            blue = text.getStyle().getColor().getRgb() & 255;
        } else {
            red = green = blue = 255;
        }
        int redRemaining = MathHelper.floor(MathHelper.clampedLerp(red * Manager.settings.startRatio, red * Manager.settings.endRatio, durationRatio));
        int greenRemaining = MathHelper.floor(MathHelper.clampedLerp(green * Manager.settings.startRatio, green * Manager.settings.endRatio, durationRatio));
        int blueRemaining = MathHelper.floor(MathHelper.clampedLerp(blue * Manager.settings.startRatio, blue * Manager.settings.endRatio, durationRatio));
        args.set(4, (redRemaining << 16 | greenRemaining << 8 | blueRemaining) - 16777216);
    }

    @ModifyArgs(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void modifyTranslation(Args args) {
        float width = -((float) args.get(0) - this.client.getWindow().getScaledWidth() + 2);
        float height = -((float) args.get(1) - this.client.getWindow().getScaledHeight() + 35);
        args.set(0, this.client.getWindow().getScaledWidth() - (width + 1) * Math.abs(Manager.settings.scale) - Manager.settings.sideMargin);
        args.set(1, this.client.getWindow().getScaledHeight() - (height + 5) * Math.abs(Manager.settings.scale) - Manager.settings.bottomMargin);
    }

    @ModifyArgs(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"))
    private void modifyScale(Args args) {
        args.set(0, Manager.settings.scale);
        args.set(1, Manager.settings.scale);
    }

    @ModifyArgs(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void modifyFill(Args args) {
        args.set(4, Manager.settings.backgroundColor);
    }
}