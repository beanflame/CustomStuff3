package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;
import net.minecraft.util.IIcon;

public class BlockCSCrossTexture extends BlockCS
{
    public BlockCSCrossTexture(WrappedBlock block)
    {
        super(block);
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return super.getIcon(0, meta);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return 1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
