package cubex2.cs3.common;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DungeonHooks;

import java.util.ArrayList;

public class DungeonMob extends BaseContent implements IPurposeStringProvider
{
    public String mob;
    public int rarity;

    private DungeonHooks.DungeonMob entry;

    public DungeonMob(BaseContentPack pack)
    {
        super(pack);
    }

    public DungeonMob(String mob, int rarity, BaseContentPack pack)
    {
        super(pack);
        this.mob = mob;
        this.rarity = rarity;
    }

    @Override
    public void apply()
    {
        ArrayList<DungeonHooks.DungeonMob> dungeonMobs = ReflectionHelper.getPrivateValue(DungeonHooks.class, null, "dungeonMobs");
        entry = new DungeonHooks.DungeonMob(rarity, mob);
        dungeonMobs.add(entry);

        super.apply();
    }

    @Override
    public void edit()
    {
        entry.type = mob;
        entry.itemWeight = rarity;

        super.edit();
    }

    @Override
    public void remove()
    {
        ArrayList<DungeonHooks.DungeonMob> dungeonMobs = ReflectionHelper.getPrivateValue(DungeonHooks.class, null, "dungeonMobs");
        dungeonMobs.remove(entry);

        super.remove();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Mob", mob);
        compound.setInteger("Rarity", rarity);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        mob = compound.getString("Mob");
        rarity = compound.getInteger("Rarity");
        return true;
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        if (purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl)
        {
            return mob + " (" + rarity + ")";
        }
        return null;
    }
}
