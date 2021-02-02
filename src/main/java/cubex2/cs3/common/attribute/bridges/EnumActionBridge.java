package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.item.EnumAction;
import net.minecraft.nbt.NBTTagCompound;

public class EnumActionBridge extends AttributeBridge<EnumAction>
{
    @Override
    public EnumAction loadValueFromNBT(NBTTagCompound compound)
    {
        return EnumAction.valueOf(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, EnumAction value)
    {
        compound.setString("Value", value.name());
    }
}
