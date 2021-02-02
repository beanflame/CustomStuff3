package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesWall;

public class WallAttributes extends BlockAttributes
{
    public WallAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        textureWindow = WindowEditTexturesWall.class;
    }
}
