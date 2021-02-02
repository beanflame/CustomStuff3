package cubex2.cs3.gui.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.gui.WindowEditBackground;
import cubex2.cs3.ingame.gui.gui.WindowEditUserGui;
import net.minecraft.util.ResourceLocation;

public class GuiAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditInteger.class)
    public int width = 150;
    @Attribute(windowClass = WindowEditInteger.class)
    public int height = 150;

    @Attribute(windowClass = WindowEditUserGui.class)
    public GuiData guiData = new GuiData();

    @Attribute(windowClass = WindowEditBackground.class)
    public ResourceLocation background;
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public int bgU;
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public int bgV;

    public GuiAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
