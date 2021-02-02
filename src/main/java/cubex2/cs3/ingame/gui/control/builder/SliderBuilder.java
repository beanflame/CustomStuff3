package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.Slider;

public class SliderBuilder extends ControlBuilder<Slider>
{
    private final Slider.Direction direction;
    private final int maxValue;

    public SliderBuilder(Slider.Direction direction, int maxValue, ControlContainer c)
    {
        super(c);
        this.direction = direction;
        this.maxValue = maxValue;
    }

    @Override
    protected Slider newInstance()
    {
        return new Slider(direction, maxValue, width, height, anchor, offsetX, offsetY, container);
    }
}
