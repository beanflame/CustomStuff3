package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.block.WindowEditDoubleSlabBlock;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesStep;
import net.minecraft.block.Block;

public class StepAttributes extends BlockAttributes
{
    // TODO create window

    //@Attribute(windowClass = WindowEditDoubleSlabBlock.class, hasOwnWindow = false)
    public Block doubleSlabBlock;

    //@Attribute(windowClass = Window.class, hasOwnWindow = false)
    public int doubleSlabMeta;

    public StepAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        textureWindow = WindowEditTexturesStep.class;
    }
}
