package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.util.BlockDrop;
import net.minecraft.nbt.NBTTagCompound;

public class BlockDropBridge extends AttributeBridge<BlockDrop>
{
    @Override
    public BlockDrop loadValueFromNBT(NBTTagCompound compound)
    {
        BlockDrop drop = new BlockDrop();
        drop.readFromNBT(compound);
        return drop;
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, BlockDrop value)
    {
        value.writeToNBT(compound);
    }
}
