package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesStairs;

public class StairAttributes extends BlockAttributes
{
    public StairAttributes(BaseContentPack pack)
    {
        super(pack);
        textureWindow = WindowEditTexturesStairs.class;
    }
}
