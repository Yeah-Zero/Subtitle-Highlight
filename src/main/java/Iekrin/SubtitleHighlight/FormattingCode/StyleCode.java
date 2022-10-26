package Iekrin.SubtitleHighlight.FormattingCode;

public enum StyleCode {
    随机("§k"), 粗体("§l"), 删除线("§m"), 下划线("§n"), 斜体("§o"), 重置("§r");
    private final String 格式代码;

    StyleCode(String 格式代码) {
        this.格式代码 = 格式代码;
    }

    public String 获取格式() {
        return this.格式代码;
    }

    @Override
    public String toString() {
        return this.格式代码 + this.name();
    }
}
