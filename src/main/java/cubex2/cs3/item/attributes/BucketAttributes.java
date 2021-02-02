package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.item.WindowEditFluid;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class BucketAttributes extends ItemAttributes
{
    @Attribute(windowClass = WindowEditFluid.class, loadOnPostInit = true)
    public Block fluid;

    public BucketAttributes(BaseContentPack pack)
    {
        super(pack);
        creativeTab = CreativeTabs.tabMisc;
        maxStack = 1;
    }
}
