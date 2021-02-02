package cubex2.cs3.common.inventory;

import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Inventory
{
    private IInventory inv;

    public Inventory(IInventory inventory)
    {
        inv = inventory;
    }

    public void clear()
    {
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            if (inv.getStackInSlot(i) != null)
            {
                inv.setInventorySlotContents(i, null);
            }
        }
    }

    public int fill(Item item, int metadata)
    {
        int amount = 0;
        int maxStack = item.getItemStackLimit(new ItemStack(item, 1, metadata));

        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack == null)
            {
                inv.setInventorySlotContents(i, new ItemStack(item, maxStack, metadata));
                amount += maxStack;
            } else if (itemstack.getItem() == item && itemstack.getItemDamage() == metadata)
            {
                amount += maxStack - itemstack.stackSize;
                inv.setInventorySlotContents(i, new ItemStack(item, maxStack, metadata));
            }
        }

        return amount;
    }

    public int add(Item item, int count, int metadata)
    {
        return add(item, count, metadata, inv.getSizeInventory() - 1);
    }

    public int add(Item item, int count, int metadata, int maxSlotId)
    {
        int amount = 0;
        int slotId = 0;

        while (amount != count)
        {
            if (slotId > inv.getSizeInventory() || slotId > maxSlotId)
            {
                break;
            }

            ItemStack stack = inv.getStackInSlot(slotId);

            if (stack == null || stack.getItem() != item || stack.getItemDamage() != metadata || stack.stackSize == stack.getMaxStackSize())
            {
                slotId++;
                continue;
            }

            inv.setInventorySlotContents(slotId, new ItemStack(item, stack.stackSize + 1, metadata));
            amount++;
        }

        slotId = 0;

        while (amount != count)
        {
            if (slotId > inv.getSizeInventory() || slotId > maxSlotId)
            {
                break;
            }

            ItemStack stack = inv.getStackInSlot(slotId);

            if (stack != null && (stack.getItem() != item || stack.getItemDamage() != metadata || stack.stackSize == stack.getMaxStackSize()))
            {
                slotId++;
                continue;
            }
            if (stack == null)
            {
                inv.setInventorySlotContents(slotId, new ItemStack(item, 1, metadata));
                amount++;
                continue;
            }
            inv.setInventorySlotContents(slotId, new ItemStack(item, stack.stackSize + 1, metadata));
            amount++;
        }

        return amount;
    }

    public int remove(Item item, int count, int metadata)
    {
        int amount = 0;
        int slotId = 0;

        while (amount != count)
        {
            if (slotId > inv.getSizeInventory())
            {
                break;
            }

            ItemStack stack = inv.getStackInSlot(slotId);

            if (stack == null || stack.getItem() != item || stack.getItemDamage() != metadata)
            {
                slotId++;
                continue;
            }
            if (stack.stackSize == 1)
            {
                inv.setInventorySlotContents(slotId, null);
                amount++;
                continue;
            }
            inv.setInventorySlotContents(slotId, new ItemStack(item, stack.stackSize - 1, metadata));
            amount++;
        }

        return amount;
    }

    public void clearSlot(int slotId)
    {
        inv.setInventorySlotContents(slotId, null);
    }

    public int fillSlot(int slotId)
    {
        int amount = 0;

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            amount = stack.getMaxStackSize() - stack.stackSize;
            inv.setInventorySlotContents(slotId, new ItemStack(stack.getItem(), stack.getMaxStackSize(), stack.getItemDamage()));
        }

        return amount;
    }

    public int setSlot(int slotId, Item item, int count, int metadata)
    {
        int amount;
        int maxStack = Math.min(count, item.getItemStackLimit(new ItemStack(item, 1, metadata)));

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack == null || stack.getItem() != item || stack.getItemDamage() != metadata)
        {
            inv.setInventorySlotContents(slotId, new ItemStack(item, maxStack, metadata));
            amount = maxStack;
        } else
        {
            amount = maxStack - stack.stackSize;
            inv.setInventorySlotContents(slotId, new ItemStack(item, maxStack, metadata));
        }

        return amount;
    }

    public int addToSlot(int slotId, int count)
    {
        int amount = 0;

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            amount = Math.min(stack.getMaxStackSize() - stack.stackSize, count);
            inv.setInventorySlotContents(slotId, new ItemStack(stack.getItem(), stack.stackSize + amount, stack.getItemDamage()));
        }

        return amount;
    }

    public int removeFromSlot(int slotId, int count)
    {
        int amount = 0;

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            amount = Math.min(count, stack.stackSize);
            if (amount == stack.stackSize)
            {
                inv.setInventorySlotContents(slotId, null);
            } else
            {
                inv.setInventorySlotContents(slotId, new ItemStack(stack.getItem(), stack.stackSize - amount, stack.getItemDamage()));
            }

        }

        return amount;
    }

    public void damageItem(int slotId, int count)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
            if (stack.isItemStackDamageable())
            {
                stack.setItemDamage(stack.getItemDamage() + count);

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

    public void repairItem(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
            if (stack.isItemStackDamageable())
            {
                stack.setItemDamage(0);
            }
    }

    public void repairItem(int slotId, int count)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
            if (stack.isItemStackDamageable())
            {
                int amount = Math.min(count, stack.getItemDamage());
                stack.setItemDamage(stack.getItemDamage() - amount);
            }
    }

    public ItemStack getStack(int slotId)
    {
        return inv.getStackInSlot(slotId);
    }

    public Item getItem(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? null : stack.getItem();
    }

    public int getStackSize(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.stackSize;
    }

    public int getMaxStackSize(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.getMaxStackSize();
    }

    public int getDamage(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.getItemDamage();
    }

    public int getItemCount(Item item, int metadata)
    {
        int amount = 0;

        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null)
                if (ItemStackHelper.itemStackEqual(stack, new ItemStack(item, 1, metadata)))
                {
                    amount += stack.stackSize;
                }
        }

        return amount;
    }

    public void moveStack(int from, int to)
    {
        if (inv.getStackInSlot(from) == null)
            return;
        if (inv.getStackInSlot(to) == null)
        {
            ItemStack stack = inv.getStackInSlot(from);
            inv.setInventorySlotContents(from, null);
            inv.setInventorySlotContents(to, stack);
        } else if (inv.getStackInSlot(from).isItemEqual(inv.getStackInSlot(to)))
        {
            ItemStack stackFrom = inv.getStackInSlot(from);
            ItemStack stackTo = inv.getStackInSlot(to);

            while (stackTo.stackSize < stackTo.getMaxStackSize() && stackFrom.stackSize > 0)
            {
                stackTo.stackSize++;
                stackFrom.stackSize--;
            }

            if (stackFrom.stackSize == 0)
            {
                inv.setInventorySlotContents(from, null);
            }
        }
    }

    public boolean isItemEqual(int slot1, int slot2)
    {
        ItemStack stack1 = inv.getStackInSlot(slot1);
        ItemStack stack2 = inv.getStackInSlot(slot2);

        if (stack1 == null && stack2 == null)
            return true;
        else if (stack1 == null ^ stack2 == null)
            return false;
        else
            return stack1.isItemEqual(stack2);
    }

    public ItemStack findItem(Item item, int metadata)
    {
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null)
                if (ItemStackHelper.itemStackEqual(stack, new ItemStack(item, 1, metadata)))
                    return stack;
        }

        return null;
    }

    public int findItemIndex(Item item, int metadata)
    {
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null)
                if (ItemStackHelper.itemStackEqual(stack, new ItemStack(item, 1, metadata)))
                    return i;
        }

        return -1;
    }

    public boolean hasEnchantment(int slotId, int id)
    {
        return hasEnchantment(slotId, id, -1);
    }

    public boolean hasEnchantment(int slotId, int id, int level)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack != null && ItemStackHelper.hasEnchantment(stack, id, level);
    }

    public void clearEnchantments(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.clearEnchantments(stack);
        }
    }

    public void addEnchantment(int slotId, int id, int level)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.addEnchantment(stack, id, level);
        }
    }

    public void removeEnchantment(int slotId, int id)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.removeEnchantment(stack, id);
        }
    }

    public int getIntData(int slotId, String name)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getIntData(stack, name);
        return -1;
    }

    public float getFloatData(int slotId, String name)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getFloatData(stack, name);
        return -1.0f;
    }

    public String getStringData(int slotId, String name)
    {

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getStringData(stack, name);
        return null;
    }

    public void setIntData(int slotId, String name, int data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setIntData(stack, name, data);
        }
    }

    public void setFloatData(int slotId, String name, float data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setFloatData(stack, name, data);
        }
    }

    public void setStringData(int slotId, String name, String data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setStringData(stack, name, data);
        }
    }
}
