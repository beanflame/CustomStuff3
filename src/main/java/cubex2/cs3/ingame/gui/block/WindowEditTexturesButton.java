package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import net.minecraft.init.Blocks;

public class WindowEditTexturesButton extends WindowEditTexturesBase
{
    public WindowEditTexturesButton(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);
        worldDisplay.lookX = 0.5f;
        worldDisplay.setCam(0.25f, 0.75f, 1.0f);

        world.setBlock(Blocks.bedrock, 0, 0, -1);
        world.setMetadata(3, 0, 0, 0);
    }
}
