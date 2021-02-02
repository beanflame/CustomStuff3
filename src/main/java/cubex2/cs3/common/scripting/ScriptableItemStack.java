package cubex2.cs3.common.scripting;

import cubex2.cs3.util.GeneralHelper;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ScriptableItemStack
{
    public ItemStack stack;

    public ScriptableItemStack(ItemStack stack)
    {
        super();
        this.stack = stack;
    }

    /**
     * Sets the stack's item, damage value and stack size
     *
     * @param itemName  The new item name
     * @param stacksize The new stack size
     * @param meta      The new damage value
     */
    public void setValues(String itemName, int stacksize, int meta)
    {
        stack.func_150996_a(GeneralHelper.getItem(itemName));
        stack.stackSize = stacksize;
        stack.setItemDamage(meta);
    }

    /**
     * Sets the stack's item
     *
     * @param itemName The new item name
     */
    public void setItem(String itemName)
    {
        stack.func_150996_a(GeneralHelper.getItem(itemName));
    }

    /**
     * Gets the stack's itemID
     *
     * @return The stack's itemID
     */
    public String getItemName()
    {
        return GeneralHelper.getItemName(stack.getItem());
    }

    /**
     * Sets the stack's damage value
     *
     * @param damage The new damage value
     */
    public void setDamage(int damage)
    {
        stack.setItemDamage(damage);
    }

    /**
     * Gets the stack's damage value
     *
     * @return The stack's damage value
     */
    public int getDamage()
    {
        return stack.getItemDamage();
    }

    /**
     * Sets the stack's stack size
     *
     * @param stackSize The new stack size
     */
    public void setStackSize(int stackSize)
    {
        stack.stackSize = stackSize;
    }

    /**
     * Gets the stack's stack size
     *
     * @return The stack's stack size
     */
    public int getStackSize()
    {
        return stack.stackSize;
    }

    /**
     * Gets the stack's maximum stack size
     *
     * @return The stack's maximum stack size
     */
    public int getMaxStackSize()
    {
        return stack.getMaxStackSize();
    }

    /**
     * Damages the item by the given amount
     *
     * @param amount The damage to add to the item
     */
    public void damageItem(int amount)
    {
        ItemStackHelper.damageItem(stack, amount);
    }

    /**
     * Checks if the itemstack has any enchantment
     *
     * @return true if the itemstack has any enchantment
     */
    public boolean hasEnchantment()
    {
        return hasEnchantment(-1);
    }

    /**
     * Checks if the itemstack has the given enchantment
     *
     * @param id The id of the enchantment
     * @return true if the itemstack has the givem enchantment
     */
    public boolean hasEnchantment(int id)
    {
        return hasEnchantment(id, -1);
    }

    /**
     * Checks if the itemstack has the given enchantment.
     *
     * @param id    The id of the enchantment
     * @param level The level of the enchantment
     * @return true if the itemstack has the given enchantment with the given level
     */
    public boolean hasEnchantment(int id, int level)
    {
        return ItemStackHelper.hasEnchantment(stack, id, level);
    }

    /**
     * Removes all enchantments from the itemstack
     */
    public void clearEnchantments()
    {
        ItemStackHelper.clearEnchantments(stack);
    }

    /**
     * Adds an enchantment to the itemstack
     *
     * @param id    The id of the enchantment
     * @param level The level of the enchantment
     */
    public void addEnchantment(int id, int level)
    {
        ItemStackHelper.addEnchantment(stack, id, level);
    }

    /**
     * Removes an enchantment from the itemstack
     *
     * @param id The id of the enchantment
     */
    public void removeEnchantment(int id)
    {
        ItemStackHelper.removeEnchantment(stack, id);
    }

    /**
     * Gets the stack's int data
     *
     * @param name The data's name
     * @return The data or -1 if the data doesn't exist
     */
    public int getIntData(String name)
    {
        return ItemStackHelper.getIntData(stack, name);
    }

    /**
     * Gets the stack's float data
     *
     * @param name The data's name
     * @return The data or -1.0 if the data doesn't exist
     */
    public float getFloatData(String name)
    {
        return ItemStackHelper.getFloatData(stack, name);
    }

    /**
     * Gets the stack's string data
     *
     * @param name The data's name
     * @return The data or null if the data doesn't exist
     */
    public String getStringData(String name)
    {
        return ItemStackHelper.getStringData(stack, name);
    }

    /**
     * Sets the stack's int data
     *
     * @param name The data's name
     * @param data The data
     */
    public void setIntData(String name, int data)
    {
        ItemStackHelper.setIntData(stack, name, data);
    }

    /**
     * Sets the stack's float data
     *
     * @param name The data's name
     * @param data The data
     */
    public void setFloatData(String name, float data)
    {
        ItemStackHelper.setFloatData(stack, name, data);
    }

    /**
     * Sets the stack's string data
     *
     * @param name The data's name
     * @param data The data
     */
    public void setStringData(String name, String data)
    {
        ItemStackHelper.setStringData(stack, name, data);
    }

    /**
     * Checks if the itemstack can efficiently break the given block. It actually checks if it can harvest the block and if its efficiency
     * against the block is higher than 1.0.
     *
     * @param blockName The block's name
     * @param metadata  The block's metadata
     * @return true if the block can efficiently be breaked.
     */
    public boolean canEfficientlyBreak(String blockName, int metadata)
    {
        Block block = GeneralHelper.getBlock(blockName);
        return stack.getItem().canHarvestBlock(block, stack) && stack.getItem().getDigSpeed(stack, block, metadata) > 1.0f;
    }

    /**
     * Checks if the itemstack can harvest the given block.
     *
     * @param blockName The block's name
     * @return true if the block can efficiently be breaked.
     */
    public boolean canHarvest(String blockName)
    {
        return stack.getItem().canHarvestBlock(GeneralHelper.getBlock(blockName), stack);
    }
}
