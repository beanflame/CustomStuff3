package cubex2.cs3.util;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTData
{
    void writeToNBT(NBTTagCompound compound);

    void readFromNBT(NBTTagCompound compound);
}
