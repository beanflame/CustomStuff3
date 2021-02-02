package cubex2.cs3.common.attribute;

import net.minecraft.nbt.NBTTagCompound;

public class NullAttributeBridge extends AttributeBridge
{
    @Override
    public Object loadValueFromNBT(NBTTagCompound compound)
    {
        return null;
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Object value)
    {

    }
}
