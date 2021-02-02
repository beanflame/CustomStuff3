package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.DropBox;

public class DropBoxBuilder<T> extends ControlBuilder<DropBox<T>>
{
    private T[] values;

    public DropBoxBuilder(T[] values, ControlContainer c)
    {
        super(c);
        this.values = values;
        height = 15;
    }

    @Override
    protected DropBox<T> newInstance()
    {
        return new DropBox<T>(values, width, height, anchor, offsetX, offsetY, container);
    }
}
