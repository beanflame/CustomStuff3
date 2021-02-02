package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.DungeonMob;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowDungeonMobs;
import cubex2.cs3.lib.Strings;

public class DungeonMobRegistry extends ContentRegistry<DungeonMob>
{
    public DungeonMobRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public DungeonMob newDataInstance()
    {
        return new DungeonMob(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowDungeonMobs(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Dungeon Mobs";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_DUNGEON_MOB;
    }
}
