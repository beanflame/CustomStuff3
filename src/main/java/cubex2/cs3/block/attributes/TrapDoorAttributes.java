package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.block.WindowEditTextureTrapDoor;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import net.minecraft.creativetab.CreativeTabs;

public class TrapDoorAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Only redstone can open door")
    public boolean redstoneOnly = false;

    public TrapDoorAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabRedstone;
        textureWindow = WindowEditTextureTrapDoor.class;
    }
}
