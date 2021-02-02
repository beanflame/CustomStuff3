package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.TradeRecipe;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowTradeRecipes extends WindowContentList<TradeRecipe>
{
    public WindowTradeRecipes(BaseContentPack pack)
    {
        super(TradeRecipe.class, "Trade Recipes", 263, 171, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<TradeRecipe> desc)
    {
        desc.columns = 2;
        desc.rows = 6;
        desc.elementHeight = 22;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateTradeRecipe(pack);
    }

    @Override
    protected Window createEditContentWindow(TradeRecipe content)
    {
        return new WindowEditOrCreateTradeRecipe(content, pack);
    }
}
