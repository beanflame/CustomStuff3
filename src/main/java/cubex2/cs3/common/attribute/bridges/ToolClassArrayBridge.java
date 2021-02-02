package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.util.ToolClass;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class ToolClassArrayBridge extends ArrayBridge<ToolClass>
{
    public ToolClassArrayBridge()
    {
        super(Constants.NBT.TAG_COMPOUND);
    }

    @Override
    protected NBTBase createTag(ToolClass value)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        value.writeToNBT(nbt);
        return nbt;
    }

    @Override
    protected ToolClass[] createArray(int size)
    {
        return new ToolClass[size];
    }

    @Override
    protected ToolClass readValueAt(int index, NBTTagList list)
    {
        ToolClass toolClass = new ToolClass();
        toolClass.readFromNBT(list.getCompoundTagAt(index));
        return toolClass;
    }
}
