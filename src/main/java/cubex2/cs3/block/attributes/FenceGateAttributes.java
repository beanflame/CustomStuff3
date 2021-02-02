package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesFenceGate;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import net.minecraft.creativetab.CreativeTabs;

public class FenceGateAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Only redstone can open gate")
    public boolean redstoneOnly = false;

    public FenceGateAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabRedstone;
        textureWindow = WindowEditTexturesFenceGate.class;
    }
}
