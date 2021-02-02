package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.ChestItem;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowChestItems extends WindowContentList<ChestItem>
{
    public WindowChestItems(BaseContentPack pack)
    {
        super(ChestItem.class, "Chest Items", 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<ChestItem> desc)
    {
        desc.rows = 7;
        desc.elementHeight = 22;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateChestItem(pack);
    }

    @Override
    protected Window createEditContentWindow(ChestItem content)
    {
        return new WindowEditOrCreateChestItem(content, pack);
    }
}
