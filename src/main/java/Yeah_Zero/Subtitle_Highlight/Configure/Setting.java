package Yeah_Zero.Subtitle_Highlight.Configure;

import Yeah_Zero.Subtitle_Highlight.FormattingCode.ColorCode;
import net.minecraft.text.TextColor;

import java.util.ArrayList;

public class Setting {
    public long 最长持续时间 = 3000;
    public double 起始比例 = 1;
    public double 终止比例 = 0.29411764705882354;
    public ColorSetting 基本颜色设置 = new ColorSetting();
    public ArrayList<Custom> 自定义列表 = new ArrayList<>();

    public class ColorSetting {
        public ColorCode 环境 = ColorCode.深蓝色;
        public Block 方块 = new Block();
        public ColorCode 魔咒 = ColorCode.紫色;
        public Entity 实体 = new Entity();
        public Item 物品 = new Item();
        public ColorCode 其它 = ColorCode.白色;

        public class Item {
            public ColorCode 武器 = ColorCode.蓝色;
            public ColorCode 防具 = ColorCode.灰色;
            public ColorCode 工具 = ColorCode.蓝色;
            public ColorCode 物品_其它 = ColorCode.白色;
        }

        public class Block {
            public ColorCode 通用 = ColorCode.白色;
            public ColorCode 互动 = ColorCode.天蓝色;
            public ColorCode 运作 = ColorCode.湖蓝色;
            public ColorCode 危险方块 = ColorCode.红色;
            public ColorCode 农作物 = ColorCode.深绿色;
            public ColorCode 方块_其它 = ColorCode.深灰色;
        }

        public class Entity {
            public Mob 生物 = new Mob();
            public ColorCode 载具 = ColorCode.灰色;
            public ColorCode 弹射物 = ColorCode.金色;
            public ColorCode 爆炸物 = ColorCode.红色;
            public ColorCode 装饰品 = ColorCode.灰色;
            public ColorCode 实体_其它 = ColorCode.深灰色;

            public class Mob {

                public Player 玩家 = new Player();
                public ColorCode 被动生物 = ColorCode.绿色;
                public ColorCode 中立生物 = ColorCode.黄色;
                public ColorCode 敌对生物 = ColorCode.红色;
                public ColorCode 头目生物 = ColorCode.粉红色;

                public class Player {
                    public ColorCode 攻击 = ColorCode.金色;
                    public ColorCode 受伤 = ColorCode.深红色;
                    public ColorCode 玩家_其它 = ColorCode.白色;
                }
            }
        }
    }

    public class Custom {
        public String 本地化键名 = "";
        public TextColor 颜色 = TextColor.fromRgb(0xffffff);
        public Boolean 随机 = false;
        public Boolean 粗体 = false;
        public Boolean 删除线 = false;
        public Boolean 下划线 = false;
        public Boolean 斜体 = false;
    }
}
