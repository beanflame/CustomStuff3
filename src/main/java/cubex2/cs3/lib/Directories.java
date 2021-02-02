package cubex2.cs3.lib;

import java.io.File;

public class Directories
{
    public static final String MAIN_DIRECTORY_NAME = "CustomStuff3";

    /* Relative to assets/[mod name] */
    public static final String TEXTURES = "textures";

    /* Relative to assets/[mod name]/textures */
    public static final String BLOCK_TEXTURES = "blocks";
    public static final String ITEM_TEXTURES = "items";
    public static final String GUI_TEXTURES = "gui";
    public static final String CHEST_TEXTURES = "entity/chest";
    public static final String ARMOR_TEXTURES = "models/armor";

    public static File MAIN_DIRECTORY;
    public static File MODS;

    public static void init(File baseDir)
    {
        MAIN_DIRECTORY = baseDir;
        MODS = new File(baseDir, "mods");

        MAIN_DIRECTORY.mkdirs();
    }
}
