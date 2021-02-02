package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.PlayerDisplay;

public class PlayerDisplayBuilder extends ControlBuilder<PlayerDisplay>
{
    public PlayerDisplayBuilder(ControlContainer c)
    {
        super(c);
    }

    @Override
    protected PlayerDisplay newInstance()
    {
        return new PlayerDisplay(width, height, anchor, offsetX, offsetY, container);
    }
}
