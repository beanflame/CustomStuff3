package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.GrassSeed;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowGrassSeeds extends WindowContentList<GrassSeed>
{
    public WindowGrassSeeds(BaseContentPack pack)
    {
        super(GrassSeed.class, "Grass Seeds", NEW | DELETE | BACK, 263, 171, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<GrassSeed> desc)
    {
        desc.elementHeight = 22;
        desc.rows = 6;
        desc.columns = 2;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateGrassSeed(pack);
    }

    @Override
    protected Window createEditContentWindow(GrassSeed content)
    {
        return null;
    }
}
