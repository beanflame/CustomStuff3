package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.InventorySlot;

public class InventorySlotBuilder extends ControlBuilder<InventorySlot>
{
    public InventorySlotBuilder(ControlContainer c)
    {
        super(c);
    }

    @Override
    protected InventorySlot newInstance()
    {
        return new InventorySlot(anchor, offsetX, offsetY, container);
    }
}
