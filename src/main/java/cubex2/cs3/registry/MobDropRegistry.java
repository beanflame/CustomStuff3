package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.MobDrop;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowMobDrops;
import cubex2.cs3.lib.Strings;

public class MobDropRegistry extends ContentRegistry<MobDrop>
{
    public MobDropRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public MobDrop newDataInstance()
    {
        return new MobDrop(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowMobDrops(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Mob Drops";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_MOB_DROP;
    }
}
