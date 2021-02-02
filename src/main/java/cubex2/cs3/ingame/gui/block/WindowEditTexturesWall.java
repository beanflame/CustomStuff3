package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesWall extends WindowEditTexturesBase
{
    public WindowEditTexturesWall(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);

        worldDisplay.setCam(-0.75f, 1.5f, 2.0f);
        worldDisplay.setLook(0.5f, 0.5f, 0.5f);

        world.setBlock(wrappedBlock.block, -1, 0, 0);
        world.setBlock(wrappedBlock.block, 0, 0, 1);
    }
}
