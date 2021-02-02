package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.TradeRecipe;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowTradeRecipes;
import cubex2.cs3.lib.Strings;

public class TradeRecipeRegistry extends ContentRegistry<TradeRecipe>
{
    public TradeRecipeRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public TradeRecipe newDataInstance()
    {
        return new TradeRecipe(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowTradeRecipes(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Trade Recipes";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_TRADE_RECIPE;
    }
}
