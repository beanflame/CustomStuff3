package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.WorldDisplay;
import cubex2.cs3.util.SimulatedWorld;

public class WorldDisplayBuilder extends ControlBuilder<WorldDisplay>
{
    private SimulatedWorld world;

    public WorldDisplayBuilder(SimulatedWorld world, ControlContainer c)
    {
        super(c);
        this.world = world;
    }

    @Override
    protected WorldDisplay newInstance()
    {
        return new WorldDisplay(world, width, height, anchor, offsetX, offsetY, container);
    }
}
