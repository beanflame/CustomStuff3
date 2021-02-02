package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesCTPost;

public class CrossTexturePostAttributes extends PostAttributes
{
    public CrossTexturePostAttributes(BaseContentPack pack)
    {
        super(pack);
        textureWindow = WindowEditTexturesCTPost.class;
    }
}
