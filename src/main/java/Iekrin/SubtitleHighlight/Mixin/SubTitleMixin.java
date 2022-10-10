package Iekrin.SubtitleHighlight.Mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static Iekrin.SubtitleHighlight.Initializer.Main.记录器;

@Environment(EnvType.CLIENT)
@org.spongepowered.asm.mixin.Mixin(SubtitlesHud.SubtitleEntry.class)
public class SubTitleMixin {
    @Shadow
    @Final
    private Text text;

    @Inject(at = @At("RETURN"), method = "getText()Lnet/minecraft/text/Text;", cancellable = true)
    private void 字幕高亮(CallbackInfoReturnable<Text> 可返回回调信息) {
        记录器.info(((TranslatableTextContent) this.text.getContent()).getKey());
        可返回回调信息.setReturnValue(((MutableText) this.text).formatted(Formatting.OBFUSCATED));
    }
}
