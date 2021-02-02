package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.util.ToolClass;

public class AxeAttributes extends ToolAttributes
{
    public AxeAttributes(BaseContentPack pack)
    {
        super(pack);
        toolClasses = new ToolClass[]{new ToolClass("axe", 0)};
    }
}
