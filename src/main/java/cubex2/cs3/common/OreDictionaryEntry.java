package cubex2.cs3.common;

import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.StackLabelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryEntry extends BaseContent implements StackLabelItem
{
    public String oreClass;
    public ItemStack stack;

    public OreDictionaryEntry(String oreClass, ItemStack stack, BaseContentPack pack)
    {
        super(pack);
        this.oreClass = oreClass;
        this.stack = stack;
    }

    public OreDictionaryEntry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public void apply()
    {
        OreDictionary.registerOre(oreClass, stack);

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
        compound.setString("OreClass", oreClass);
        compound.setTag("Stack", ItemStackHelper.writeToNBTNamed(stack));
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        oreClass = compound.getString("OreClass");
        stack = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Stack"));

        return stack != null;
    }

    @Override
    public ItemStack getStack()
    {
        return stack;
    }

    @Override
    public String getLabel()
    {
        return "Ore Class: " + oreClass;
    }
}
