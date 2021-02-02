package cubex2.cs3.registry;

import com.google.common.collect.Sets;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowSmeltingRecipes;
import cubex2.cs3.lib.Strings;

import java.util.Arrays;
import java.util.Set;

public class SmeltingRecipeRegistry extends ContentRegistry<SmeltingRecipe>
{
    public SmeltingRecipeRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public SmeltingRecipe newDataInstance()
    {
        return new SmeltingRecipe(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowSmeltingRecipes(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Smelting Recipes";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_SMELTING_RECIPE;
    }

    public String[] getRecipeLists()
    {
        Set<String> lists = Sets.newHashSet();
        for (SmeltingRecipe recipe : getContentList())
        {
            lists.add(recipe.recipeList);
        }
        lists.add("vanilla");

        String[] ret = lists.toArray(new String[lists.size()]);
        Arrays.sort(ret);
        return ret;
    }
}
