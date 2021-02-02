package cubex2.cs3.tileentity.data;

import cubex2.cs3.util.NBTDataList;

public class FurnaceModules extends NBTDataList<FurnaceModule>
{
    public FurnaceModules()
    {
        super(FurnaceModule.class);
    }

    public FurnaceModule getModule(String name)
    {
        for (FurnaceModule module : list)
        {
            if (module.name != null && module.name.equals(name))
                return module;
        }

        return null;
    }
}
