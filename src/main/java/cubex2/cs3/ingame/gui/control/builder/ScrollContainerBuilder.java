package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.ScrollContainer;

public class ScrollContainerBuilder extends ControlBuilder<ScrollContainer>
{
    private final int totalHeight;

    public ScrollContainerBuilder(int totalHeight, ControlContainer c)
    {
        super(c);
        this.totalHeight = totalHeight;
    }

    @Override
    protected ScrollContainer newInstance()
    {
        return new ScrollContainer(totalHeight, width, height, anchor, offsetX, offsetY, container);
    }
}
