package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.ItemDisplay;

public class WindowEditPick extends WindowEditBlockAttribute
{
    private ItemDisplay itemDisplay;

    public WindowEditPick(WrappedBlock block)
    {
        super(block, "pick", 150, 100);

        itemDisplay = itemDisplay().top(31).centerHor().add();
        itemDisplay.setItemStack(container.pick.copy());
        itemDisplay.setDrawSlotBackground();
        itemDisplay.useSelectItemDialog(false);
    }

    @Override
    protected void applyChanges()
    {
        container.pick = itemDisplay.getItemStack();
    }
}
