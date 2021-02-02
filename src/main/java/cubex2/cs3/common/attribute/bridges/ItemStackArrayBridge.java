package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class ItemStackArrayBridge extends ArrayBridge<ItemStack>
{
    public ItemStackArrayBridge()
    {
        super(Constants.NBT.TAG_COMPOUND);
    }

    @Override
    protected NBTBase createTag(ItemStack value)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        if (value != null)
        {
            ItemStackHelper.writeToNBTNamed(value, nbt);
        }
        return nbt;
    }

    @Override
    protected ItemStack[] createArray(int size)
    {
        return new ItemStack[size];
    }

    @Override
    protected ItemStack readValueAt(int index, NBTTagList list)
    {
        NBTTagCompound nbt = list.getCompoundTagAt(index);
        return ItemStackHelper.readFromNBTNamed(nbt);
    }
}
