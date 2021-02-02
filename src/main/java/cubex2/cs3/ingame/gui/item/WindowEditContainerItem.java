package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditContainerItem extends WindowEditItemAttribute implements IWindowClosedListener<WindowSelectItem>
{
    private ItemDisplay itemDisplay;
    private CheckBox checkBox;

    public WindowEditContainerItem(WrappedItem item)
    {
        super(item, "containerItem", 150, 100);

        itemDisplay = itemDisplay().top(8).centerHor().add();
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setClearOnRightClick();
        itemDisplay.setItemStack(container.containerItem);

        checkBox = checkBox("Remain in crafting grid").below(itemDisplay, 5).left(7).add();
        checkBox.setIsChecked(container.leaveContainerItem);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == itemDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem(false));
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    protected void applyChanges()
    {
        container.containerItem = itemDisplay.getItemStack();
        container.leaveContainerItem = checkBox.getIsChecked();
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
