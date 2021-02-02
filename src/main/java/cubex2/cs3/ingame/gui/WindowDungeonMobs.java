package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.DungeonMob;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowDungeonMobs extends WindowContentList<DungeonMob>
{
    public WindowDungeonMobs(BaseContentPack pack)
    {
        super(DungeonMob.class, "Dungeon Mobs", 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<DungeonMob> desc)
    {
        desc.rows = 12;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateDungeonMob(pack);
    }

    @Override
    protected Window createEditContentWindow(DungeonMob content)
    {
        return new WindowEditOrCreateDungeonMob(content, pack);
    }
}
