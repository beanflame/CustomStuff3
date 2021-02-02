package cubex2.cs3.tileentity.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.tileentity.WindowEditFurnaceModules;
import cubex2.cs3.tileentity.data.FurnaceModules;

public class TileEntityInventoryAttributes extends TileEntityAttributes
{
    @Attribute(windowClass = WindowEditInteger.class)
    public int slotCount = 0;

    @Attribute(windowClass = WindowEditFurnaceModules.class)
    public FurnaceModules furnaceModules = new FurnaceModules();

    public TileEntityInventoryAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
