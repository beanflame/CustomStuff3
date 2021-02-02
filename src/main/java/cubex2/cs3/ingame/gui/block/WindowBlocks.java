package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowContentList;
import cubex2.cs3.ingame.gui.WindowCreateBlock;
import cubex2.cs3.ingame.gui.common.WindowEditAttributeContent;
import cubex2.cs3.ingame.gui.control.ToolTipProvider;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.lib.Strings;

public class WindowBlocks extends WindowContentList<WrappedBlock>
{
    public WindowBlocks(BaseContentPack pack)
    {
        super(WrappedBlock.class, "Blocks", 263, 160, pack);
        btnEdit.toolTipProvider = new ToolTipProvider.SelectedNotEnabled<WrappedBlock>(listBox, Strings.TOOL_TIP_NEEDS_RESTART);
        btnDelete.toolTipProvider = new ToolTipProvider.Enabled(Strings.TOOL_TIP_NEEDS_RESTART);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<WrappedBlock> desc)
    {
        desc.rows = 5;
        desc.elementHeight = 22;
        desc.sorted = true;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateBlock(pack);
    }

    @Override
    protected Window createEditContentWindow(WrappedBlock content)
    {
        return new WindowEditAttributeContent(content);
    }
}
