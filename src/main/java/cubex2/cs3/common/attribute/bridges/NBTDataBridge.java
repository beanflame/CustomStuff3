package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.util.NBTData;
import net.minecraft.nbt.NBTTagCompound;

public abstract class NBTDataBridge<T extends NBTData> extends AttributeBridge<T>
{
    private Class<T> clazz;

    public NBTDataBridge(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public T loadValueFromNBT(NBTTagCompound compound)
    {
        try
        {
            T data = clazz.newInstance();
            data.readFromNBT(compound);
            return data;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, T value)
    {
        value.writeToNBT(compound);
    }
}
