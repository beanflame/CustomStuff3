package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesButton;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import net.minecraft.creativetab.CreativeTabs;

public class ButtonAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Activated by arrows")
    public boolean isSensible = false;

    public ButtonAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabRedstone;
        textureWindow = WindowEditTexturesButton.class;
    }
}
