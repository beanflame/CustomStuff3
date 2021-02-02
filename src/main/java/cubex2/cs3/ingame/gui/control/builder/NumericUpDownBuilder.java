package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.NumericUpDown;

public class NumericUpDownBuilder extends ControlBuilder<NumericUpDown>
{
    public NumericUpDownBuilder(ControlContainer c)
    {
        super(c);
        height = 20;
    }

    @Override
    protected NumericUpDown newInstance()
    {
        return new NumericUpDown(width, anchor, offsetX, offsetY, container);
    }
}
