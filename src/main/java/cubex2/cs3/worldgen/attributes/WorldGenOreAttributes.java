package cubex2.cs3.worldgen.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.worldgen.WindowEditAmount;
import net.minecraft.block.Block;

public class WorldGenOreAttributes extends WorldGenAttributes
{
    @Attribute(windowClass = WindowEditAmount.class)
    public int amount = 1;

    public WorldGenOreAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
