package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.TextBox;

public class TextBoxBuilder extends ControlBuilder<TextBox>
{
    public TextBoxBuilder(ControlContainer c)
    {
        super(c);
        height = 15;
    }

    @Override
    protected TextBox newInstance()
    {
        return new TextBox(width, height, anchor, offsetX, offsetY, container);
    }
}
