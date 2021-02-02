package cubex2.cs3.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.common.scripting.TriggerData;
import cubex2.cs3.item.attributes.ArmorAttributes;
import cubex2.cs3.util.JavaScriptHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemCSArmor extends ItemArmor
{
    private WrappedItem wrappedItem;
    private ArmorAttributes container;

    public ItemCSArmor(WrappedItem item, int type)
    {
        super(((ArmorAttributes) item.container).material, 0, type);
        wrappedItem = item;
        container = (ArmorAttributes) wrappedItem.container;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if (container.onArmorUpdate != null && container.onArmorUpdate.script != null)
        {
            ITriggerData data = new TriggerData("onArmorUpdate", TriggerType.ITEM, world, player, itemStack);
            JavaScriptHelper.executeTrigger(container.onArmorUpdate.script, data, wrappedItem.getPack());
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return container.texture;
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return false;
    }

    @Override
    public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
    {
        return 16777215;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return itemIcon;
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
        super.onItemRightClick(stack, world, player);
        if (stack.stackSize == 0)
            return stack;
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
        return wrappedItem.onBlockDestroyed(stack, world, block, x, y, z, living);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase living1, EntityLivingBase living2)
    {
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
    public boolean getIsRepairable(ItemStack stack, ItemStack material)
    {
        return wrappedItem.getIsRepairable(stack, material);
    }
}
