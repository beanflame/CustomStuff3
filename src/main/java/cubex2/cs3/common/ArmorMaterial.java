package cubex2.cs3.common;

import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;

public class ArmorMaterial extends BaseContent implements IPurposeStringProvider
{
    public ItemArmor.ArmorMaterial material;

    public String name;
    public int enchantability;
    public int durability;
    public int[] reductionAmounts;

    public ArmorMaterial(BaseContentPack pack)
    {
        super(pack);
    }

    public ArmorMaterial(String name, int durability, int enchantability, int[] reductionAmounts, BaseContentPack pack)
    {
        super(pack);
        this.name = name;
        this.durability = durability;
        this.reductionAmounts = reductionAmounts;
        this.enchantability = enchantability;
    }

    @Override
    public void apply()
    {
        material = EnumHelper.addArmorMaterial(name, durability, reductionAmounts, enchantability);

        super.apply();
    }

    @Override
    public boolean canEdit()
    {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", name);
        compound.setInteger("Enchantability", enchantability);
        compound.setInteger("Durability", durability);
        compound.setIntArray("ReductionAmount", reductionAmounts);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        enchantability = compound.getInteger("Enchantability");
        durability = compound.getInteger("Durability");
        reductionAmounts = compound.getIntArray("ReductionAmount");

        return true;
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        if (purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl)
        {
            return name;
        }
        return null;
    }
}
