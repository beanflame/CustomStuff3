package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.PlayerInventoryArea;

public class PlayerInventoryAreaBuilder extends ControlBuilder<PlayerInventoryArea>
{
    public PlayerInventoryAreaBuilder(ControlContainer c)
    {
        super(c);
    }

    @Override
    protected PlayerInventoryArea newInstance()
    {
        return new PlayerInventoryArea(anchor, offsetX, offsetY, container);
    }
}
