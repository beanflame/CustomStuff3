package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesFlat;

public class FlatRendererAttributes extends FacingAttributes {

    public FlatRendererAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        faceBySide = true;
        textureWindow = WindowEditTexturesFlat.class;
    }


}
