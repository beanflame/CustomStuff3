package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;

public class BlockCSWheat extends BlockCS
{
    public BlockCSWheat(WrappedBlock block)
    {
        super(block);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.wheatRenderId;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
