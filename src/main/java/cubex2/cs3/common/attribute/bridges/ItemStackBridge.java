package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackBridge extends AttributeBridge<ItemStack>
{
    @Override
    public ItemStack loadValueFromNBT(NBTTagCompound compound)
    {
        return ItemStackHelper.readFromNBTNamed(compound);
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, ItemStack value)
    {
        if (value != null)
        {
            ItemStackHelper.writeToNBTNamed(value, compound);
        }
    }
}
