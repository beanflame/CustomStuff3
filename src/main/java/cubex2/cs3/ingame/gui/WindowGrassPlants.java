package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.GrassPlant;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowGrassPlants extends WindowContentList<GrassPlant>
{
    public WindowGrassPlants(BaseContentPack pack)
    {
        super(GrassPlant.class, "Grass Plants", 263, 171, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<GrassPlant> desc)
    {
        desc.elementHeight = 22;
        desc.rows = 6;
        desc.columns = 2;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateGrassPlant(pack);
    }

    @Override
    protected Window createEditContentWindow(GrassPlant content)
    {
        return new WindowEditOrCreateGrassPlant(content, pack);
    }
}
