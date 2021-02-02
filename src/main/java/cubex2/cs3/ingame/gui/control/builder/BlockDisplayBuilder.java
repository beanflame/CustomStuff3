package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.BlockDisplay;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import net.minecraft.block.Block;

public class BlockDisplayBuilder extends ControlBuilder<BlockDisplay>
{
    private final Block block;
    private final int meta;

    public BlockDisplayBuilder(Block block, int meta, ControlContainer c)
    {
        super(c);
        this.block = block;
        this.meta = meta;
    }

    @Override
    protected BlockDisplay newInstance()
    {
        return new BlockDisplay(block, meta, width, height, anchor, offsetX, offsetY, container);
    }
}
