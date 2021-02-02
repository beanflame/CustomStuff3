package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import net.minecraft.init.Blocks;

public class WindowEditTexturesLadder extends WindowEditTexturesBase
{
    public WindowEditTexturesLadder(WrappedBlock block)
    {
        super(block, new String[]{"bottom"});
        worldDisplay.setCam(0.25f, 1.0f, 0.75f);
        worldDisplay.setLook(0.5f, 0.5f, 0.0f);

        world.setBlock(Blocks.bedrock, 0, 0, -1);
        world.setMetadata(3, 0, 0, 0);
    }
}
