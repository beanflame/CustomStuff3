package cubex2.cs3.ingame.gui.mcdata;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemProvider;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

import java.util.List;

public abstract class DataProvider<T> implements IListBoxItemProvider
{
    public final String title;
    public int windowWidth;
    public int windowHeight;

    protected DataProvider(String title)
    {
        this.title = title;
    }

    public List<T> getElements()
    {
        return Lists.newArrayList();
    }

    public void modifyListBoxDesc(ListBoxDescription<T> desc)
    {

    }
}
