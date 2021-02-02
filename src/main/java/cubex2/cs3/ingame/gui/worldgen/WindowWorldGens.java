package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowContentList;
import cubex2.cs3.ingame.gui.common.WindowEditAttributeContent;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowWorldGens extends WindowContentList<WrappedWorldGen>
{
    public WindowWorldGens(BaseContentPack pack)
    {
        super(WrappedWorldGen.class, "World Generators", 263, 160, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<WrappedWorldGen> desc)
    {
        desc.rows = 5;
        desc.elementHeight = 22;
        desc.sorted = true;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateWorldGen(pack);
    }

    @Override
    protected Window createEditContentWindow(WrappedWorldGen content)
    {
        return new WindowEditAttributeContent(content);
    }
}
