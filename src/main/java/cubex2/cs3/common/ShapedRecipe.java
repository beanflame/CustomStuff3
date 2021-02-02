package cubex2.cs3.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedRecipe extends BaseContent
{
    public RecipeInput[] input;
    public int width;
    public int height;
    public ItemStack result;

    private ShapedOreRecipe recipe;

    private boolean postponed = false;

    public ShapedRecipe(BaseContentPack pack)
    {
        super(pack);
    }

    public ShapedRecipe(int width, int height, RecipeInput[] input, ItemStack result, BaseContentPack pack)
    {
        super(pack);
        this.width = width;
        this.height = height;
        this.input = input;
        this.result = result;
    }

    @Override
    public void apply()
    {
        recipe = new ShapedOreRecipe(result, createRecipeObjects());
        GameRegistry.addRecipe(recipe);

        super.apply();
    }

    @Override
    public void remove()
    {
        CraftingManager.getInstance().getRecipeList().remove(recipe);

        super.remove();
    }

    @Override
    public void edit()
    {
        CraftingManager.getInstance().getRecipeList().remove(recipe);
        recipe = new ShapedOreRecipe(result, createRecipeObjects());
        GameRegistry.addRecipe(recipe);

        super.edit();
    }

    private Object[] createRecipeObjects()
    {
        Object[] recipeObjects = new Object[height + (width * height) * 2];

        String[] shape = createShape();
        System.arraycopy(shape, 0, recipeObjects, 0, shape.length);

        for (int i = 0, n = 0; i < width * height * 2; i += 2)
        {
            int idx = i + height;
            recipeObjects[idx] = input[n] != null ? (char) (n) : Character.MAX_VALUE;
            recipeObjects[idx + 1] = input[n] != null ? input[n].getInput() : Blocks.air;
            n += 1;
        }

        return recipeObjects;
    }

    private String[] createShape()
    {
        String[] shape = new String[height];
        int n = 0;
        for (int i = 0; i < shape.length; i++)
        {
            String s = "";
            for (int j = 0; j < width; j++)
            {
                s += (char) n++;
            }
            shape[i] = s;
        }
        return shape;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setByte("Width", (byte) width);
        compound.setByte("Height", (byte) height);

        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < input.length; i++)
        {
            if (input[i] != null)
            {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Index", (byte) i);

                input[i].writeToNBT(compound1);

                tagList.appendTag(compound1);
            }
        }
        compound.setTag("Input", tagList);

        compound.setTag("Result", ItemStackHelper.writeToNBTNamed(result));
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        width = compound.getByte("Width");
        height = compound.getByte("Height");

        NBTTagList tagList = compound.getTagList("Input", 10);
        input = new RecipeInput[width * height];
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound compound1 = tagList.getCompoundTagAt(i);
            int idx = compound1.getByte("Index");
            input[idx] = RecipeInput.loadFromNBT(compound1);
            if (input[idx] != null && input[idx].getInput() == null)
            {
                return false;
            }
        }

        result = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Result"));
        return result != null;


    }
}
