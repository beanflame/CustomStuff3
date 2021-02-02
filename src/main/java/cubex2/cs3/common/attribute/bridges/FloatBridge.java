package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.nbt.NBTTagCompound;

public class FloatBridge extends AttributeBridge<Float>
{
    @Override
    public Float loadValueFromNBT(NBTTagCompound compound)
    {
        return compound.getFloat("Value");
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Float value)
    {
        compound.setFloat("Value", value);
    }
}
