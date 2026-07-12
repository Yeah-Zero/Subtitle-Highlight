package Yeah_Zero.Subtitle_Highlight.Util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum ColorCode {
    BLACK('0'), DARK_BLUE('1'), DARK_GREEN('2'), DARK_AQUA('3'), DARK_RED('4'), DARK_PURPLE('5'), GOLD('6'), GRAY('7'), DARK_GRAY('8'), BLUE('9'), GREEN('a'), AQUA('b'), RED('c'), LIGHT_PURPLE('d'), YELLOW('e'), WHITE('f');
    private final Formatting formatting;

    ColorCode(char colorCode) {
        this.formatting = Formatting.byCode(colorCode);
    }

    public static Text formatTranslation(Formatting formatting) {
        return Text.translatable("formatting_code." + formatting.getName()).formatted(formatting);
    }

    public static Text colorTranslation(Enum<ColorCode> enumType) {
        if (enumType instanceof ColorCode colorCode) {
            return formatTranslation(colorCode.getFormatting());
        } else {
            return Text.literal("????");
        }
    }

    public Formatting getFormatting() {
        return this.formatting;
    }

    public static ColorCode fromName(String name) {
        for (ColorCode colorCode : ColorCode.values()) {
            if (colorCode.name().toLowerCase().equals(name)) {
                return colorCode;
            }
        }
        return null;
    }
}