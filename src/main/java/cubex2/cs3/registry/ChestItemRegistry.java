package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.ChestItem;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowChestItems;
import cubex2.cs3.lib.Strings;

public class ChestItemRegistry extends ContentRegistry<ChestItem>
{
    public ChestItemRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public ChestItem newDataInstance()
    {
        return new ChestItem(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowChestItems(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Chest Items";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_CHEST_ITEM;
    }
}
