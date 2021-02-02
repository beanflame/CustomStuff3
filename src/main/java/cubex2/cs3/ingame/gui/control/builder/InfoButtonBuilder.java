package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.InfoButton;
import cubex2.cs3.ingame.gui.control.Label;

public class InfoButtonBuilder extends ControlBuilder<InfoButton>
{
    private String text;

    public InfoButtonBuilder(String text, ControlContainer c)
    {
        super(c);
        this.text = text;
    }

    @Override
    public ControlBuilder<InfoButton> rightTo(Control c, int gap)
    {
        ControlBuilder<InfoButton> builder = super.rightTo(c, gap);
        if (c instanceof Label)
        {
            offsetY = -1;
        }
        return builder;
    }

    @Override
    protected InfoButton newInstance()
    {
        return new InfoButton(text, anchor, offsetX, offsetY, container);
    }
}
