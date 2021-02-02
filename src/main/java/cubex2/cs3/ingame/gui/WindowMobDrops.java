package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.MobDrop;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowMobDrops extends WindowContentList<MobDrop>
{
    public WindowMobDrops(BaseContentPack pack)
    {
        super(MobDrop.class, "Mob Drops", 263, 171, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<MobDrop> desc)
    {
        desc.elementHeight = 22;
        desc.rows = 6;
        desc.columns = 2;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateMobDrop(pack);
    }

    @Override
    protected Window createEditContentWindow(MobDrop content)
    {
        return new WindowEditOrCreateMobDrop(content, pack);
    }
}
