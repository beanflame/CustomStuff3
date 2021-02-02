package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.ShapelessRecipe;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowShapelessRecipes;
import cubex2.cs3.lib.Strings;

public class ShapelessRecipeRegistry extends ContentRegistry<ShapelessRecipe>
{
    public ShapelessRecipeRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public ShapelessRecipe newDataInstance()
    {
        return new ShapelessRecipe(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowShapelessRecipes(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Shapeless Recipes";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_SHAPELESS_RECIPE;
    }
}
