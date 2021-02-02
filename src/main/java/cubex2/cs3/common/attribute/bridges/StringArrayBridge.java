package cubex2.cs3.common.attribute.bridges;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

public class StringArrayBridge extends ArrayBridge<String>
{
    public StringArrayBridge()
    {
        super(Constants.NBT.TAG_STRING);
    }

    @Override
    protected NBTBase createTag(String value)
    {
        return new NBTTagString(value);
    }

    @Override
    protected String[] createArray(int size)
    {
        return new String[size];
    }

    @Override
    protected String readValueAt(int index, NBTTagList list)
    {
        return list.getStringTagAt(index);
    }
}
