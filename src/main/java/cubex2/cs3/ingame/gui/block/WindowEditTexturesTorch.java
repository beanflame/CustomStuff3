package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesTorch extends WindowEditTexturesBase
{
    public WindowEditTexturesTorch(WrappedBlock block)
    {
        super(block, new String[]{"bottom"});
        
        worldDisplay.setCam(1.125f, 1.25f, 1.125f);
        worldDisplay.setLook(0.5f, 0.5f, 0.5f);

        world.setMetadata(5, 0, 0, 0);
    }
}
