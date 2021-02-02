package cubex2.cs3.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.item.attributes.FoodAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCSFood extends ItemCS
{
    private FoodAttributes container;

    public ItemCSFood(WrappedItem item)
    {
        super(item);
        container = (FoodAttributes) item.container;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (player.canEat(container.alwaysEdible))
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        --stack.stackSize;
        player.getFoodStats().addStats(container.hunger, container.saturation);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);
        return super.onEaten(stack, world, player);
    }

    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (container.potion != null && !world.isRemote && container.potion.id > 0 && world.rand.nextFloat() < container.potionProbability)
        {
            player.addPotionEffect(new PotionEffect(container.potion.id, container.potionDuration * 20, container.potionAmplifier));
        }
    }
}
