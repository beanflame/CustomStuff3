package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesLadder;
import net.minecraft.creativetab.CreativeTabs;

public class LadderAttributes extends BlockAttributes
{
    public LadderAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabDecorations;
        textureWindow = WindowEditTexturesLadder.class;
    }
}
