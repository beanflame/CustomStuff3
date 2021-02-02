package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import net.minecraft.creativetab.CreativeTabs;

public class ToolAttributes extends ItemAttributes
{
    public ToolAttributes(BaseContentPack pack)
    {
        super(pack);
        full3d = true;
        creativeTab = CreativeTabs.tabTools;
        maxStack = 1;
        maxDamage = 100;
    }
}
