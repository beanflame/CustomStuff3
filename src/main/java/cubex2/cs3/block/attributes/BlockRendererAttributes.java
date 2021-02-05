package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesFlat;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;


// 豆焰写的

//BlockAttributes

// FacingAttributes

public class BlockRendererAttributes extends BlockAttributes {

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Falls into the sky")
    public boolean hasAntiGravity;

    public BlockRendererAttributes(BaseContentPack pack)
    {
        super(pack);
        tickrate = 2;
        opacity = 0;
        //faceBySide = true;
        //textureWindow = WindowEditTexturesFlat.class;
        textureWindow = WindowEditTexturesFlat.class;
    }

    /*

    public BlockRendererAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        faceBySide = true;
        textureWindow = WindowEditTexturesFlat.class;
    }

     */


}
