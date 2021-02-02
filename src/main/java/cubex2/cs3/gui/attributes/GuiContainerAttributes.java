package cubex2.cs3.gui.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.gui.data.ShiftClickRules;
import cubex2.cs3.ingame.gui.gui.WindowEditShiftClickRules;

public class GuiContainerAttributes extends GuiAttributes
{
    @Attribute(windowClass = WindowEditShiftClickRules.class)
    public ShiftClickRules shiftClickRules = new ShiftClickRules();

    public GuiContainerAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
