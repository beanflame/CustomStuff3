package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesFlat extends WindowEditTexturesBase
{
    public WindowEditTexturesFlat(WrappedBlock block)
    {
        super(block, new String[]{"bottom"});

        world.setMetadata(1, 0, 0, 0);
    }
}
