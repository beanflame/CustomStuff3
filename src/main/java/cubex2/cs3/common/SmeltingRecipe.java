package cubex2.cs3.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class SmeltingRecipe extends BaseContent
{
    public ItemStack input;
    public ItemStack result;
    public String recipeList = "vanilla";

    public SmeltingRecipe(String recipeList, ItemStack input, ItemStack result, BaseContentPack pack)
    {
        super(pack);
        this.recipeList = recipeList;
        this.input = input;
        this.result = result;
    }

    public SmeltingRecipe(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public void apply()
    {
        apply_do();
        super.apply();
    }

    private void apply_do()
    {
        if (recipeList.equals("vanilla"))
            GameRegistry.addSmelting(input, result, 0.0f);
    }

    @Override
    public void remove()
    {
        remove_do();
        super.remove();
    }

    private void remove_do()
    {
        if (recipeList.equals("vanilla"))
        {
            Map<?, ?> smeltingList = FurnaceRecipes.smelting().getSmeltingList();
            for (Object o : smeltingList.keySet())
            {
                ItemStack stack = (ItemStack) o;
                if (stack.getItem() == input.getItem() && stack.getItemDamage() == input.getItemDamage())
                {
                    smeltingList.remove(o);
                    break;
                }
            }
        }
    }

    @Override
    public void edit()
    {
        remove_do();
        apply_do();
        super.edit();
    }


    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("Input", ItemStackHelper.writeToNBTNamed(input));
        compound.setTag("Result", ItemStackHelper.writeToNBTNamed(result));
        compound.setString("RecipeList", recipeList);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        input = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Input"));
        result = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Result"));
        if (compound.hasKey("RecipeList"))
            recipeList = compound.getString("RecipeList");

        return input != null && result != null;
    }

    public ItemStack getResult(ItemStack stack, String list)
    {
        if (!list.equals(recipeList) || stack == null || !compareItemStacks(stack, input)) return null;
        return result;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }
}
