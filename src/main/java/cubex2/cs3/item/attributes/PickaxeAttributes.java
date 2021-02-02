package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.util.ToolClass;

public class PickaxeAttributes extends ToolAttributes
{
    public PickaxeAttributes(BaseContentPack pack)
    {
        super(pack);
        toolClasses = new ToolClass[]{new ToolClass("pickaxe", 0)};
    }
}
