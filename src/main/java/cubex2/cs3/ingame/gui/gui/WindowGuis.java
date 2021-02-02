package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowContentList;
import cubex2.cs3.ingame.gui.common.WindowEditAttributeContent;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowGuis extends WindowContentList<WrappedGui>
{
    public WindowGuis(BaseContentPack pack)
    {
        super(WrappedGui.class, "GUIs", 263, 160, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<WrappedGui> desc)
    {
        desc.rows = 9;
        desc.sorted = true;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateGui(pack);
    }

    @Override
    protected Window createEditContentWindow(WrappedGui content)
    {
        return new WindowEditAttributeContent(content);
    }
}
