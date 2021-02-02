package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.ControlContainer;

public class ButtonBuilder extends ControlBuilder<Button>
{
    private String text;

    public ButtonBuilder(String text, ControlContainer c)
    {
        super(c);
        this.text = text;
        width = 60;
        height = 16;
    }

    @Override
    protected Button newInstance()
    {
        return new Button(text, width, height, anchor, offsetX, offsetY, container);
    }
}
