package Iekrin.SubtitleHighlight.Configure;

import java.util.ArrayList;

public class Entry {
    public long 最长持续时间;
    public double 起始比例;
    public double 终止比例;
    public ColorSetting 基本颜色设置;
    public String 事件;
    public String 物品;
    public String 粒子;
    public String 用户界面;
    public String 天气;
    public ArrayList<CustomEntry> 自定义;

    public static class ColorSetting {
        public String 环境;
        public String 方块;
        public String 魔咒;
        public Entity 实体;

        public static class Entity {
            public Mob 生物;
            public String 载具;
            public String 弹射物;
            public String 其它;

            public static class Mob {
                public String 玩家;
                public String 被动生物;
                public String 中立生物;
                public String 敌对生物;
                public String 头目生物;
            }
        }
    }

    public static class CustomEntry {
        public String 本地化键名;
        public String 格式化代码;
    }
}
