package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesTorch;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import net.minecraft.creativetab.CreativeTabs;

public class TorchAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Spawn particles")
    public boolean particles = true;

    public TorchAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabDecorations;
        canPlaceOnCeiling = false;
        textureWindow = WindowEditTexturesTorch.class;
    }
}
