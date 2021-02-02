package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesFence;
import net.minecraft.creativetab.CreativeTabs;

public class FenceAttributes extends BlockAttributes
{
    public FenceAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabDecorations;
        textureWindow = WindowEditTexturesFence.class;
    }
}
