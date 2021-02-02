package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTextureWheat;
import cubex2.cs3.util.IconWrapper;

public class WheatAttributes extends BlockAttributes
{
    public WheatAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        textureWindow = WindowEditTextureWheat.class;
    }

    @Override
    public IconWrapper getTexture(int side)
    {
        return super.getTexture(0);
    }

    @Override
    public IconWrapper getTexture(String name)
    {
        return super.getTexture("bottom");
    }
}
