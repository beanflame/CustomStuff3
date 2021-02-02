package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesPressurePlate extends WindowEditTexturesBase
{
    public WindowEditTexturesPressurePlate(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);

        worldDisplay.setCam(1.25f, 1.0f, 1.25f);
        worldDisplay.setLook(0.5f, 0.0f, 0.5f);
    }
}
