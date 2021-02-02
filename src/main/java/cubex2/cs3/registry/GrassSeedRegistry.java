package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.GrassSeed;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowGrassSeeds;
import cubex2.cs3.lib.Strings;

public class GrassSeedRegistry extends ContentRegistry<GrassSeed>
{
    public GrassSeedRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public GrassSeed newDataInstance()
    {
        return new GrassSeed(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowGrassSeeds(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Grass Seeds";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_GRASS_SEED;
    }
}
