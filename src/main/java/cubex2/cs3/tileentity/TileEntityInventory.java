package cubex2.cs3.tileentity;

import com.google.common.collect.Maps;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.tileentity.attributes.TileEntityInventoryAttributes;
import cubex2.cs3.tileentity.data.FurnaceModule;
import cubex2.cs3.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.Map;

public class TileEntityInventory extends TileEntityCS implements IInventory
{

    public ItemStack[] inventoryContents;
    private int slotCount;
    private TileEntityInventoryAttributes invContainer;

    private Map<FurnaceModule, FurnaceData> furnaceData = Maps.newHashMap();

    public TileEntityInventory(WrappedTileEntity wrappedTileEntity)
    {
        super(wrappedTileEntity);
        invContainer = (TileEntityInventoryAttributes) wrappedTileEntity.container;
        slotCount = invContainer.slotCount;
        inventoryContents = new ItemStack[slotCount];
    }

    public TileEntityInventory()
    {
    }

    public FurnaceData getFurnaceData(FurnaceModule module)
    {
        return furnaceData.get(module);
    }

    public int getCookProgressScaled(String name, int width)
    {
        FurnaceModule module = invContainer.furnaceModules.getModule(name);
        if (module == null)
            return 0;
        return furnaceData.get(module).cookTime * width / module.cookTime;
    }

    public int getBurnTimeRemainingScaled(String name, int width)
    {
        FurnaceModule module = invContainer.furnaceModules.getModule(name);
        if (module == null)
            return 0;

        FurnaceData data = getFurnaceData(module);

        if (data.currentBurnTime == 0)
        {
            data.currentBurnTime = module.cookTime;
        }

        return data.burnTime * width / data.currentBurnTime;
    }

    public TileEntityInventoryAttributes getContainer()
    {
        return invContainer;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        for (FurnaceModule module : invContainer.furnaceModules.list)
        {
            FurnaceData data = furnaceData.get(module);
            if (data == null)
            {
                data = new FurnaceData(0, 0, 0);
                furnaceData.put(module, data);
            }

            boolean flag = data.burnTime > 0;
            boolean flag1 = false;

            if (data.burnTime > 0)
            {
                --data.burnTime;
            }

            if (!worldObj.isRemote)
            {
                if (data.burnTime != 0 || inventoryContents[module.fuelSlot] != null && inventoryContents[module.inputSlot] != null)
                {
                    if (data.burnTime == 0 && canSmelt(module))
                    {
                        data.currentBurnTime = data.burnTime = invContainer.getPack().fuelHandler.getItemBurnTime(inventoryContents[module.fuelSlot], module.fuelList);

                        if (data.burnTime > 0)
                        {
                            flag1 = true;

                            if (this.inventoryContents[module.fuelSlot] != null)
                            {
                                --this.inventoryContents[module.fuelSlot].stackSize;

                                if (this.inventoryContents[module.fuelSlot].stackSize == 0)
                                {
                                    this.inventoryContents[module.fuelSlot] = inventoryContents[module.fuelSlot].getItem().getContainerItem(inventoryContents[module.fuelSlot]);
                                }
                            }
                        }
                    }

                    if (isBurning(data) && canSmelt(module))
                    {
                        ++data.cookTime;

                        if (data.cookTime == module.cookTime)
                        {
                            data.cookTime = 0;
                            smeltItem(module);
                            flag1 = true;
                        }
                    } else
                    {
                        data.cookTime = 0;
                    }
                }

                if (flag != data.burnTime > 0)
                {
                    flag1 = true;
                    // TODO update furnace state
                }
            }

            if (flag1)
            {
                markDirty();
            }
        }
    }

    public void smeltItem(FurnaceModule module)
    {
        if (this.canSmelt(module))
        {
            ItemStack itemstack = invContainer.getPack().smeltingRecipeHandler.getSmeltingResult(inventoryContents[module.inputSlot], module.recipeList);

            if (this.inventoryContents[module.outputSlot] == null)
            {
                this.inventoryContents[module.outputSlot] = itemstack.copy();
            } else if (this.inventoryContents[module.outputSlot].getItem() == itemstack.getItem())
            {
                this.inventoryContents[module.outputSlot].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --this.inventoryContents[module.inputSlot].stackSize;

            if (this.inventoryContents[module.inputSlot].stackSize <= 0)
            {
                this.inventoryContents[module.inputSlot] = null;
            }
        }
    }

    private boolean isBurning(FurnaceData data)
    {
        return data.burnTime > 0;
    }

    private boolean canSmelt(FurnaceModule module)
    {
        if (this.inventoryContents[module.inputSlot] == null)
        {
            return false;
        } else
        {
            ItemStack itemstack = invContainer.getPack().smeltingRecipeHandler.getSmeltingResult(inventoryContents[module.inputSlot], module.recipeList);
            if (itemstack == null) return false;
            if (this.inventoryContents[module.outputSlot] == null) return true;
            if (!this.inventoryContents[module.outputSlot].isItemEqual(itemstack)) return false;
            int result = inventoryContents[module.outputSlot].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inventoryContents[module.outputSlot].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        invContainer = (TileEntityInventoryAttributes) container;

        slotCount = invContainer.slotCount;
        inventoryContents = new ItemStack[slotCount];

        Util.readStacksFromNBT("CS3_Inv_Items", inventoryContents, compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        Util.writeStacksToNBT("CS3_Inv_Items", inventoryContents, compound);
    }

    @Override
    public int getSizeInventory()
    {
        return slotCount;
    }

    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return p_70301_1_ >= 0 && p_70301_1_ < this.inventoryContents.length ? this.inventoryContents[p_70301_1_] : null;
    }

    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.inventoryContents[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.inventoryContents[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.inventoryContents[p_70298_1_];
                this.inventoryContents[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            } else
            {
                itemstack = this.inventoryContents[p_70298_1_].splitStack(p_70298_2_);

                if (this.inventoryContents[p_70298_1_].stackSize == 0)
                {
                    this.inventoryContents[p_70298_1_] = null;
                }

                this.markDirty();
                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.inventoryContents[p_70304_1_] != null)
        {
            ItemStack itemstack = this.inventoryContents[p_70304_1_];
            this.inventoryContents[p_70304_1_] = null;
            return itemstack;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.inventoryContents[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return "invCS3TileEntity";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return true;
    }

    public static class FurnaceData
    {
        public int burnTime;
        public int currentBurnTime;
        public int cookTime;

        public FurnaceData(int burnTime, int currentBurnTime, int cookTime)
        {
            this.burnTime = burnTime;
            this.currentBurnTime = currentBurnTime;
            this.cookTime = cookTime;
        }
    }
}
