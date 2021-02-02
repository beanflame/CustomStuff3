package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.tileentity.WindowTileEntities;
import cubex2.cs3.lib.Strings;

public class TileEntityRegistry extends ContentRegistry<WrappedTileEntity>
{
    public TileEntityRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    public WrappedTileEntity getTileEntity(String name)
    {
        for (WrappedTileEntity tileEntity : contentList)
        {
            if (tileEntity.getName().equals(name))
                return tileEntity;
        }

        return null;
    }

    @Override
    public WrappedTileEntity newDataInstance()
    {
        return new WrappedTileEntity(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowTileEntities(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Tile Entities";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_TILE_ENTITY;
    }
}
