package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.GrassPlant;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowGrassPlants;
import cubex2.cs3.lib.Strings;

public class GrassPlantRegistry extends ContentRegistry<GrassPlant>
{
    public GrassPlantRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public GrassPlant newDataInstance()
    {
        return new GrassPlant(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowGrassPlants(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Grass Plants";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_GRASS_PLANT;
    }
}
