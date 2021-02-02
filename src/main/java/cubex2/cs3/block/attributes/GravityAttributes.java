package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;

public class GravityAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Falls into the sky")
    public boolean hasAntiGravity;

    public GravityAttributes(BaseContentPack pack)
    {
        super(pack);
        tickrate = 2;
    }
}
