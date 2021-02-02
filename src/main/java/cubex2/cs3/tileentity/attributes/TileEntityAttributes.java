package cubex2.cs3.tileentity.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.common.WindowEditScript;
import cubex2.cs3.util.ScriptWrapper;

public class TileEntityAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUpdate = null;

    public TileEntityAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
