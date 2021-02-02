package cubex2.cs3.util;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class NBTDataList<T extends NBTData> implements NBTData
{
    public List<T> list = Lists.newArrayList();

    private final Class<T> clazz;

    public NBTDataList(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        Util.writeListToNBT("DataList", list, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        Util.readListFromNBT("DataList", list, compound, clazz);
    }
}
