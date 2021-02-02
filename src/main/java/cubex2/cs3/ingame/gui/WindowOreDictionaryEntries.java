package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowOreDictionaryEntries extends WindowContentList<OreDictionaryEntry>
{
    public WindowOreDictionaryEntries(BaseContentPack pack)
    {
        super(OreDictionaryEntry.class, "Ore Dictionary Entries", NEW | DELETE | BACK, 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<OreDictionaryEntry> desc)
    {
        desc.rows = 7;
        desc.elementHeight = 22;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateOreDictEntry(pack);
    }

    @Override
    protected Window createEditContentWindow(OreDictionaryEntry content)
    {
        return null;
    }
}
