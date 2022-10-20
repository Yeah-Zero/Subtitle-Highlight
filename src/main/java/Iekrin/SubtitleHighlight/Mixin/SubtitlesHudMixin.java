package Iekrin.SubtitleHighlight.Mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static net.minecraft.client.gui.DrawableHelper.fill;

@Environment(EnvType.CLIENT)
@org.spongepowered.asm.mixin.Mixin(SubtitlesHud.class)
public class SubtitlesHudMixin {
    private static final double 最长持续时间 = 3000;
    private static final double 起始比例 = 1;
    private static final double 终止比例 = 75 / 255d;
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    @Final
    private List<SubtitlesHud.SubtitleEntry> entries;
    @Mutable
    @Shadow
    @Final
    private boolean enabled;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void render(MatrixStack matrices) {
        if (!this.enabled && this.client.options.getShowSubtitles().getValue()) {
            this.client.getSoundManager().registerListener((SoundInstanceListener) this);
            this.enabled = true;
        } else if (this.enabled && !(Boolean) this.client.options.getShowSubtitles().getValue()) {
            this.client.getSoundManager().unregisterListener((SoundInstanceListener) this);
            this.enabled = false;
        }
        if (this.enabled && !this.entries.isEmpty()) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            assert this.client.player != null;
            Vec3d vec3d = new Vec3d(this.client.player.getX(), this.client.player.getEyeY(), this.client.player.getZ());
            Vec3d vec3d2 = (new Vec3d(0.0, 0.0, -1.0)).rotateX(-this.client.player.getPitch() * 0.017453292F).rotateY(-this.client.player.getYaw() * 0.017453292F);
            Vec3d vec3d3 = (new Vec3d(0.0, 1.0, 0.0)).rotateX(-this.client.player.getPitch() * 0.017453292F).rotateY(-this.client.player.getYaw() * 0.017453292F);
            Vec3d vec3d4 = vec3d2.crossProduct(vec3d3);
            int i = 0;
            int j = 0;
            Iterator<SubtitlesHud.SubtitleEntry> iterator = this.entries.iterator();
            SubtitlesHud.SubtitleEntry subtitleEntry;
            while (iterator.hasNext()) {
                subtitleEntry = iterator.next();
                if (subtitleEntry.getTime() + 最长持续时间 <= Util.getMeasuringTimeMs()) {
                    iterator.remove();
                } else {
                    j = Math.max(j, this.client.textRenderer.getWidth(subtitleEntry.getText()));
                }
            }
            j += this.client.textRenderer.getWidth("<") + this.client.textRenderer.getWidth(" ") + this.client.textRenderer.getWidth(">") + this.client.textRenderer.getWidth(" ");
            for (iterator = this.entries.iterator(); iterator.hasNext(); ++i) {
                subtitleEntry = iterator.next();
                Text text = subtitleEntry.getText();
                System.out.println(text.getString());
                Vec3d vec3d5 = subtitleEntry.getPosition().subtract(vec3d).normalize();
                double d = -vec3d4.dotProduct(vec3d5);
                double e = -vec3d2.dotProduct(vec3d5);
                boolean bl = e > 0.5;
                int l = j / 2;
                Objects.requireNonNull(this.client.textRenderer);
                int m = 9;
                int n = m / 2;
                int o = this.client.textRenderer.getWidth(text);
                double 持续时间比例 = (Util.getMeasuringTimeMs() - subtitleEntry.getTime()) / 最长持续时间;
                int p = MathHelper.floor(MathHelper.clampedLerp(255 * 起始比例, 255 * 终止比例, 持续时间比例));
                int q = p << 16 | p << 8 | p;
                matrices.push();
                matrices.translate((float) this.client.getWindow().getScaledWidth() - (float) l - 2.0F, (float) (this.client.getWindow().getScaledHeight() - 35) - (float) (i * (m + 1)), 0.0);
                matrices.scale(1.0F, 1.0F, 1.0F);
                fill(matrices, -l - 1, -n - 1, l + 1, n + 1, this.client.options.getTextBackgroundColor(0.8F));
                RenderSystem.enableBlend();
                if (!bl) {
                    if (d > 0.0) {
                        this.client.textRenderer.draw(matrices, ">", (float) (l - this.client.textRenderer.getWidth(">")), (float) (-n), q + -16777216);
                    } else if (d < 0.0) {
                        this.client.textRenderer.draw(matrices, "<", (float) (-l), (float) (-n), q + -16777216);
                    }
                }
                Text 去色文字 = (Text.literal(text.getString())).setStyle(Style.EMPTY.withColor((TextColor) null).withBold(text.getStyle().isBold()).withItalic(text.getStyle().isItalic()).withUnderline(text.getStyle().isUnderlined()).withStrikethrough(text.getStyle().isStrikethrough()).withObfuscated(text.getStyle().isObfuscated()).withClickEvent(text.getStyle().getClickEvent()).withHoverEvent(text.getStyle().getHoverEvent()).withInsertion(text.getStyle().getInsertion()).withFont(text.getStyle().getFont()));
                int 红, 绿, 蓝;
                if (text.getStyle().getColor() != null) {
                    红 = (text.getStyle().getColor().getRgb() >> 16) & 255;
                    绿 = (text.getStyle().getColor().getRgb() >> 8) & 255;
                    蓝 = text.getStyle().getColor().getRgb() & 255;
                } else {
                    红 = 绿 = 蓝 = 255;
                }
                int 红色剩余 = MathHelper.floor(MathHelper.clampedLerp(红 * 起始比例, 红 * 终止比例, 持续时间比例));
                int 绿色剩余 = MathHelper.floor(MathHelper.clampedLerp(绿 * 起始比例, 绿 * 终止比例, 持续时间比例));
                int 蓝色剩余 = MathHelper.floor(MathHelper.clampedLerp(蓝 * 起始比例, 蓝 * 终止比例, 持续时间比例));
                int 文字显示颜色 = 红色剩余 << 16 | 绿色剩余 << 8 | 蓝色剩余;
                this.client.textRenderer.draw(matrices, 去色文字, (float) (-o / 2), (float) (-n), 文字显示颜色 + -16777216);
                matrices.pop();
            }
            RenderSystem.disableBlend();
        }
    }
}
