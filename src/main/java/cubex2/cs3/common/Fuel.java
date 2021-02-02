package cubex2.cs3.common;

import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.StackLabelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class Fuel extends BaseContent implements StackLabelItem
{
    public String fuelList = "vanilla";
    public ItemStack stack;
    public int duration;

    public Fuel(String fuelList, ItemStack stack, int duration, BaseContentPack pack)
    {
        super(pack);
        this.fuelList = fuelList;
        this.stack = stack;
        this.duration = duration;
    }

    public Fuel(BaseContentPack pack)
    {
        super(pack);
    }

    public boolean isRepresentingStack(ItemStack stack, String list)
    {
        return fuelList.equals(list) &&
                this.stack.getItem() == stack.getItem() &&
                (this.stack.getItemDamage() == stack.getItemDamage() || this.stack.getItemDamage() == OreDictionary.WILDCARD_VALUE);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("Stack", ItemStackHelper.writeToNBTNamed(stack));
        compound.setInteger("Duration", duration);
        compound.setString("FuelList", fuelList);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        stack = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Stack"));
        duration = compound.getInteger("Duration");
        if (compound.hasKey("FuelList"))
            fuelList = compound.getString("FuelList");

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
        return "Duration: " + duration + ", List: " + fuelList;
    }
}
