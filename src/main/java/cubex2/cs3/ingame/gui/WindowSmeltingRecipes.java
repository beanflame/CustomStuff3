package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

import java.util.Comparator;

public class WindowSmeltingRecipes extends WindowContentList<SmeltingRecipe>
{
    public WindowSmeltingRecipes(BaseContentPack pack)
    {
        super(SmeltingRecipe.class, "Smelting Recipes", 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<SmeltingRecipe> desc)
    {
        desc.rows = 5;
        desc.columns = 3;
        desc.elementHeight = 32;
        desc.sorted = true;
        desc.comparator = new Comparator<SmeltingRecipe>() {
            @Override
            public int compare(SmeltingRecipe o1, SmeltingRecipe o2)
            {
                return o1.recipeList.compareTo(o2.recipeList);
            }
        };
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateSmeltingRecipe(pack);
    }

    @Override
    protected Window createEditContentWindow(SmeltingRecipe content)
    {
        return new WindowEditOrCreateSmeltingRecipe(content, pack);
    }
}
