package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesPost;
import cubex2.cs3.ingame.gui.common.WindowEditFloat;

public class PostAttributes extends FacingAttributes
{
    @Attribute(windowClass = WindowEditFloat.class)
    public float thickness = 0.5f;

    public PostAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        faceBySide = true;
        canFaceTop = true;
        canFaceBottom = true;
        textureWindow = WindowEditTexturesPost.class;
    }
}
