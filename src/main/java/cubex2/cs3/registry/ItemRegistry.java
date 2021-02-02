package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.item.WindowItems;
import cubex2.cs3.lib.Strings;

public class ItemRegistry extends ContentRegistry<WrappedItem>
{
    public ItemRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public WrappedItem newDataInstance()
    {
        return new WrappedItem(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowItems(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Items";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_ITEM;
    }
}
