package cubex2.cs3.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiContainerAttributes;
import cubex2.cs3.gui.data.*;
import cubex2.cs3.tileentity.TileEntityInventory;
import cubex2.cs3.tileentity.data.FurnaceModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.List;

public class ContainerBasic extends Container
{
    private final WrappedGui gui;
    private final EntityPlayer player;
    private final IInventory slotInv;
    private final GuiContainerAttributes container;

    private final boolean isTile;

    private int[] lastCookTimes;
    private int[] lastBurnTimes;
    private int[] lastCurrentBurnTimes;

    public ContainerBasic(WrappedGui gui, EntityPlayer player, IInventory slotInv)
    {
        this.gui = gui;
        this.container = (GuiContainerAttributes) gui.container;
        this.player = player;
        this.slotInv = slotInv;
        isTile = slotInv instanceof TileEntityInventory;

        if (isTile)
        {
            int num = ((TileEntityInventory) slotInv).getContainer().furnaceModules.list.size();
            lastCookTimes = new int[num];
            lastBurnTimes = new int[num];
            lastCurrentBurnTimes = new int[num];
        }


        final boolean isStackInv = slotInv instanceof InventoryItemStack;

        GuiData data = gui.container.guiData;
        int nextSlot = 0;
        for (int iter = 0; iter < 2; iter++)
        {
            for (ControlData cData : data.controls)
            {
                if (iter == 0 && cData instanceof SlotData)
                {
                    SlotData slotData = (SlotData) cData;
                    if (slotData.furnaceOutput)
                        addSlotToContainer(new SlotFurnaceOutput(gui.getPack(), slotData.recipeList, player, slotInv, nextSlot++, cData.x + 1, cData.y + 1));
                    else
                        addSlotToContainer(new Slot(slotInv, nextSlot++, cData.x + 1, cData.y + 1));
                } else if (iter == 1 && cData instanceof PlayerInventoryData)
                {
                    for (int i = 0; i < 3; ++i)
                    {
                        for (int j = 0; j < 9; ++j)
                        {
                            this.addSlotToContainer(new Slot(player.inventory, j + (i + 1) * 9, cData.x + 1 + j * 18, cData.y + 1 + i * 18));
                        }
                    }

                    for (int i = 0; i < 9; ++i)
                    {
                        this.addSlotToContainer(new Slot(player.inventory, i, cData.x + 1 + i * 18, cData.y + 59)
                        {
                            @Override
                            public boolean canTakeStack(EntityPlayer player)
                            {
                                return !isStackInv || player.inventory.getCurrentItem() != getStack();
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);

        if (isTile)
        {
            TileEntityInventory tile = (TileEntityInventory) slotInv;
            List<FurnaceModule> modules = tile.getContainer().furnaceModules.list;
            for (int i = 0; i < modules.size(); i++)
            {
                TileEntityInventory.FurnaceData data = tile.getFurnaceData(modules.get(i));
                crafting.sendProgressBarUpdate(this, i * 3, data.cookTime);
                crafting.sendProgressBarUpdate(this, i * 3 + 1, data.burnTime);
                crafting.sendProgressBarUpdate(this, i * 3 + 2, data.currentBurnTime);
            }
        }
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (isTile)
        {
            for (int i = 0; i < crafters.size(); i++)
            {
                ICrafting icrafting = (ICrafting) this.crafters.get(i);

                TileEntityInventory tile = (TileEntityInventory) slotInv;
                List<FurnaceModule> modules = tile.getContainer().furnaceModules.list;
                for (int j = 0; j < modules.size(); j++)
                {
                    TileEntityInventory.FurnaceData data = tile.getFurnaceData(modules.get(j));

                    if (lastCookTimes[j] != data.cookTime)
                    {
                        icrafting.sendProgressBarUpdate(this, j * 3, data.cookTime);
                    }

                    if (lastBurnTimes[j] != data.burnTime)
                    {
                        icrafting.sendProgressBarUpdate(this, j * 3 + 1, data.burnTime);
                    }

                    if (lastCurrentBurnTimes[j] != data.currentBurnTime)
                    {
                        icrafting.sendProgressBarUpdate(this, j * 3 + 2, data.currentBurnTime);
                    }

                    lastCookTimes[j] = data.cookTime;
                    lastBurnTimes[j] = data.burnTime;
                    lastCurrentBurnTimes[j] = data.currentBurnTime;
                }
            }
        }
    }


    @Override
    public void updateProgressBar(int id, int value)
    {
        super.updateProgressBar(id, value);

        if (isTile)
        {
            int moduleNr = id / 3;
            id = id % 3;

            TileEntityInventory tile = (TileEntityInventory) slotInv;
            TileEntityInventory.FurnaceData data = tile.getFurnaceData(tile.getContainer().furnaceModules.list.get(moduleNr));

            if (id == 0)
            {
                data.cookTime = value;
            } else if (id == 1)
            {
                data.burnTime = value;
            } else if (id == 2)
            {
                data.currentBurnTime = value;
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            boolean ruleApplied = false;
            for (ShiftClickRule rule : container.shiftClickRules.list)
            {
                if (rule.fuelOnly && !gui.getPack().fuelHandler.isItemFuel(itemstack1, rule.fuelList))
                    continue;
                if (rule.furnaceInputOnly && gui.getPack().smeltingRecipeHandler.getSmeltingResult(itemstack1, rule.recipeList) == null)
                    continue;

                int start = rule.fromStart;
                int end = rule.fromEnd;
                if (rule.fromInv)
                {
                    start += slotInv.getSizeInventory();
                    end += slotInv.getSizeInventory();
                }

                if (slotIndex >= start && slotIndex <= end)
                {
                    start = rule.toStart;
                    end = rule.toEnd;
                    if (rule.toInv)
                    {
                        start += slotInv.getSizeInventory();
                        end += slotInv.getSizeInventory();
                    }

                    ruleApplied = true;
                    boolean reverse = start > end;
                    if (!mergeItemStack(itemstack1, reverse ? end : start, reverse ? start + 1 : end + 1, reverse))
                    {
                        return null;
                    }
                    break;
                }
            }

            if (!ruleApplied)
            {
                return null;
            }

            /*if (slotIndex < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
            {
                return null;
            }*/

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        slotInv.markDirty();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return slotInv.isUseableByPlayer(player);
    }
}
