package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.MobSpawn;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowMobSpawns extends WindowContentList<MobSpawn>
{
    public WindowMobSpawns(BaseContentPack pack)
    {
        super(MobSpawn.class, "Mob Spawns", 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<MobSpawn> desc)
    {
        desc.rows = 12;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateMobSpawn(pack);
    }

    @Override
    protected Window createEditContentWindow(MobSpawn content)
    {
        return new WindowEditOrCreateMobSpawn(content, pack);
    }
}
