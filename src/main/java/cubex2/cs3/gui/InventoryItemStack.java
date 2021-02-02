package cubex2.cs3.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.util.InventoryBasic;
import cubex2.cs3.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryItemStack extends InventoryBasic
{
    private final ItemStack stack;
    private final EntityPlayer player;
    private final int slotId;

    public InventoryItemStack(WrappedGui gui, EntityPlayer player, int slotId)
    {
        super("invItemStack", false, gui.container.guiData.numSlots());
        this.stack = player.inventory.getStackInSlot(slotId);
        this.player = player;
        this.slotId = slotId;

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CS3_Inv_Items"))
            Util.readStacksFromNBT("CS3_Inv_Items", inventoryContents, stack.getTagCompound());
    }

    @Override
    public void markDirty()
    {
        super.markDirty();

        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        Util.writeStacksToNBT("CS3_Inv_Items", inventoryContents, stack.getTagCompound());

        player.inventory.setInventorySlotContents(slotId, stack);
    }
}
