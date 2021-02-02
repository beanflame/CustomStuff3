package cubex2.cs3.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs3.lib.CreatureTypes;
import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.StringProviderPurpose;
import cubex2.cs3.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;
import java.util.Map;

public class MobSpawn extends BaseContent implements IPurposeStringProvider
{
    public String mob;
    public int rate;
    public int min;
    public int max;
    public EnumCreatureType type;
    public List<BiomeGenBase> biomes = Lists.newArrayList();

    private Map<BiomeGenBase, BiomeGenBase.SpawnListEntry> entries = Maps.newHashMap();

    public MobSpawn(BaseContentPack pack)
    {
        super(pack);
    }

    public MobSpawn(String mob, int rate, int min, int max, EnumCreatureType type, List<BiomeGenBase> biomes, BaseContentPack pack)
    {
        super(pack);
        this.mob = mob;
        this.rate = rate;
        this.min = min;
        this.max = max;
        this.type = type;
        this.biomes = biomes;
    }


    @Override
    public void apply()
    {
        addEntries();

        super.apply();
    }

    @SuppressWarnings("unchecked")
    private void addEntries()
    {
        Class<? extends Entity> entityClazz = (Class<? extends Entity>) EntityList.stringToClassMapping.get(mob);

        if (EntityLiving.class.isAssignableFrom(entityClazz))
        {
            Class<? extends EntityLiving> clazz = (Class<? extends EntityLiving>) entityClazz;

            for (BiomeGenBase biome : biomes)
            {
                if (biome == null)
                    continue;

                List<BiomeGenBase.SpawnListEntry> spawns = biome.getSpawnableList(type);

                BiomeGenBase.SpawnListEntry entry = new BiomeGenBase.SpawnListEntry(clazz, rate, min, max);
                spawns.add(entry);
                entries.put(biome, entry);
            }
        }
    }

    @Override
    public void edit()
    {
        removeEntries();
        addEntries();

        super.edit();
    }


    @Override
    public void remove()
    {
        removeEntries();

        super.remove();
    }

    @SuppressWarnings("unchecked")
    private void removeEntries()
    {
        for (BiomeGenBase biome : entries.keySet())
        {
            List<BiomeGenBase.SpawnListEntry> spawns = biome.getSpawnableList(type);
            if (entries.containsKey(biome))
            {
                spawns.remove(entries.get(biome));
            }
        }
        entries.clear();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Mob", mob);
        compound.setInteger("Rate", rate);
        compound.setInteger("Min", min);
        compound.setInteger("Max", max);
        compound.setString("Type", CreatureTypes.getName(type));
        Util.writeListToNBT("Biomes", biomes, Util.NBT_BIOME_WRITER, compound);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        mob = compound.getString("Mob");
        rate = compound.getInteger("Rate");
        min = compound.getInteger("Min");
        max = compound.getInteger("Max");
        type = CreatureTypes.getType(compound.getString("Type"));
        biomes = Lists.newArrayList();
        Util.readListFromNBT("Biomes", biomes, Util.NBT_BIOME_READER, compound);

        return !biomes.contains(null);
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        if (purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl)
        {
            return mob + " (" + CreatureTypes.getName(type) + ")";
        }
        return null;
    }
}
