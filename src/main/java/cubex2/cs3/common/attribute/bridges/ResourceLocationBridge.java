package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationBridge extends AttributeBridge<ResourceLocation>
{
    @Override
    public ResourceLocation loadValueFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("Value"))
        {
            return new ResourceLocation(compound.getString("Value"));
        }
        return null;
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, ResourceLocation value)
    {
        if (value != null)
        {
            compound.setString("Value", value.toString());
        }
    }
}
