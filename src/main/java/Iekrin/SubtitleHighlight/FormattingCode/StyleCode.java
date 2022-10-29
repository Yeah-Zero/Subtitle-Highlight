package Iekrin.SubtitleHighlight.FormattingCode;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum StyleCode {
    随机('k'), 粗体('l'), 删除线('m'), 下划线('n'), 斜体('o'), 重置('r');

    private final Formatting 格式代码;

    StyleCode(char 颜色代码) {
        this.格式代码 = Formatting.byCode(颜色代码);
    }

    public Formatting 获取格式代码() {
        return this.格式代码;
    }

    @Override
    public String toString() {
        return this.格式代码.toString() + Text.translatable("formatting_code." + this.格式代码.getName()).getString();
    }
}
