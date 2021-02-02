package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagCompound;

public class ArmorMaterialBridge extends AttributeBridge<ItemArmor.ArmorMaterial>
{
    @Override
    public ItemArmor.ArmorMaterial loadValueFromNBT(NBTTagCompound compound)
    {
        return ItemArmor.ArmorMaterial.valueOf(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, ItemArmor.ArmorMaterial value)
    {
        compound.setString("Value", value.name());
    }
}
