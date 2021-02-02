package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.CreativeTab;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowCreativeTabs extends WindowContentList<CreativeTab>
{
    public WindowCreativeTabs(BaseContentPack pack)
    {
        super(CreativeTab.class, "Creative Tabs", 263, 171, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<CreativeTab> desc)
    {
        desc.elementHeight = 22;
        desc.rows = 6;
        desc.columns = 2;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateCreativeTab(pack);
    }

    @Override
    protected Window createEditContentWindow(CreativeTab content)
    {
        return new WindowEditOrCreateCreativeTab(content, pack);
    }
}
