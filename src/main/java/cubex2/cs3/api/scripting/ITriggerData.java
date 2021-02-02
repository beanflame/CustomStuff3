package cubex2.cs3.api.scripting;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITriggerData
{
    String getTriggerName();

    String getBlockName();

    TriggerType getTriggerType();

    World getWorld();

    Integer getPositionX();

    Integer getPositionY();

    Integer getPositionZ();

    EntityPlayer getPlayer();

    EntityPlayer getInteractPlayer();

    EntityLivingBase getLiving();

    EntityItem getItem();

    Entity getEntity();

    ItemStack getItemStack();

    Float getHitX();

    Float getHitY();

    Integer getSide();

    Integer getSlotId();

    Integer getTickCount();

    Boolean getIsCurrentItem();

    Boolean getRedstoneSignal();

    Integer getMouseX();

    Integer getMouseY();

    Integer getGuiX();

    Integer getGuiY();

    Integer getWidth();

    Integer getHeight();

    IInventory getCraftMatrix();
}
