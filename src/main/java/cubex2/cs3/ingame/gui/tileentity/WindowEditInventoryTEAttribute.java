package cubex2.cs3.ingame.gui.tileentity;

import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.common.WindowEditContainerAttribute;
import cubex2.cs3.tileentity.attributes.TileEntityInventoryAttributes;

public class WindowEditInventoryTEAttribute extends WindowEditContainerAttribute<TileEntityInventoryAttributes>
{
    protected WrappedTileEntity wrappedTileEntity;

    public WindowEditInventoryTEAttribute(WrappedTileEntity tileEntity, String title, int usedControls, int width, int height)
    {
        super((TileEntityInventoryAttributes) tileEntity.container, title, usedControls, width, height);
        this.wrappedTileEntity = tileEntity;
    }

    public WindowEditInventoryTEAttribute(WrappedTileEntity tileEntity, String title, int width, int height)
    {
        super((TileEntityInventoryAttributes) tileEntity.container, title, width, height);
        this.wrappedTileEntity = tileEntity;
    }
}
