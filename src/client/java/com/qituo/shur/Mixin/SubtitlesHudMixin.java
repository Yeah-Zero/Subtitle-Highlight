package com.qituo.shur.Mixin;

import com.qituo.shur.Configure.Manager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.lang.reflect.Method;
import java.util.Iterator;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net.minecraft.client.gui.hud.SubtitlesHud")
public class SubtitlesHudMixin {
    private static double durationRatio;
    @Shadow
    @Final
    private MinecraftClient client;
    
    // 缓存反射方法引用，避免每次渲染都进行反射查找
    private static volatile Method getTimeMethod = null;
    private static volatile Method drawTextWithShadowMethod = null;
    private static volatile Class<?> drawContextClass = null;

    @Redirect(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", ordinal = 0))
    private Object getSubtitleEntry(Iterator instance) {
        Object subtitleEntry = instance.next();
        try {
            // 使用缓存的反射方法
            Method method = getTimeMethod(subtitleEntry.getClass());
            long time = (long) method.invoke(subtitleEntry);
            durationRatio = (Util.getMeasuringTimeMs() - time) / (double) (Manager.settings.maxDuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subtitleEntry;
    }

    @Redirect(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud$SubtitleEntry;getTime()J"))
    private long redirectSubtitleTime(Object instance) {
        try {
            // 使用缓存的反射方法
            Method method = getTimeMethod(instance.getClass());
            long time = (long) method.invoke(instance);
            return (long) (time - 3000 * this.client.options.getNotificationDisplayTime().getValue() + Manager.settings.maxDuration * this.client.options.getNotificationDisplayTime().getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    private static Method getTimeMethod(Class<?> clazz) throws NoSuchMethodException {
        if (getTimeMethod == null) {
            synchronized (SubtitlesHudMixin.class) {
                if (getTimeMethod == null) {
                    getTimeMethod = clazz.getMethod("getTime");
                }
            }
        }
        return getTimeMethod;
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

    @Redirect(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I", ordinal = 0))
    private int redirectDrawTextWithShadow(Object drawContext, Object textRenderer, Object text, int x, int y, int color) {
        // 检查字幕是否在视锥体范围内
        if (!isInFrustum()) {
            return 0;
        }
        // 调用原始方法（使用缓存的反射方法）
        try {
            Method method = getDrawTextWithShadowMethod(drawContext.getClass(), textRenderer.getClass(), text.getClass());
            return (int) method.invoke(drawContext, textRenderer, text, x, y, color);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    private static Method getDrawTextWithShadowMethod(Class<?> drawContextClazz, Class<?> textRendererClazz, Class<?> textClazz) throws NoSuchMethodException {
        if (drawTextWithShadowMethod == null) {
            synchronized (SubtitlesHudMixin.class) {
                if (drawTextWithShadowMethod == null) {
                    drawTextWithShadowMethod = drawContextClazz.getMethod("drawTextWithShadow", textRendererClazz, textClazz, int.class, int.class, int.class);
                }
            }
        }
        return drawTextWithShadowMethod;
    }

    private boolean isInFrustum() {
        // 检查字幕是否在屏幕范围内
        if (client == null || client.getWindow() == null) {
            return false;
        }
        
        // 简单的屏幕范围检查
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        
        // 这里可以添加更复杂的视锥体检查逻辑
        // 例如使用 Frustum 类的方法来检查点是否在视锥体范围内
        
        return true;
    }
}