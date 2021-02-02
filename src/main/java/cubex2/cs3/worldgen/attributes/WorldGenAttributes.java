package cubex2.cs3.worldgen.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.common.WindowEditFloat;
import cubex2.cs3.ingame.gui.worldgen.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class WorldGenAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditFloat.class)
    public float generationsPerChunk = 1.0f;

    @Attribute(windowClass = WindowEditHeight.class, customName = "height")
    public int minHeight = 0;
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public int maxHeight = 255;

    @Attribute(windowClass = WindowEditGenDimension.class, customName = "dimensions")
    public boolean generateInOverworld = true;
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean generateInNether = false;
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean generateInEnd = false;

    @Attribute(windowClass = WindowEditReplacedBlocks.class, customName = "replacedBlocks")
    public ItemStack[] overworldReplacedBlocks = new ItemStack[]{new ItemStack(Blocks.stone)};
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public ItemStack[] netherReplacedBlocks = new ItemStack[]{new ItemStack(Blocks.netherrack)};
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public ItemStack[] endReplacedBlocks = new ItemStack[]{new ItemStack(Blocks.end_stone)};

    @Attribute(windowClass = WindowEditGeneratedBlock.class)
    public ItemStack generatedBlock;

    @Attribute(windowClass = WindowEditAllowedBiomes.class)
    public String[] allowedBiomes = new String[]{"all"};

    public WorldGenAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
