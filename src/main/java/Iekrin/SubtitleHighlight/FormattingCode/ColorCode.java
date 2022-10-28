package Iekrin.SubtitleHighlight.FormattingCode;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum ColorCode {
    黑色('0'), 深蓝色('1'), 深绿色('2'), 湖蓝色('3'), 深红色('4'), 紫色('5'), 金色('6'), 灰色('7'), 深灰色('8'), 蓝色('9'), 绿色('a'), 天蓝色('b'), 红色('c'), 粉红色('d'), 黄色('e'), 白色('f');
    private final Formatting 颜色格式;

    ColorCode(char 颜色代码) {
        this.颜色格式 = Formatting.byCode(颜色代码);
    }

    public Formatting 获取颜色格式() {
        return this.颜色格式;
    }

    @Override
    public String toString() {
        return this.颜色格式.toString() + Text.translatable("formatting_code." + this.颜色格式.getName()).getString();
    }
}
