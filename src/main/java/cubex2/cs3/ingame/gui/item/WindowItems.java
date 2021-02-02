package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowContentList;
import cubex2.cs3.ingame.gui.WindowCreateItem;
import cubex2.cs3.ingame.gui.common.WindowEditAttributeContent;
import cubex2.cs3.ingame.gui.control.ToolTipProvider;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.lib.Strings;

public class WindowItems extends WindowContentList<WrappedItem>
{
    public WindowItems(BaseContentPack pack)
    {
        super(WrappedItem.class, "Items", 263, 160, pack);
        btnEdit.toolTipProvider = new ToolTipProvider.SelectedNotEnabled<WrappedItem>(listBox, Strings.TOOL_TIP_NEEDS_RESTART);
        btnDelete.toolTipProvider = new ToolTipProvider.Enabled(Strings.TOOL_TIP_NEEDS_RESTART);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<WrappedItem> desc)
    {
        desc.rows = 5;
        desc.elementHeight = 22;
        desc.sorted = true;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateItem(pack);
    }

    @Override
    protected Window createEditContentWindow(WrappedItem content)
    {
        return new WindowEditAttributeContent(content);
    }
}
