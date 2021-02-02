package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.CreativeTab;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowCreativeTabs;
import cubex2.cs3.lib.Strings;

public class CreativeTabRegistry extends ContentRegistry<CreativeTab>
{
    public CreativeTabRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public CreativeTab newDataInstance()
    {
        return new CreativeTab(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowCreativeTabs(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Creative Tabs";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_CREATIVE_TAB;
    }
}
