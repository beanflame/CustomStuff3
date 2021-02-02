package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowShapedRecipes extends WindowContentList<ShapedRecipe>
{
    public WindowShapedRecipes(BaseContentPack pack)
    {
        super(ShapedRecipe.class, "Shaped Recipes", 263, 165, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<ShapedRecipe> desc)
    {
        desc.rows = 2;
        desc.columns = 2;
        desc.elementHeight = 60;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateShapedRecipe(pack);
    }

    @Override
    protected Window createEditContentWindow(ShapedRecipe content)
    {
        return new WindowEditOrCreateShapedRecipe(content, pack);
    }
}
