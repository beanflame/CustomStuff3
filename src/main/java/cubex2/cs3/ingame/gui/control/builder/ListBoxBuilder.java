package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class ListBoxBuilder<T> extends ControlBuilder<ListBox<T>>
{
    private ListBoxDescription<T> desc;

    public ListBoxBuilder(ListBoxDescription<T> desc, ControlContainer c)
    {
        super(c);
        this.desc = desc;
    }

    @Override
    protected ListBox<T> newInstance()
    {
        if (height <= 0 && desc.rows != -1)
        {
            height = desc.rows * (desc.elementHeight + ListBox.VERTICAL_GAP) - ListBox.VERTICAL_GAP;
            if (desc.hasSearchBar)
                height += 17;
        }
        return new ListBox<T>(desc, width, height, anchor, offsetX, offsetY, container);
    }
}
