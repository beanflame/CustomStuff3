package cubex2.cs3.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class WrappedInventory implements IInventory
{
    private final IInventory inv;

    public String customName;

    public WrappedInventory(IInventory inv)
    {
        this.inv = inv;
    }

    @Override
    public int getSizeInventory()
    {
        return inv.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return inv.getStackInSlot(p_70301_1_);
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        return inv.decrStackSize(p_70298_1_, p_70298_2_);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        return inv.getStackInSlotOnClosing(p_70304_1_);
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        inv.setInventorySlotContents(p_70299_1_, p_70299_2_);
    }

    @Override
    public String getInventoryName()
    {
        if (customName != null)
            return customName;
        return inv.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return customName != null || inv.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inv.getInventoryStackLimit();
    }

    @Override
    public void markDirty()
    {
        inv.markDirty();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return inv.isUseableByPlayer(p_70300_1_);
    }

    @Override
    public void openInventory()
    {
        inv.openInventory();
    }

    @Override
    public void closeInventory()
    {
        inv.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return inv.isItemValidForSlot(p_94041_1_, p_94041_2_);
    }
}
