package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;

public class ContainerBuilder extends ControlBuilder<ControlContainer>
{
    public ContainerBuilder(ControlContainer c)
    {
        super(c);
    }

    @Override
    protected ControlContainer newInstance()
    {
        return new ControlContainer(width, height, anchor, offsetX, offsetY, container);
    }
}
