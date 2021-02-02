package cubex2.cs3.ingame.gui.mcdata;

import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowDataList<T> extends Window
{
    private final DataProvider<T> provider;

    private ListBox<T> listBox;

    public WindowDataList(DataProvider<T> provider)
    {
        super(provider.title, BACK, provider.windowWidth, provider.windowHeight);
        this.provider = provider;

        ListBoxDescription<T> desc = new ListBoxDescription<T>(7, 7);
        desc.elements = provider.getElements();
        desc.canSelect = false;
        desc.itemProvider = provider;
        provider.modifyListBoxDesc(desc);
        listBox = listBox(desc).fillWidth(7).top(7).add();
    }
}
