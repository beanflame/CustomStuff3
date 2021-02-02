package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class ArrayBridge<T> extends AttributeBridge<T[]>
{
    private final int tagType;

    public ArrayBridge(int tagType)
    {
        this.tagType = tagType;
    }

    @Override
    public T[] loadValueFromNBT(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("Value", tagType);
        T[] value = createArray(list.tagCount());
        for (int i = 0; i < list.tagCount(); i++)
        {
            value[i] = readValueAt(i, list);
        }
        return value;
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, T[] value)
    {
        NBTTagList list = new NBTTagList();
        for (T t : value)
        {
            list.appendTag(createTag(t));
        }
        compound.setTag("Value", list);
    }

    protected abstract NBTBase createTag(T value);

    protected abstract T[] createArray(int size);

    protected abstract T readValueAt(int index, NBTTagList list);
}
