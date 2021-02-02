package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;

public class ListBoxItemWrappedWorldGen extends ListBoxItem<WrappedWorldGen>
{
    public ListBoxItemWrappedWorldGen(WrappedWorldGen value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        ItemDisplay display = itemDisplay().left(3).centerVert().add();
        if (value.container.generatedBlock != null)
        {
            display.setItemStack(value.container.generatedBlock);
        }

        label(value.getName() + " (" + value.getType().name + ")").left(display, 3).centerVert(1).add();
    }
}
