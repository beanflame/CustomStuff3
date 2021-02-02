package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class BlockBridge extends AttributeBridge<Block>
{
    @Override
    public Block loadValueFromNBT(NBTTagCompound compound)
    {
        return GeneralHelper.getBlock(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Block value)
    {
        if (value != null)
            compound.setString("Value", GeneralHelper.getBlockName(value));
    }
}
