package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesCarpet;
import net.minecraft.creativetab.CreativeTabs;

public class CarpetAttributes extends BlockAttributes
{
    public CarpetAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabDecorations;
        textureWindow = WindowEditTexturesCarpet.class;
    }
}
