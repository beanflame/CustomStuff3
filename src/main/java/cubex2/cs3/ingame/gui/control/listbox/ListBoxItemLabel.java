package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.StringProviderPurpose;

public class ListBoxItemLabel<T> extends ListBoxItem<T>
{
    private Label label;

    public ListBoxItemLabel(T value, int idx,int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        String text = value instanceof IPurposeStringProvider ? ((IPurposeStringProvider) value).getStringForPurpose(StringProviderPurpose.LIST_BOX_ITEM_LABEl)
                : value.toString();
        label = label(text).left(3).centerVert(1).add();
    }

    @Override
    public void selectionChanged()
    {
        label.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
