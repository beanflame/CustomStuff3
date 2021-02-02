package cubex2.cs3.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.common.BaseContent;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessRecipe extends BaseContent
{
    public RecipeInput[] input;
    public ItemStack result;

    private ShapelessOreRecipe recipe;

    public ShapelessRecipe(BaseContentPack pack)
    {
        super(pack);
    }

    public ShapelessRecipe(RecipeInput[] input, ItemStack result, BaseContentPack pack)
    {
        super(pack);
        this.input = input;
        this.result = result;
    }

    @Override
    public void apply()
    {
        recipe = new ShapelessOreRecipe(result, createRecipeObjects());
        GameRegistry.addRecipe(recipe);

        super.apply();
    }

    @Override
    public void edit()
    {
        CraftingManager.getInstance().getRecipeList().remove(recipe);
        recipe = new ShapelessOreRecipe(result, createRecipeObjects());
        GameRegistry.addRecipe(recipe);

        super.edit();
    }

    @Override
    public void remove()
    {
        CraftingManager.getInstance().getRecipeList().remove(recipe);

        super.remove();
    }

    private Object[] createRecipeObjects()
    {
        Object[] recipeObjects = new Object[input.length];
        for (int i = 0; i < input.length; i++)
        {
            recipeObjects[i] = input[i].getInput();
        }

        return recipeObjects;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < input.length; i++)
        {
            NBTTagCompound compound1 = new NBTTagCompound();
            input[i].writeToNBT(compound1);
            tagList.appendTag(compound1);
        }
        compound.setTag("Input", tagList);

        compound.setTag("Result", ItemStackHelper.writeToNBTNamed(result));
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        NBTTagList tagList = compound.getTagList("Input", 10);
        input = new RecipeInput[tagList.tagCount()];
        for (int i=0;i<input.length;i++)
        {
            NBTTagCompound compound1 = tagList.getCompoundTagAt(i);
            input[i] = RecipeInput.loadFromNBT(compound1);
            if (input[i].getInput() == null)
            {
                return false;
            }
        }

        result = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Result"));
        return result != null;
    }
}
