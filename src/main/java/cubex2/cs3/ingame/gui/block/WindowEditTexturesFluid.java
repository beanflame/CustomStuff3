package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesFluid extends WindowEditTexturesBase
{
    private static final String[] TEXTURE_NAMES = new String[]{"still", "flowing"};

    public WindowEditTexturesFluid(WrappedBlock block)
    {
        super(block, TEXTURE_NAMES, false, false, false, true);

        world.setBlock(wrappedBlock.block, 0, -1, 0);
        world.setBlock(wrappedBlock.block, 1, -1, 0);
        world.setBlock(wrappedBlock.block, 0, -1, -1);
        world.setBlock(null, 0, 0, 0);
        world.setMetadata(1, 0, -1, -1);

        worldDisplay.setCam(0.5f, 1.5f, 1.5f);
        worldDisplay.setLook(0.5f, 0.5f, 0.5f);
    }
}
