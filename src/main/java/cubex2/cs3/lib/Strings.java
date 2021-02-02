package cubex2.cs3.lib;

public class Strings
{
    // NBT
    public static final String INT_DATA_PREFIX = "cs2intData_";
    public static final String STRING_DATA_PREFIX = "cs2stringData_";
    public static final String FLOAT_DATA_PREFIX = "cs2floatData_";

    public static final String FILE_MOD_CONFIGURATION = "config.ini";
    public static final String FILE_MOD = "mod.js";
    public static final String FILE_PACKMCMETA = "pack.mcmeta";

    public static final String FILE_INGAME_MOD = "mod.cs2";

    // Content registries
    public static final String REGISTRY_FUEL = "FuelRegistry";
    public static final String REGISTRY_SMELTING_RECIPE = "SmeltingRecipeRegistry";
    public static final String REGISTRY_ORE_DICT_ENTRY = "OreDictEntryRegistry";
    public static final String REGISTRY_SHAPED_RECIPE = "ShapedRecipeRegistry";
    public static final String REGISTRY_SHAPELESS_RECIPE = "ShapelessRecipeRegistry";
    public static final String REGISTRY_ITEM = "ItemRegistry";
    public static final String REGISTRY_BLOCK = "BlockRegistry";
    public static final String REGISTRY_WORLD_GEN = "WorldGenRegistry";
    public static final String REGISTRY_TRADE_RECIPE = "TradeRecipeRegistry";
    public static final String REGISTRY_GRASS_PLANT = "GrassPlantRegistry";
    public static final String REGISTRY_GRASS_SEED = "GrassSeedRegistry";
    public static final String REGISTRY_CHEST_ITEM = "ChestItemRegistry";
    public static final String REGISTRY_MOB_SPAWN = "MobSpawnRegistry";
    public static final String REGISTRY_DUNGEON_MOB = "DungeonMobRegistry";
    public static final String REGISTRY_CREATIVE_TAB = "CreativeTabRegistry";
    public static final String REGISTRY_ARMOR_MATERIAL = "ArmorMaterialRegistry";
    public static final String REGISTRY_MOB_DROP = "MobDropRegistry";
    public static final String REGISTRY_TILE_ENTITY = "TileEntityRegistry";
    public static final String REGISTRY_GUI = "GuiRegistry";

    // Tool tips
    public static final String TOOL_TIP_NEEDS_RESTART = "Restart of the game is required.";

    // GUI text
    public static final String INFO_NEW_PACK = "This has to be unique and|is used to reference items,|blocks and textures.";
    public static final String INFO_TEXURE_FILE = "The format is [modId]:[texture]|" +
            "[modId] is the mod's ID in all lowercase|" +
            "  this has to match the name of folder|" +
            "  located in the assets folder of the mod|" +
            "[texture] is name of the file without \".png\"||" +
            "Example: If the texture is located at|" +
            " \"assets/mymod/textures/items/myItem.png\"|" +
            "you have to enter|" +
            " \"mymod:myItem\"";
    public static final String INFO_TOOL_CLASS_BLOCK = "The tool class that is effective|" +
            " on this block. Notice that if|" +
            " the material is set to rock|" +
            " or iron, pickaxes are effective|" +
            " even if not specified here.|" +
            "The most common tool classes are:|" +
            " pickaxe|" +
            " axe|" +
            " shovel";
    public static final String INFO_HARVEST_LEVEL_BLOCK = "The harvest level that is|" +
            "required for the tool to|" +
            "be effective on this block.|" +
            " 0 = wood / gold|" +
            " 1 = stone|" +
            " 2 = iron|" +
            " 3 = diamond";
    public static final String INFO_TOOL_CLASS_ITEM = "The tool class specifies on what|" +
            " blocks the item is efficient.|" +
            "You can use multiple|" +
            " tool classes by using ',' as|" +
            " a separator.|" +
            "Common tool classes are:|" +
            " pickaxe|" +
            " axe|" +
            " shovel|" +
            "Special classes are:|" +
            " noHarvest - can't break any blocks|" +
            " all - effective on everything";
    public static final String INFO_HARVEST_LEVEL_ITEM = "The harvest level of the|" +
            "specified tool class.|" +
            " 0 = wood / gold|" +
            " 1 = stone|" +
            " 2 = iron|" +
            " 3 = diamond";
    public static final String INFO_DUNGEON_MOB_RARITY = "A higher value makes the|" +
            "mob more common.|" +
            " 100 = spider / skeleton|" +
            " 200 = zombie";
    public static final String INFO_FUEL_DURATION = "The duration in ticks|" +
            "(20 ticks = 1 second)|" +
            " 100 = stick|" +
            " 300 = wood|" +
            " 1600 = coal|";
    public static final String INFO_GRASS_PLANT_WEIGHT = "The weight of the plant|" +
            " 10 = red flowers|" +
            " 20 = yellow flowers";
    public static final String INFO_GRASS_SEED_WEIGHT = "The weight of the seed|" +
            " 10 = vanilla seeds";
    public static final String INFO_MOB_SPAWN_RATE = "The spawn rate of the mob|" +
            " 5 = witch|" +
            " 8 = cow|" +
            " 10 = enderman|" +
            " 100 = zombie";
    public static final String INFO_MOB_SPAWN_MIN = "1 = enderman|" +
            "4 = creeper|" +
            "8 = bat";
    public static final String INFO_MOB_SPAWN_MAX = "4 = enderman|" +
            "4 = creeper|" +
            "8 = bat";
    public static final String INFO_CHEST_ITEM_RARITY = "1 = golden apple (dungeon)|" +
            "3 = stone axe (bonus)|" +
            "10 = iron ingot (blacksmith)";
    public static final String INFO_DURABILITY_ARMOR = "The actual durability is this|" +
            "multiplied with these values:|" +
            "Helmet: 11|" +
            "Plate: 16|" +
            "Legs: 15|" +
            "Boots: 13";
    public static final String INFO_ARMOR_REDUCTION_AMOUNTS = "Each 1 point is half a shield";
    public static final String INFO_HUNGER = "Each 1 point is half a hunger stick.|" +
            "2 = melon|" +
            "4 = apple|" +
            "8 = steak";
    public static final String INFO_POTION_DURATION = "Duration in seconds";
    public static final String INFO_SATURATION = "0.1 = cookie|" +
            "0.3 = melon|" +
            "0.8 = steak";
    public static final String INFO_PROGRESS_DIRECTION = "If the progress is reversed,|" +
            "like for furnace fuel,|" +
            "you still need to select|" +
            "the direction in which the bar|" +
            "fills:||" +
            "bar empties downwards -> select up";


}
