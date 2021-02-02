package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import net.minecraft.item.ItemStack;

public class ListBoxItemWrappedItem extends ListBoxItem<WrappedItem>
{

    public ListBoxItemWrappedItem(WrappedItem value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        ItemDisplay display = itemDisplay().left(3).centerVert().add();
        if (value.item != null)
        {
            display.setItemStack(new ItemStack(value.item));
        }

        label(value.getName() + " (" + value.getType().name + ")").left(display, 3).centerVert(1).add();
    }
}
