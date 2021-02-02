package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.util.Filter;

import java.util.Comparator;
import java.util.List;

public class ListBoxDescription<E>
{
    public ListBoxDescription()
    {
    }

    public ListBoxDescription(int x, int y)
    {

    }

    public int columns = 1;
    public int rows = -1;
    public int elementWidth = -1;
    public int elementHeight = 13;
    public int sliderWidth = 20;
    public int listBoxItemMeta = 0;
    public boolean canSelect = true;
    public boolean multiSelect = false;
    public boolean sorted = false;
    public boolean hasSearchBar = false;
    public Comparator<E> comparator;
    public Filter<E> filter;
    public List<E> elements;
    public IListBoxItemProvider itemProvider = new ListBoxItemProvider();
}
