package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.block.WindowBlocks;
import cubex2.cs3.lib.Strings;

public class BlockRegistry extends  ContentRegistry<WrappedBlock>
{
    public BlockRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public WrappedBlock newDataInstance()
    {
        return new WrappedBlock(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowBlocks(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Blocks";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_BLOCK;
    }
}
