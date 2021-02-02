package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTextures extends WindowEditTexturesBase
{
    private static final String[] textures = new String[]{"bottom", "top", "north", "south", "east", "west"};

    public WindowEditTextures(WrappedBlock block)
    {
        super(block, textures, true, true, true);

        world.setBlock(wrappedBlock.block, 0, 0, -1);
        world.setBlock(wrappedBlock.block, 1, 0, -1);
        world.setBlock(wrappedBlock.block, 1, 0, 0);
    }
}
