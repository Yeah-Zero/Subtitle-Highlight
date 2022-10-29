package Iekrin.SubtitleHighlight.Configure;

import Iekrin.SubtitleHighlight.FormattingCode.ColorCode;

import java.util.ArrayList;

public class Setting {
    public long 最长持续时间 = 3000;
    public double 起始比例 = 1;
    public double 终止比例 = 0.29411764705882354;
    public ColorSetting 基本颜色设置 = new ColorSetting();
    public ArrayList<Custom> 自定义 = new ArrayList<>();

    public class ColorSetting {
        public ColorCode 环境 = ColorCode.深红色;
        public Block 方块 = new Block();
        public ColorCode 魔咒 = ColorCode.紫色;
        public Entity 实体 = new Entity();
        public ColorCode 事件 = ColorCode.红色;
        public ColorCode 物品;
        public ColorCode 粒子 = ColorCode.紫色;
        public ColorCode 用户界面 = ColorCode.天蓝色;
        public ColorCode 天气 = ColorCode.蓝色;

        public class Block {
            public ColorCode 互动 = ColorCode.天蓝色;
            public ColorCode 运作 = ColorCode.湖蓝色;
            public ColorCode 危险 = ColorCode.红色;
            public ColorCode 作物 = ColorCode.深绿色;
            public ColorCode 其它 = ColorCode.深灰色;
        }

        public class Entity {
            public Mob 生物 = new Mob();
            public ColorCode 载具 = ColorCode.灰色;
            public ColorCode 弹射物 = ColorCode.金色;
            public ColorCode 爆炸物 = ColorCode.红色;
            public ColorCode 装饰品 = ColorCode.灰色;
            public ColorCode 其它 = ColorCode.深灰色;

            public class Mob {
                public ColorCode 玩家 = ColorCode.白色;
                public ColorCode 被动生物 = ColorCode.绿色;
                public ColorCode 中立生物 = ColorCode.黄色;
                public ColorCode 敌对生物 = ColorCode.红色;
                public ColorCode 头目生物 = ColorCode.粉红色;
            }
        }
    }

    public class Custom {
        public String 本地化键名;
        public String 格式化代码;
        public String 文本;

        Custom(String 本地化键名, String 格式化代码, String 文本) {
            this.本地化键名 = 本地化键名;
            this.格式化代码 = 格式化代码;
            this.文本 = 文本;
        }
    }
}
