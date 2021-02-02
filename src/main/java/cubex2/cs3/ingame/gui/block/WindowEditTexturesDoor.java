package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesDoor extends WindowEditTexturesBase
{
    private static final String[] TEXTURES = new String[]{"top front", "bottom front", "top sides", "bottom sides", "top", "bottom"};

    public WindowEditTexturesDoor(WrappedBlock block)
    {
        super(block, TEXTURES, false, true, false);
    }
}
