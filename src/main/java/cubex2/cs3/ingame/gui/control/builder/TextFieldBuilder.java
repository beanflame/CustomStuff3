package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.TextField;

public class TextFieldBuilder extends ControlBuilder<TextField>
{
    public TextFieldBuilder(ControlContainer c)
    {
        super(c);
    }

    @Override
    protected TextField newInstance()
    {
        return new TextField(width, height, anchor, offsetX, offsetY, container);
    }
}
