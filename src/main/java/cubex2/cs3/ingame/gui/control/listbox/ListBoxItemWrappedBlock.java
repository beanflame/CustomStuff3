package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.item.ItemStack;

public class ListBoxItemWrappedBlock extends ListBoxItem<WrappedBlock>
{
    public ListBoxItemWrappedBlock(WrappedBlock value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        ItemDisplay display = itemDisplay().left(3).centerVert().add();
        if (value.block != null)
        {
            display.setItemStack(new ItemStack(GeneralHelper.getItem(GeneralHelper.getBlockName(value.block))));
        }

        label(value.getName() + " (" + value.getType().name + ")").left(display, 3).centerVert(1).add();
    }
}
