package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.IconTextBox;

public class IconTextBoxBuilder extends ControlBuilder<IconTextBox>
{
    private final BaseContentPack pack;
    private final String subFolder;

    public IconTextBoxBuilder(BaseContentPack pack, String subFolder, ControlContainer c)
    {
        super(c);
        this.pack = pack;
        this.subFolder = subFolder;
    }

    @Override
    protected IconTextBox newInstance()
    {
        return new IconTextBox(pack, subFolder, width, anchor, offsetX, offsetY, container);
    }
}
