package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesFluid;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.util.IconWrapper;
import net.minecraft.block.material.Material;

public class FluidAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Is infinite source")
    public boolean infiniteSource = true;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Is gaseous")
    public boolean gaseous = false;

    @Attribute(windowClass = WindowEditInteger.class)
    public int density = 1000;

    @Attribute(windowClass = WindowEditInteger.class)
    public int viscosity = 1000;

    @Attribute(windowClass = WindowEditInteger.class)
    public int flowLength = 8;

    @Attribute(windowClass = WindowEditTexturesFluid.class, customName = "textures")
    public IconWrapper textureStill = new IconWrapper("");

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureFlowing = new IconWrapper("");

    public FluidAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 3;
        material = Material.water;
    }

    @Override
    public IconWrapper getTexture(String name)
    {
        if (name.equals("still"))
            return textureStill;
        if (name.equals("flowing"))
            return textureFlowing;
        return super.getTexture(name);
    }

    @Override
    protected boolean addAttribute(String attribute, String type)
    {
        return !attribute.equals("textureBottom") &&
                !attribute.equals("drop");
    }
}
