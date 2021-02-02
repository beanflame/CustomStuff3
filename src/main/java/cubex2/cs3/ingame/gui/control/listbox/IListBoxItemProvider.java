package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;

public interface IListBoxItemProvider
{
    <T> ListBoxItem<?> createListBoxItem(T value, int idx, int meta, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent);
}
