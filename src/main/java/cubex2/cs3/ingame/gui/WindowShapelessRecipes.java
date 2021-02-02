package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ShapelessRecipe;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowShapelessRecipes extends WindowContentList<ShapelessRecipe>
{
    public WindowShapelessRecipes(BaseContentPack pack)
    {
        super(ShapelessRecipe.class, "Shapeless Recipes", 263, 165, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<ShapelessRecipe> desc)
    {
        desc.rows = 2;
        desc.columns = 2;
        desc.elementHeight = 60;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateShapelessRecipe(pack);
    }

    @Override
    protected Window createEditContentWindow(ShapelessRecipe content)
    {
        return new WindowEditOrCreateShapelessRecipe(content, pack);
    }
}
