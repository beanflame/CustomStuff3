package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.util.IconWrapper;
import net.minecraft.nbt.NBTTagCompound;

public class IconWrapperBridge extends AttributeBridge<IconWrapper>
{
    @Override
    public IconWrapper loadValueFromNBT(NBTTagCompound compound)
    {
        return new IconWrapper(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, IconWrapper value)
    {
        compound.setString("Value", value.iconString);
    }
}
