package cubex2.cs3.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs3.api.IContentPack;
import cubex2.cs3.lib.ModInfo;
import cubex2.cs3.registry.*;
import cubex2.cs3.util.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BaseContentPack implements IContentPack, IPurposeStringProvider, Comparable<BaseContentPack>
{
    public final String name;
    public final String id;
    public final File directory;
    public final PostponeHandler postponeHandler = new PostponeHandler();
    protected final Logger logger;
    protected final List<ContentRegistry> contentRegistryList = Lists.newArrayList();
    protected final Map<Class<? extends Content>, ContentRegistry> contentRegistry = Maps.newHashMap();
    private final Map<String, ContentRegistry> nameToRegistryMap = Maps.newHashMap();
    private boolean initialized = false;

    public final SmeltingRecipeHandler smeltingRecipeHandler = new SmeltingRecipeHandler(this);
    public final FuelHandler fuelHandler = new FuelHandler(this);

    private boolean doSave = true;

    public BaseContentPack(File directory, String name, String id)
    {
        this.directory = directory;
        this.name = name;
        this.id = id;
        logger = Logger.getLogger(ModInfo.ID + "_" + name);

        registerContentRegistry(new OreDictEntryRegistry(this), OreDictionaryEntry.class);
        registerContentRegistry(new CreativeTabRegistry(this), CreativeTab.class);
        registerContentRegistry(new FuelRegistry(this), Fuel.class);
        registerContentRegistry(new SmeltingRecipeRegistry(this), SmeltingRecipe.class);
        registerContentRegistry(new ShapedRecipeRegistry(this), ShapedRecipe.class);
        registerContentRegistry(new ShapelessRecipeRegistry(this), ShapelessRecipe.class);
        registerContentRegistry(new ArmorMaterialRegistry(this), ArmorMaterial.class);
        registerContentRegistry(new ItemRegistry(this), WrappedItem.class);
        registerContentRegistry(new TileEntityRegistry(this), WrappedTileEntity.class);
        registerContentRegistry(new GuiRegistry(this), WrappedGui.class);
        registerContentRegistry(new BlockRegistry(this), WrappedBlock.class);
        registerContentRegistry(new WorldGenRegistry(this), WrappedWorldGen.class);
        registerContentRegistry(new TradeRecipeRegistry(this), TradeRecipe.class);
        registerContentRegistry(new GrassPlantRegistry(this), GrassPlant.class);
        registerContentRegistry(new GrassSeedRegistry(this), GrassSeed.class);
        registerContentRegistry(new ChestItemRegistry(this), ChestItem.class);
        registerContentRegistry(new MobSpawnRegistry(this), MobSpawn.class);
        registerContentRegistry(new DungeonMobRegistry(this), DungeonMob.class);

        registerContentRegistry(new MobDropRegistry(this), MobDrop.class);
    }

    public boolean isZipped()
    {
        return directory.isFile();
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public File getDirectory()
    {
        return directory;
    }

    @Override
    public Logger getLogger()
    {
        return logger;
    }

    public List<ContentRegistry> getRegistries()
    {
        return Collections.unmodifiableList(contentRegistryList);
    }

    public <T extends Content> ContentRegistry<T> getContentRegistry(T content)
    {
        return contentRegistry.get(content.getClass());
    }

    public ContentRegistry getContentRegistry(String name)
    {
        return nameToRegistryMap.get(name);
    }

    public <T extends Content> ContentRegistry<T> getContentRegistry(Class<T> clazz)
    {
        return contentRegistry.get(clazz);
    }

    private void registerContentRegistry(ContentRegistry registry, Class<? extends Content>... classes)
    {
        for (Class<? extends Content> clazz : classes)
        {
            contentRegistry.put(clazz, registry);
        }
        nameToRegistryMap.put(registry.getName(), registry);
        contentRegistryList.add(registry);
    }

    public void save()
    {
        if (!initialized || isZipped() || !doSave)
            return;

        NBTTagCompound compound = new NBTTagCompound();

        NBTTagList managerList = new NBTTagList();
        for (ContentRegistry contentManager : contentRegistryList)
        {
            NBTTagCompound managerTag = new NBTTagCompound();
            contentManager.writeToNBT(managerTag);
            managerList.appendTag(managerTag);
        }
        compound.setTag("ManagerList", managerList);

        IOHelper.writeNBTToFile(compound, new File(directory, "data.dat"));

        NBTHelper.dumpNBT(compound, new File(directory, "data_dump.txt"));
    }

    public void load()
    {
        NBTTagCompound compound = IOHelper.readNBTFromPath("data.dat", this);
        if (compound == null)
            return;

        NBTTagList managerList = compound.getTagList("ManagerList", 10);
        for (int i = 0; i < managerList.tagCount(); i++)
        {
            NBTTagCompound managerTag = managerList.getCompoundTagAt(i);
            String managerName = managerTag.getString("Name");
            ContentRegistry manager = getContentRegistry(managerName);
            manager.readFromNBT(managerTag);
        }
    }

    public void init()
    {
        load();
        initialized = true;
    }

    public void postInit()
    {
        for (ContentRegistry<?> registry : contentRegistryList)
        {
            for (Content content : registry.getContentList())
            {
                content.postInit();
            }
        }

        doSave = false;
        postponeHandler.executePostponedTasks();
        doSave = true;

        save();
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        return purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl ? getName() : null;
    }

    @Override
    public int compareTo(BaseContentPack o)
    {
        return name.compareTo(o.name);
    }
}
