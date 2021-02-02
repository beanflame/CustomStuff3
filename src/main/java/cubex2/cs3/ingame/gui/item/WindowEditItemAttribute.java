package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.common.WindowEditContainerAttribute;
import cubex2.cs3.item.attributes.ItemAttributes;

public abstract class WindowEditItemAttribute extends WindowEditContainerAttribute<ItemAttributes>
{
    protected final WrappedItem wrappedItem;

    public WindowEditItemAttribute(WrappedItem item, String title, int usedControls, int width, int height)
    {
        super(item.container, title, usedControls, width, height);
        wrappedItem = item;
    }

    public WindowEditItemAttribute(WrappedItem item, String title, int width, int height)
    {
        super(item.container, title, EDIT | CANCEL, width, height);
        wrappedItem = item;
    }
}
