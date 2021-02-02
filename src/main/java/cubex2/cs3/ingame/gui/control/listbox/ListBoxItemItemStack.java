package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class ListBoxItemItemStack extends ListBoxItem<ItemStack>
{
    public ListBoxItemItemStack(ItemStack value, int idx,int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        itemDisplay(value).left(3).centerVert().add();
    }
}
