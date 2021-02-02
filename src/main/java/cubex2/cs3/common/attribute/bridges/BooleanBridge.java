package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.nbt.NBTTagCompound;

public class BooleanBridge extends AttributeBridge<Boolean>
{
    @Override
    public Boolean loadValueFromNBT(NBTTagCompound compound)
    {
        return compound.getBoolean("Value");
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Boolean value)
    {
        compound.setBoolean("Value", value);
    }
}
