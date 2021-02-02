package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesFence extends WindowEditTexturesBase
{
    public WindowEditTexturesFence(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);

        world.setBlock(null, 0, 0, 0);
        world.setBlock(wrappedBlock.block, 0, 0, -1);
        world.setBlock(wrappedBlock.block, 1, 0, -1);
        world.setBlock(wrappedBlock.block, 1, 0, 0);
    }
}
