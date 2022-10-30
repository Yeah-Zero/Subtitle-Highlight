package Yeah_Zero.Subtitle_Highlight.Mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(SimpleOption.class)
public interface SimpleOptionMixin<T> {
    @Accessor("defaultValue")
    T 获取默认值();

    @Accessor("defaultValue")
    void 设定默认值(T 设定值);
}