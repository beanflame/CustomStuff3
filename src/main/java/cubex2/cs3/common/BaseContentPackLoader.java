package cubex2.cs3.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLCommonHandler;
import cubex2.cs3.asm.ICSMod;
import cubex2.cs3.asm.ModGenData;
import cubex2.cs3.asm.ModGenerator;
import cubex2.cs3.lib.Directories;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BaseContentPackLoader
{
    private static final BaseContentPackLoader instance = new BaseContentPackLoader();

    private List<BaseContentPack> contentPacks = Lists.newArrayList();
    private Map<ICSMod, BaseContentPack> contentPackMap = Maps.newHashMap();

    private BaseContentPackLoader()
    {
    }

    public static BaseContentPackLoader instance()
    {
        return instance;
    }

    public List<BaseContentPack> getContentPacks()
    {
        return contentPacks;
    }

    public BaseContentPack getContentPack(String name)
    {
        for (BaseContentPack pack : contentPacks)
        {
            if (pack.getName().equals(name))
                return pack;
        }

        return null;
    }

    public BaseContentPack getContentPackById(String id)
    {
        for (BaseContentPack pack : contentPacks)
        {
            if (pack.id.equals(id))
                return pack;
        }

        return null;
    }

    public void createContentPack(String name, String id)
    {
        File directory = new File(Directories.MODS, id);
        directory.mkdirs();

        File textures = new File(directory, "assets/" + id.toLowerCase() + "/" + Directories.TEXTURES);
        textures.mkdirs();
        new File(textures, Directories.ARMOR_TEXTURES).mkdirs();
        new File(textures, Directories.BLOCK_TEXTURES).mkdir();
        new File(textures, Directories.GUI_TEXTURES).mkdir();
        new File(textures, Directories.ITEM_TEXTURES).mkdir();
        new File(textures, Directories.CHEST_TEXTURES).mkdirs();

        ModGenData data = new ModGenData();
        data.modClassName = id.replace(" ", "");
        data.modId = id;
        data.modName = name;
        data.modVersion = "1.0.0";
        data.isIngamePack = true;

        new ModGenerator(data, directory);
    }

    public void onPreInitPack(ICSMod pack)
    {
        File packDir = FMLCommonHandler.instance().findContainerFor(pack).getSource();
        BaseContentPack ipack = new BaseContentPack(packDir, pack.getName(), pack.getId());
        contentPacks.add(ipack);
        contentPackMap.put(pack, ipack);
    }

    public void onInitPack(ICSMod pack)
    {
        contentPackMap.get(pack).init();
    }

    public void onPostInitPack(ICSMod pack)
    {
        contentPackMap.get(pack).postInit();
    }
}
