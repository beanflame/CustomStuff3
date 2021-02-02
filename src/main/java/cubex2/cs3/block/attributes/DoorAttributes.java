package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.block.WindowEditDoorIcon;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesDoor;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import cubex2.cs3.util.BlockDrop;
import cubex2.cs3.util.GeneralHelper;
import cubex2.cs3.util.IconWrapper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DoorAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Only redstone can open door")
    public boolean redstoneOnly = false;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Shade like normal blocks")
    public boolean normalBlockShading = false;

    @Attribute(windowClass = WindowEditDoorIcon.class, customName = "icon")
    public IconWrapper iconFile = new IconWrapper("");

    public DoorAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabRedstone;
        textureWindow = WindowEditTexturesDoor.class;
    }

    @Override
    public IconWrapper getTexture(String name)
    {
        if (name.equals("top front"))
            return textureNorth;
        if (name.equals("top sides"))
            return textureSouth;
        if (name.equals("bottom front"))
            return textureEast;
        if (name.equals("bottom sides"))
            return textureWest;
        if (name.equals("doorIcon"))
            return iconFile;
        return super.getTexture(name);
    }

    @Override
    public void postCreateBlock(Block block)
    {
        if (drop == null)
        {
            Item item = GeneralHelper.getItem(GeneralHelper.getBlockName(block));
            drop = new BlockDrop(item, 0);
        }
        if (pick == null)
        {
            Item item = GeneralHelper.getItem(GeneralHelper.getBlockName(block));
            pick = new ItemStack(item);
        }
    }
}
