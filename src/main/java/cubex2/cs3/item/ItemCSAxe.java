package cubex2.cs3.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cubex2.cs3.common.WrappedItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemCSAxe extends ItemAxe
{
    private WrappedItem wrappedItem;

    public ItemCSAxe(WrappedItem item)
    {
        super(ToolMaterial.STONE);
        wrappedItem = item;
    }

    @Override
    public void setHarvestLevel(String toolClass, int level)
    {
        if (!toolClass.equals("noHarvest") && !toolClass.equals("all"))
        {
            super.setHarvestLevel(toolClass, level);
        }
    }

    @Override
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return wrappedItem.getItemStackLimit(stack);
    }

    @Override
    public boolean isFull3D()
    {
        return wrappedItem.isFull3D();
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return wrappedItem.hasEffect(stack);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return wrappedItem.getCreativeTab();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return wrappedItem.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return wrappedItem.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return wrappedItem.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target)
    {
        wrappedItem.useItemOnEntity(stack, player, target);
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living)
    {
        if ((double) block.getBlockHardness(world, x, y, z) != 0.0D)
        {
            stack.damageItem(1, living);
        }
        return wrappedItem.onBlockDestroyed(stack, world, block, x, y, z, living);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase living1, EntityLivingBase living2)
    {
        stack.damageItem(2, living2);
        return wrappedItem.hitEntity(stack, living1, living2);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return wrappedItem.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slotId, boolean isCurrentItem)
    {
        wrappedItem.onUpdate(stack, world, entity, slotId, isCurrentItem);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        wrappedItem.onCreated(stack, world, player);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int tickCount)
    {
        wrappedItem.onUsingTick(stack, player, tickCount);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int tickCount)
    {
        wrappedItem.onPlayerStoppedUsing(stack, world, player, tickCount);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player)
    {
        return wrappedItem.onDroppedByPlayer(stack, player);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player)
    {
        return wrappedItem.onBlockStartBreak(stack, x, y, z, player);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        return wrappedItem.onEaten(stack, world, player);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
        return wrappedItem.getDigSpeed(stack, block, meta);
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack)
    {
        return wrappedItem.canHarvestBlock(block);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return wrappedItem.getMaxItemUseDuration(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        wrappedItem.addInformation(stack, player, list, advanced);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return wrappedItem.getItemUseAction(stack);
    }

    @Override
    public int getMaxDamage()
    {
        return wrappedItem.getMaxDamage();
    }

    @Override
    public boolean isDamageable()
    {
        return wrappedItem.isDamageable();
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        return wrappedItem.getAttributeModifiers(HashMultimap.create(), field_111210_e);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return wrappedItem.hasContainerItem(stack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        return wrappedItem.getContainerItem(stack);
    }


    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return wrappedItem.doesContainerItemLeaveCraftingGrid(stack);
    }

    @Override
    public int getItemEnchantability()
    {
        return wrappedItem.getItemEnchantability();
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack material)
    {
        return wrappedItem.getIsRepairable(stack, material);
    }
}
