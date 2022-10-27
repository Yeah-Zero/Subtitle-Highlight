package Iekrin.SubtitleHighlight.Configure;

import Iekrin.SubtitleHighlight.FormattingCode.ColorCode;

import java.util.ArrayList;

public class Setting {
    public long 最长持续时间;
    public double 起始比例;
    public double 终止比例;
    public ColorSetting 基本颜色设置;
    public ArrayList<Custom> 自定义;

    public static class ColorSetting {
        public ColorCode 环境;
        public ColorCode 方块;
        public ColorCode 魔咒;
        public Entity 实体;
        public ColorCode 事件;
        public ColorCode 物品;
        public ColorCode 粒子;
        public ColorCode 用户界面;
        public ColorCode 天气;

        public static class Entity {
            public Mob 生物;
            public ColorCode 载具;
            public ColorCode 弹射物;
            public ColorCode 其它;

            public static class Mob {
                public ColorCode 玩家;
                public ColorCode 被动生物;
                public ColorCode 中立生物;
                public ColorCode 敌对生物;
                public ColorCode 头目生物;
            }
        }
    }

    public static class Custom {
        public String 本地化键名;
        public String 格式化代码;
        public String 文本;
    }
}
