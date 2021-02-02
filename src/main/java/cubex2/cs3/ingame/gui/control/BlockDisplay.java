package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.block.Block;

public class BlockDisplay extends WorldDisplay
{
    public BlockDisplay(Block block, int meta, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(createWorld(block, meta), width, height, anchor, offsetX, offsetY, parent);
        canMoveAround = true;
        rotate = false;
        lookX = 0.5f;
        lookY = 0.5f;
        lookZ = 0.5f;
        camX = -0.5f;
        camY = 1.6f;
        camZ = 1.5f;
    }

    private static SimulatedWorld createWorld(Block block, int meta)
    {
        SimulatedWorld world = new SimulatedWorld(0, 0, 0, 0, 0, 0);
        world.setBlock(block, 0, 0, 0);
        world.setMetadata(meta, 0, 0, 0);
        return world;
    }

    public void setBlock(Block block)
    {
        world.setBlock(block, 0, 0, 0);
    }

    public void setMetadata(int meta)
    {
        world.setMetadata(meta, 0, 0, 0);
    }


}
