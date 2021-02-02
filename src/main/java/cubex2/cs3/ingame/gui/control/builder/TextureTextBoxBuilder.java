package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.TextureTextBox;

public class TextureTextBoxBuilder extends ControlBuilder<TextureTextBox>
{
    private final BaseContentPack pack;
    private final String subFolder;

    public TextureTextBoxBuilder(BaseContentPack pack, String subFolder, ControlContainer c)
    {
        super(c);
        this.pack = pack;
        this.subFolder = subFolder;
    }

    @Override
    protected TextureTextBox newInstance()
    {
        return new TextureTextBox(pack, subFolder, width, anchor, offsetX, offsetY, container);
    }
}
