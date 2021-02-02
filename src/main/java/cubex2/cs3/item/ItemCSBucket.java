package cubex2.cs3.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.item.attributes.BucketAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemCSBucket extends ItemCS
{
    private BucketAttributes container;

    public ItemCSBucket(WrappedItem item)
    {
        super(item);

        container = (BucketAttributes) item.container;
    }

    @Override
    public void postInit()
    {
        if (container.fluid != null)
            FluidContainerRegistry.registerFluidContainer(((BlockFluidBase) container.fluid).getFluid(), new ItemStack(this), FluidContainerRegistry.EMPTY_BUCKET);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        super.onItemRightClick(stack, world, player);

        float var4 = 1.0F;
        double var5 = player.prevPosX + (player.posX - player.prevPosX) * var4;
        double var7 = player.prevPosY + (player.posY - player.prevPosY) * var4 + 1.62D - player.yOffset;
        double var9 = player.prevPosZ + (player.posZ - player.prevPosZ) * var4;
        MovingObjectPosition pos = this.getMovingObjectPositionFromPlayer(world, player, false);
        if (pos == null)
            return stack;
        else if (pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int x = pos.blockX;
            int y = pos.blockY;
            int z = pos.blockZ;

            if (!world.canMineBlock(player, x, y, z))
                return stack;

            if (pos.sideHit == 0)
            {
                --y;
            }

            if (pos.sideHit == 1)
            {
                ++y;
            }

            if (pos.sideHit == 2)
            {
                --z;
            }

            if (pos.sideHit == 3)
            {
                ++z;
            }

            if (pos.sideHit == 4)
            {
                --x;
            }

            if (pos.sideHit == 5)
            {
                ++x;
            }

            if (!player.canPlayerEdit(x, y, z, pos.sideHit, stack))
                return stack;

            if (this.placeFluid(world, var5, var7, var9, x, y, z) && !player.capabilities.isCreativeMode)
                return new ItemStack(Items.bucket);
        }

        return stack;
    }

    public boolean placeFluid(World world, double par2, double par4, double par6, int x, int y, int z)
    {
        if (!world.isAirBlock(x, y, z) && world.getBlock(x, y, z).getMaterial().isSolid())
            return false;
        else
        {
            if (world.provider.isHellWorld /*
                                            * this.isFull == Block.waterMoving.blockID
                                            *//* TODO allow in nether */)
            {
                world.playSoundEffect(par2 + 0.5D, par4 + 0.5D, par6 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                for (int var11 = 0; var11 < 8; ++var11)
                {
                    world.spawnParticle("largesmoke", x + Math.random(), y + Math.random(), z + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            } else
            {
                world.setBlock(x, y, z, container.fluid, 0, 3);
            }

            return true;
        }
    }
}
