package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesCrossTexture;

public class CrossTextureAttributes extends BlockAttributes
{
    public CrossTextureAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        textureWindow = WindowEditTexturesCrossTexture.class;
    }
}
