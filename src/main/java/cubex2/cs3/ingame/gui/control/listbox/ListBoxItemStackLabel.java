package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.StackLabelItem;

public class ListBoxItemStackLabel extends ListBoxItem<StackLabelItem>
{
    private Label lblLabel;

    public ListBoxItemStackLabel(StackLabelItem value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
        ItemDisplay display = itemDisplay(value.getStack()).left(3).centerVert().add();

        lblLabel = label(value.getLabel()).left(display, 2).centerVert(1).add();
    }

    @Override
    public void selectionChanged()
    {
        lblLabel.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
