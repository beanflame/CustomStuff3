package cubex2.cs3.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.BlockCSStep;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.item.ItemCSBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCSStep extends ItemCSBlock
{
    private BlockCSStep block;

    public ItemCSStep(Block block, WrappedBlock wrappedBlock)
    {
        super(block, wrappedBlock);
        this.block = (BlockCSStep) block;
    }

    @Override
    public IIcon getIconFromDamage(int metadata)
    {
        return block.getIcon(2, metadata);
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return block.getUnlocalizedName() + itemstack.getItemDamage();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (stack.stackSize == 0)
            return false;
        else if (!player.canPlayerEdit(x, y, z, side, stack))
            return false;
        else
        {
            Block block1 = world.getBlock(x, y, z);
            int metadata = world.getBlockMetadata(x, y, z);
            int state = metadata & 7;
            boolean var11 = (metadata & 8) != 0;

            Block doubleSlabBlock = block.getDoubleSlabBlock(stack.getItemDamage());
            if ((side == 1 && !var11 || side == 0 && var11) && block1 == block && state == stack.getItemDamage())
            {
                if (doubleSlabBlock != null && world.checkNoEntityCollision(doubleSlabBlock.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, doubleSlabBlock, block.getDoubleSlabMeta(stack.getItemDamage()), 3))
                {
                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, doubleSlabBlock.stepSound.getBreakSound(), (doubleSlabBlock.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlabBlock.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                }

                return true;
            } else
                return func_50087_b(block, stack, player, world, x, y, z, side) ? true : super.onItemUse(stack, player, world, x, y, z, side, par8, par9, par10);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack)
    {
        int var8 = x;
        int var9 = y;
        int var10 = z;
        Block block1 = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        int var13 = meta & 7;
        boolean var14 = (meta & 8) != 0;

        if ((side == 1 && !var14 || side == 0 && var14) && block1 == block && var13 == stack.getItemDamage())
            return true;
        else
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }

            block1 = world.getBlock(x, y, z);
            meta = world.getBlockMetadata(x, y, z);
            var13 = meta & 7;
            return block1 == block && var13 == stack.getItemDamage() ? true : super.func_150936_a(world, var8, var9, var10, side, player, stack);
        }
    }

    private static boolean func_50087_b(BlockCSStep block, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side)
    {
        if (side == 0)
        {
            --y;
        }

        if (side == 1)
        {
            ++y;
        }

        if (side == 2)
        {
            --z;
        }

        if (side == 3)
        {
            ++z;
        }

        if (side == 4)
        {
            --x;
        }

        if (side == 5)
        {
            ++x;
        }
        Block block1 = world.getBlock(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        int state = metadata & 7;

        Block doubleSlabBlock = block.getDoubleSlabBlock(stack.getItemDamage());
        if (doubleSlabBlock != null && block1 == block && state == stack.getItemDamage())
        {
            if (world.checkNoEntityCollision(doubleSlabBlock.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, doubleSlabBlock, block.getDoubleSlabMeta(stack.getItemDamage()), 3))
            {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, doubleSlabBlock.stepSound.getBreakSound(), (doubleSlabBlock.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlabBlock.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        } else
            return false;
    }
}
