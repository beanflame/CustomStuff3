package cubex2.cs3.worldgen.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class WorldGenFlowerAttributes extends WorldGenAttributes
{
    @Attribute(windowClass = WindowEditInteger.class)
    public int blockRate = 64;

    public WorldGenFlowerAttributes(BaseContentPack pack)
    {
        super(pack);
        overworldReplacedBlocks = new ItemStack[]{
                new ItemStack(Blocks.grass),
                new ItemStack(Blocks.dirt),
                new ItemStack(Blocks.farmland)};
    }
}
