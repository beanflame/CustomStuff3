package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ButtonUpDown;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.ItemDisplay;

public class ButtonUpDownBuilder extends ControlBuilder<ButtonUpDown>
{
    private boolean up;

    public ButtonUpDownBuilder(boolean up, ControlContainer c)
    {
        super(c);
        this.up = up;
    }

    @Override
    public ControlBuilder<ButtonUpDown> rightTo(Control c)
    {
        ControlBuilder<ButtonUpDown> cb = super.rightTo(c);
        if (c instanceof ItemDisplay)
        {
            offsetY = -1;
            offsetX = -2;
            if (!up)
                offsetY += 9;
        }
        return cb;
    }

    @Override
    protected ButtonUpDown newInstance()
    {
        return new ButtonUpDown(up, anchor, offsetX, offsetY, container);
    }
}
