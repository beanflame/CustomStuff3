package cubex2.cs3.util;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class RecipeInput
{
    private ItemStack stack;
    private String oreClass;

    public RecipeInput(ItemStack stack)
    {
        this.stack = stack;
    }

    public RecipeInput(String oreClass)
    {
        this.oreClass = oreClass;
    }

    public boolean isOreClass()
    {
        return oreClass != null;
    }

    public Object getInput()
    {
        return isOreClass() ? oreClass : stack;
    }

    public List<ItemStack> getStacks()
    {
        List<ItemStack> stacks = Lists.newArrayList();
        if (stack != null)
        {
            stacks.add(stack.copy());
        } else
        {
            stacks.addAll(OreDictionary.getOres(oreClass));
        }
        return stacks;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("IsOre", oreClass != null);
        if (oreClass != null)
        {
            compound.setString("OreClass", oreClass);
        } else
        {
            compound.setTag("Stack", ItemStackHelper.writeToNBTNamed(stack));
        }
    }

    public static RecipeInput loadFromNBT(NBTTagCompound compound)
    {
        if (!compound.hasKey("IsOre"))
        {
            return null;
        }

        boolean isOre = compound.getBoolean("IsOre");
        if (isOre)
        {
            return new RecipeInput(compound.getString("OreClass"));
        } else
        {
            return new RecipeInput(ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Stack")));
        }
    }
}
