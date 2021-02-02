package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.lib.Potions;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;

public class PotionBridge extends AttributeBridge<Potion>
{
    @Override
    public Potion loadValueFromNBT(NBTTagCompound compound)
    {
        return Potions.getPotion(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Potion value)
    {
        compound.setString("Value", Potions.getPotionName(value));
    }
}
