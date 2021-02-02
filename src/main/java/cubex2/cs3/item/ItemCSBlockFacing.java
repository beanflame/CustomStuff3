package cubex2.cs3.item;

import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;

public class ItemCSBlockFacing extends ItemCSBlock
{
    public ItemCSBlockFacing(Block block, WrappedBlock wrappedBlock)
    {
        super(block, wrappedBlock);
    }

    @Override
    public int getMetadata(int i)
    {
        return i == 1 ? 8 : 0;
    }
}
