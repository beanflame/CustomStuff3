package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesPane extends WindowEditTexturesBase
{
    public WindowEditTexturesPane(WrappedBlock block)
    {
        super(block, new String[]{"bottom"});

        worldDisplay.camZ = 0.75f;

        world.setBlock(wrappedBlock.block, 1, 0, 0);
        world.setBlock(wrappedBlock.block, -1, 0, 0);
        world.setBlock(wrappedBlock.block, 0, 0, 1);
        world.setBlock(wrappedBlock.block, 0, 0, -1);
    }
}
