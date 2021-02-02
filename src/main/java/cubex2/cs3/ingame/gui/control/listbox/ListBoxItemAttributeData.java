package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;

public class ListBoxItemAttributeData extends ListBoxItemLabel<AttributeData>
{
    public ListBoxItemAttributeData(AttributeData value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
    }
}
