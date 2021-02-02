package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.nbt.NBTTagCompound;

public class IntegerBridge extends AttributeBridge<Integer>
{
    @Override
    public Integer loadValueFromNBT(NBTTagCompound compound)
    {
        return compound.getInteger("Value");
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Integer value)
    {
        compound.setInteger("Value", value);
    }
}
