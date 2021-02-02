package cubex2.cs3.util;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ItemStackHelper
{
    public static void damageItem(ItemStack stack, int amount)
    {
        if (stack.isItemStackDamageable())
        {
            stack.setItemDamage(stack.getItemDamage() + amount);

            if (stack.getItemDamage() > stack.getMaxDamage())
            {
                --stack.stackSize;

                if (stack.stackSize < 0)
                {
                    stack.stackSize = 0;
                }

                stack.setItemDamage(0);
            }
        }
    }

    public static boolean hasEnchantment(ItemStack stack, int id, int level)
    {
        if (!stack.isItemEnchanted())
            return false;
        NBTTagCompound tag = stack.getTagCompound();
        NBTTagList tagList = (NBTTagList) tag.getTag("ench");
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tag1 = tagList.getCompoundTagAt(i);
            boolean idMatch = id == -1 ? true : tag1.getShort("id") == id;
            boolean lvlMatch = level == -1 ? true : tag1.getShort("lvl") == level;
            if (idMatch && lvlMatch)
                return true;
        }
        return false;
    }

    public static void clearEnchantments(ItemStack stack)
    {
        if (!stack.isItemEnchanted())
            return;
        NBTTagCompound tag = stack.getTagCompound();
        tag.removeTag("ench");
    }

    public static void addEnchantment(ItemStack stack, int id, int level)
    {
        if (Enchantment.enchantmentsList[id] != null)
        {
            stack.addEnchantment(Enchantment.enchantmentsList[id], level);
        }
    }

    public static void removeEnchantment(ItemStack stack, int id)
    {
        if (hasEnchantment(stack, id, -1))
        {
            NBTTagCompound tag = stack.getTagCompound();
            NBTTagList tagList = (NBTTagList) tag.getTag("ench");
            for (int i = 0; i < tagList.tagCount(); i++)
            {
                NBTTagCompound tag1 = tagList.getCompoundTagAt(i);
                if (tag1.getShort("id") == id)
                {
                    tagList.removeTag(i);
                    break;
                }
            }
            if (tagList.tagCount() == 0)
            {
                tag.removeTag("ench");
            }
        }
    }

    public static int getIntData(ItemStack stack, String name)
    {
        return NBTHelper.getCSIntData(stack.stackTagCompound, name);
    }

    public static float getFloatData(ItemStack stack, String name)
    {
        return NBTHelper.getCSFloatData(stack.stackTagCompound, name);
    }

    public static String getStringData(ItemStack stack, String name)
    {
        return NBTHelper.getCSStringData(stack.stackTagCompound, name);
    }

    public static void setIntData(ItemStack stack, String name, int data)
    {
        initNBTTag(stack);
        NBTHelper.setCSIntData(stack.stackTagCompound, name, data);
    }

    public static void setFloatData(ItemStack stack, String name, float data)
    {
        initNBTTag(stack);
        NBTHelper.setCSFloatData(stack.stackTagCompound, name, data);
    }

    public static void setStringData(ItemStack stack, String name, String data)
    {
        initNBTTag(stack);
        NBTHelper.setCSStringData(stack.stackTagCompound, name, data);
    }

    public static void initNBTTag(ItemStack stack)
    {
        if (stack.stackTagCompound == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    public static List<ItemStack> getSubTypes(Item item)
    {
        List<ItemStack> stacks = Lists.newArrayList();
        item.getSubItems(item, null, stacks);
        return stacks;
    }

    public static ArrayList<ItemStack> getAllItemStacks()
    {
        return getAllItemStacks(true);
    }

    public static ArrayList<ItemStack> getAllItemStacks(boolean wildCardStacks)
    {
        ArrayList<ItemStack> stacks = Lists.newArrayList();

        for (Object o : GameData.getItemRegistry().getKeys())
        {
            Item item = (Item) GameData.getItemRegistry().getObject(o);

            List<ItemStack> itemStacks = Lists.newArrayList();
            if (item.getHasSubtypes())
            {
                itemStacks.addAll(ItemStackHelper.getSubTypes(item));
                if (wildCardStacks && itemStacks.size() > 1)
                {
                    itemStacks.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
                }
            } else if (item.isDamageable())
            {
                itemStacks.add(new ItemStack(item, 1, 0));
                if (wildCardStacks)
                {
                    itemStacks.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
                }
            } else
            {
                itemStacks.add(new ItemStack(item, 1, 0));
            }
            stacks.addAll(itemStacks);
        }

        return stacks;
    }

    public static List<ItemStack> getBlockStacks(boolean wildCardStacks)
    {
        List<ItemStack> stacks = Lists.newArrayList();

        for (Object o : GameData.getBlockRegistry().getKeys())
        {
            Block block = (Block) GameData.getBlockRegistry().getObject(o);
            Item item = Item.getItemFromBlock(block);
            block.getSubBlocks(Item.getItemFromBlock(block), CreativeTabs.tabAllSearch, stacks);
            if (item != null && wildCardStacks && item.getHasSubtypes())
            {
                stacks.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
            }
        }

        for (int i = 0; i < stacks.size(); i++)
        {
            if (stacks.get(i).getItem() == null)
            {
                stacks.remove(i--);
            }
        }

        return stacks;
    }

    public static List<ItemStack> getBlockStacks()
    {
        List<ItemStack> stacks = Lists.newArrayList();

        for (Object o : GameData.getBlockRegistry().getKeys())
        {
            Block block = (Block) GameData.getBlockRegistry().getObject(o);
            Item item = Item.getItemFromBlock(block);
            if (item != null && item instanceof ItemBlock)
            {
                if (item.getHasSubtypes())
                {
                    List<ItemStack> list = Lists.newArrayList();
                    item.getSubItems(item, CreativeTabs.tabAllSearch, list);
                    stacks.addAll(list);
                } else
                {
                    stacks.add(new ItemStack(item, 1, 0));
                }
            }
        }

        return stacks;
    }

    public static List<ItemStack> getOreItemStacks(String oreName)
    {
        List<ItemStack> stacks = Lists.newArrayList();
        List<ItemStack> oreStacks = OreDictionary.getOres(oreName);

        for (int i = 0; i < oreStacks.size(); i++)
        {
            ItemStack stack = oreStacks.get(i);
            Item item = stack.getItem();
            if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE && item != null)
            {
                if (item.getHasSubtypes())
                {
                    stacks.addAll(ItemStackHelper.getSubTypes(item));
                } else
                {
                    stacks.add(new ItemStack(item, 1, 0));
                }
            } else
            {
                stacks.add(stack.copy());
            }
        }
        return stacks;
    }

    public static boolean itemStackEqual(ItemStack is1, ItemStack is2)
    {
        if (is1 == null && is2 == null)
            return true;
        if (is1 == null ^ is2 == null)
            return false;
        if (is1.getItem() == null && is2.getItem() == null)
            return true;
        if (is1.getItem() == null ^ is2.getItem() == null)
            return false;

        boolean itemEqual = is1.getItem() == is2.getItem();

        boolean damageEqual;
        if (is1.getItemDamage() == OreDictionary.WILDCARD_VALUE || is2.getItemDamage() == OreDictionary.WILDCARD_VALUE)
        {
            damageEqual = true;
        } else
        {
            damageEqual = is1.getItemDamage() == is2.getItemDamage();
        }

        return itemEqual && damageEqual;
    }

    public static void writeToNBTNamed(ItemStack stack, NBTTagCompound nbt)
    {
        nbt.setString("ItemName", Item.itemRegistry.getNameForObject(stack.getItem()));
        nbt.setByte("Count", (byte) stack.stackSize);
        nbt.setShort("Damage", (short) stack.getItemDamage());

        if (stack.stackTagCompound != null)
        {
            nbt.setTag("tag", stack.stackTagCompound);
        }
    }

    public static NBTTagCompound writeToNBTNamed(ItemStack stack)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBTNamed(stack, nbt);
        return nbt;
    }

    public static ItemStack readFromNBTNamed(NBTTagCompound compound)
    {
        if (!compound.hasKey("ItemName"))
            return null;

        ItemStack stack = new ItemStack((Item) null);
        stack.func_150996_a(GameData.getItemRegistry().getObject(compound.getString("ItemName")));
        if (stack.getItem() == null)
            return null;
        stack.stackSize = compound.getByte("Count");
        stack.setItemDamage(compound.getShort("Damage"));

        if (compound.hasKey("tag", 10))
        {
            stack.stackTagCompound = compound.getCompoundTag("tag");
        }

        return stack;
    }
}
