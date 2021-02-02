package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ButtonDoc;
import cubex2.cs3.ingame.gui.control.ControlContainer;

public class ButtonDocBuilder extends ControlBuilder<ButtonDoc>
{
    private final String path;
    private String text;

    public ButtonDocBuilder(String text, String path, ControlContainer c)
    {
        super(c);
        this.text = text;
        this.path = path;
        width = 60;
        height = 16;
    }

    @Override
    protected ButtonDoc newInstance()
    {
        return new ButtonDoc(text, path, width, height, anchor, offsetX, offsetY, container);
    }
}
