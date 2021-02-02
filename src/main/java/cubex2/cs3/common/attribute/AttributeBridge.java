package cubex2.cs3.common.attribute;

import net.minecraft.nbt.NBTTagCompound;

public abstract class AttributeBridge<T>
{
    public String additionalInfo;

    public abstract T loadValueFromNBT(NBTTagCompound compound);

    public abstract void writeValueToNBT(NBTTagCompound compound, T value);
}
