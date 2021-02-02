package cubex2.cs3.registry;

import cubex2.cs3.common.MobSpawn;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowMobSpawns;
import cubex2.cs3.lib.Strings;

public class MobSpawnRegistry extends ContentRegistry<MobSpawn>
{
    public MobSpawnRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public MobSpawn newDataInstance()
    {
        return new MobSpawn(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowMobSpawns(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Mob Spawns";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_MOB_SPAWN;
    }
}
