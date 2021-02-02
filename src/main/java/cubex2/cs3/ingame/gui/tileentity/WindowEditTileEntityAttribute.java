package cubex2.cs3.ingame.gui.tileentity;

import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.common.WindowEditContainerAttribute;
import cubex2.cs3.tileentity.attributes.TileEntityAttributes;

public class WindowEditTileEntityAttribute extends WindowEditContainerAttribute<TileEntityAttributes>
{
    private WrappedTileEntity wrappedTileEntity;

    public WindowEditTileEntityAttribute(WrappedTileEntity tileEntity, String title, int usedControls, int width, int height)
    {
        super(tileEntity.container, title, usedControls, width, height);
        this.wrappedTileEntity = tileEntity;
    }

    public WindowEditTileEntityAttribute(WrappedTileEntity tileEntity, String title, int width, int height)
    {
        super(tileEntity.container, title, width, height);
        this.wrappedTileEntity = tileEntity;
    }
}
