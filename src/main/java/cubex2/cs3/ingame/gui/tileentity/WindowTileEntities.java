package cubex2.cs3.ingame.gui.tileentity;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowContentList;
import cubex2.cs3.ingame.gui.common.WindowEditAttributeContent;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowTileEntities extends WindowContentList<WrappedTileEntity>
{
    public WindowTileEntities(BaseContentPack pack)
    {
        super(WrappedTileEntity.class, "Tile Entities", 263, 160, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<WrappedTileEntity> desc)
    {
        desc.rows = 9;
        desc.sorted = true;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateTileEntity(pack);
    }

    @Override
    protected Window createEditContentWindow(WrappedTileEntity content)
    {
        return new WindowEditAttributeContent(content);
    }
}
