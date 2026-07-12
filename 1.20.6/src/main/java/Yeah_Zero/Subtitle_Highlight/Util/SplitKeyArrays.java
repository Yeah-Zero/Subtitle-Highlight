package Yeah_Zero.Subtitle_Highlight.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SplitKeyArrays {
    public static final String[] friendlyMobs = {"allay", "axolotl", "bat", "camel", "cat", "chicken", "cod", "cow", "donkey", "fox", "frog", "glow_squid", "horse", "mooshroom", "mule", "ocelot", "parrot", "pig", "puffer_fish", "rabbit", "salmon", "sheep", "skeleton_horse", "sniffer", "snow_golem", "squid", "strider", "tadpole", "tropical_fish", "turtle", "villager", "wandering_trader", "zombie_horse"};
    public static final String[] neutralMobs = {"bee", "dolphin", "enderman", "goat", "iron_golem", "llama", "panda", "piglin", "polar_bear", "spider", "wolf", "zombified_piglin"};
    public static final String[] hostileMobs = {"blaze", "creeper", "drowned", "elder_guardian", "endermite", "evoker", "ghast", "guardian", "hoglin", "husk", "illusioner", "magma_cube", "phantom", "piglin_brute", "pillager", "ravager", "shulker", "silverfish", "skeleton", "slime", "stray", "vex", "vindicator", "warden", "witch", "wither_skeleton", "zoglin", "zombie", "zombie_villager"};
    public static final String[] bossMobs = {"ender_dragon", "wither"};
    public static final String[] vehicles = {"boat", "minecart"};
    public static final String[] projectiles = {"arrow", "egg", "ender_eye", "ender_pearl", "potion", "shulker_bullet", "snowball"};
    public static final String[] explosives = {"firework_rocket", "lightning_bolt", "tnt"};
    public static final String[] decorations = {"armor_stand", "glow_item_frame", "item_frame", "painting"};
    public static final String[] hurt = {"big_fall", "burn", "death", "explode", "extinguish_fire", "hurt", "small_fall", "freeze_hurt", "hurt_drown", "hurt_on_fire"};
    public static final String[] interact = {"anvil", "barrel", "bell", "big_dripleaf", "button", "cake", "chest", "comparator", "composter", "door", "enchantment_table", "end_portal_frame", "fence_gate", "grindstone", "growing_plant", "honey_block", "lever", "note_block", "pressure_plate", "pumpkin", "respawn_anchor", "sculk_sensor", "shulker_box", "sign", "smithing_table", "sweet_berry_bush", "trapdoor", "tripwire"};
    public static final String[] working = {"amethyst_block", "beacon", "beehive", "blastfurnace", "brewing_stand", "bubble_column", "candle", "conduit", "decorated_pot", "dispenser", "end_portal", "furnace", "iron_trapdoor", "piston", "pointed_dripstone", "portal", "redstone_torch", "sculk", "sculk_catalyst", "smoker", "sniffer_egg", "water"};
    public static final String[] dangerousBlocks = {"campfire", "fire", "lava", "sculk_shrieker"};
    public static final String[] crops = {"chorus_flower", "frogspawn"};
    public static final String[] weapons = {"crossbow", "trident"};
    public static final String[] armors = {"armor", "shield"};
    public static final String[] tools = {"axe", "bottle", "brush", "bucket", "bundle", "flintandsteel", "hoe", "goat_horn", "lodestone_compass", "shears", "shovel", "spyglass", "totem"};

    public static final Set<String> friendlyMobsSet = new HashSet<>(Arrays.asList(friendlyMobs));
    public static final Set<String> neutralMobsSet = new HashSet<>(Arrays.asList(neutralMobs));
    public static final Set<String> hostileMobsSet = new HashSet<>(Arrays.asList(hostileMobs));
    public static final Set<String> bossMobsSet = new HashSet<>(Arrays.asList(bossMobs));
    public static final Set<String> vehiclesSet = new HashSet<>(Arrays.asList(vehicles));
    public static final Set<String> projectilesSet = new HashSet<>(Arrays.asList(projectiles));
    public static final Set<String> explosivesSet = new HashSet<>(Arrays.asList(explosives));
    public static final Set<String> decorationsSet = new HashSet<>(Arrays.asList(decorations));
    public static final Set<String> hurtSet = new HashSet<>(Arrays.asList(hurt));
    public static final Set<String> interactSet = new HashSet<>(Arrays.asList(interact));
    public static final Set<String> workingSet = new HashSet<>(Arrays.asList(working));
    public static final Set<String> dangerousBlocksSet = new HashSet<>(Arrays.asList(dangerousBlocks));
    public static final Set<String> cropsSet = new HashSet<>(Arrays.asList(crops));
    public static final Set<String> weaponsSet = new HashSet<>(Arrays.asList(weapons));
    public static final Set<String> armorsSet = new HashSet<>(Arrays.asList(armors));
    public static final Set<String> toolsSet = new HashSet<>(Arrays.asList(tools));
}