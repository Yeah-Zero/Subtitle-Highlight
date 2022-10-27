package Iekrin.SubtitleHighlight.Mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SubtitlesHud.SubtitleEntry.class)
public class SubtitleEntryMixin {
    private final String[] 被动生物 = {"allay", "axolotl", "bat", "cat", "chicken", "cod", "cow", "donkey", "fox", "frog", "glow_squid", "horse", "mooshroom", "mule", "ocelot", "parrot", "pig", "puffer_fish", "rabbit", "salmon", "sheep", "skeleton_horse", "snow_golem", "squid", "strider", "tadpole", "tropical_fish", "turtle", "villager", "wandering_trader"};
    @Shadow
    @Final
    private Text text;

    @Inject(at = @At("RETURN"), method = "getText()Lnet/minecraft/text/Text;", cancellable = true)
    private void 字幕着色(CallbackInfoReturnable<Text> 可返回回调信息) {
        可返回回调信息.setReturnValue(this.text);
    }
}
