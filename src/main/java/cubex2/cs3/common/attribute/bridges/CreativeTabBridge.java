package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.nbt.NBTTagCompound;

public class CreativeTabBridge extends AttributeBridge<CreativeTabs>
{
    @Override
    public CreativeTabs loadValueFromNBT(NBTTagCompound compound)
    {
        return cubex2.cs3.lib.CreativeTabs.getCreativeTab(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, CreativeTabs value)
    {
        compound.setString("Value", cubex2.cs3.lib.CreativeTabs.getTabName(value));
    }
}
