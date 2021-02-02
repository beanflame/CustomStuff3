package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;

public class BlockCSNormal extends BlockCS
{
    public BlockCSNormal(WrappedBlock block)
    {
        super(block);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }
}
