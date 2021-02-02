package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditAnvilMaterial extends WindowEditItemAttribute implements IWindowClosedListener<WindowSelectItem>
{
    private ItemDisplay itemDisplay;

    public WindowEditAnvilMaterial(WrappedItem item)
    {
        super(item, "anvilMaterial", 150, 100);

        itemDisplay = itemDisplay().top(31).centerHor().add();
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setItemStack(container.anvilMaterial);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == itemDisplay)
        {
            if (button == 0)
            {
                GuiBase.openWindow(new WindowSelectItem(false));
            } else if (button == 1)
            {
                itemDisplay.setItemStack(null);
            }
        }
    }

    @Override
    protected void applyChanges()
    {
        container.anvilMaterial = itemDisplay.getItemStack();
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        ItemStack stack = window.getSelectedStack();
        if (stack != null)
        {
            itemDisplay.setItemStack(stack);
        }
    }
}
