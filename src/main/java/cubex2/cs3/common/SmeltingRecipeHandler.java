package cubex2.cs3.common;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class SmeltingRecipeHandler
{
    private final BaseContentPack pack;

    public SmeltingRecipeHandler(BaseContentPack pack)
    {
        this.pack = pack;
    }

    public ItemStack getSmeltingResult(ItemStack stack, String recipeList)
    {
        if (recipeList.equals("vanilla"))
            return FurnaceRecipes.smelting().getSmeltingResult(stack);
        for (SmeltingRecipe recipe : pack.getContentRegistry(SmeltingRecipe.class).getContentList())
        {
            ItemStack result = recipe.getResult(stack, recipeList);
            if (result != null) return result;
        }
        return null;
    }

    public float getSmeltingExperience(ItemStack stack, String recipeList)
    {
        if (recipeList.equals("vanilla"))
            return FurnaceRecipes.smelting().func_151398_b(stack);
        return 0f;
    }
}
