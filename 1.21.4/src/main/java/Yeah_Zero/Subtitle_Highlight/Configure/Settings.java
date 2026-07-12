package Yeah_Zero.Subtitle_Highlight.Configure;

import Yeah_Zero.Subtitle_Highlight.Util.ColorCode;
import net.minecraft.text.TextColor;

import java.util.ArrayList;

public class Settings {
    public long maxDuration = 3000;
    public double startRatio = 1;
    public double endRatio = 0.29411764705882354;
    public int backgroundColor = 0xcc000000;
    public float scale = 1;
    public float bottomMargin = 30;
    public float sideMargin = 1;
    public Boolean ikunEasterEgg = true;
    public ColorSetting colorSettings = new ColorSetting();
    public ArrayList<Custom> customList = new ArrayList<>();

    public class ColorSetting {
        public ColorCode ambient = ColorCode.DARK_BLUE;
        public Block block = new Block();
        public ColorCode enchant = ColorCode.DARK_PURPLE;
        public Entity entity = new Entity();
        public Item item = new Item();
        public ColorCode other = ColorCode.WHITE;

        public class Item {
            public ColorCode weapon = ColorCode.GOLD;
            public ColorCode armor = ColorCode.DARK_GREEN;
            public ColorCode tool = ColorCode.BLUE;
            public ColorCode other = ColorCode.WHITE;
        }

        public class Block {
            public ColorCode generic = ColorCode.WHITE;
            public ColorCode interact = ColorCode.AQUA;
            public ColorCode working = ColorCode.DARK_AQUA;
            public ColorCode dangerous = ColorCode.RED;
            public ColorCode crop = ColorCode.DARK_GREEN;
            public ColorCode other = ColorCode.DARK_GRAY;
        }

        public class Entity {
            public Mob mob = new Mob();
            public ColorCode vehicle = ColorCode.GRAY;
            public ColorCode projectile = ColorCode.GOLD;
            public ColorCode explosive = ColorCode.RED;
            public ColorCode decoration = ColorCode.GRAY;
            public ColorCode other = ColorCode.DARK_GRAY;

            public class Mob {

                public Player player = new Player();
                public ColorCode passive = ColorCode.GREEN;
                public ColorCode neutral = ColorCode.YELLOW;
                public ColorCode hostile = ColorCode.RED;
                public ColorCode boss = ColorCode.LIGHT_PURPLE;

                public class Player {
                    public ColorCode attack = ColorCode.GOLD;
                    public ColorCode hurt = ColorCode.DARK_RED;
                    public ColorCode other = ColorCode.WHITE;
                }
            }
        }
    }

    public class Custom {
        public String translationKey = "";
        public TextColor color = TextColor.fromRgb(0xffffff);
        public Boolean obfuscated = false;
        public Boolean bold = false;
        public Boolean strikethrough = false;
        public Boolean underline = false;
        public Boolean italic = false;
    }
}