package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditHasEffect extends WindowEditItemAttribute
{
    private CheckBox checkBox;
    private ItemDisplay itemDisplay;

    private boolean oldHasEffect;
    private boolean newHasEffect;

    public WindowEditHasEffect(WrappedItem item)
    {
        super(item, "hasEffect", 150, 100);
        oldHasEffect = newHasEffect = container.hasEffect;

        checkBox = checkBox("Has effect", newHasEffect).at(7, 7).add();

        itemDisplay = itemDisplay(new ItemStack(wrappedItem.item)).below(checkBox).add().setDrawSlotBackground();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == checkBox)
        {
            newHasEffect = checkBox.getIsChecked();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    protected void applyChanges()
    {
        container.hasEffect = newHasEffect;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        container.hasEffect = newHasEffect;
        super.draw(mouseX, mouseY, renderTick);
        container.hasEffect = oldHasEffect;
    }
}
