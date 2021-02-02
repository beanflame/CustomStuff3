package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.lib.Materials;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;

public class MaterialBridge extends AttributeBridge<Material>
{
    @Override
    public Material loadValueFromNBT(NBTTagCompound compound)
    {
        return Materials.getMaterial(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Material value)
    {
        compound.setString("Value", Materials.getMaterialName(value));
    }
}
