package cubex2.cs3.block;

import cubex2.cs3.block.attributes.FenceGateAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCSFenceGate extends BlockCS
{
    private FenceGateAttributes container;

    public BlockCSFenceGate(WrappedBlock block)
    {
        super(block);
        container = (FenceGateAttributes) block.container;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.getBlock(x, y - 1, z).getMaterial().isSolid() && super.canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        if (!world.isRemote)
        {
            int metadata = world.getBlockMetadata(x, y, z);
            boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z);

            if (powered || neighborBlock != Blocks.air && neighborBlock.canProvidePower() || neighborBlock == Blocks.air)
                if (powered && !isFenceGateOpen(metadata))
                {
                    world.setBlockMetadataWithNotify(x, y, z, metadata | 4, 2);
                    world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
                } else if (!powered && isFenceGateOpen(metadata))
                {
                    world.setBlockMetadataWithNotify(x, y, z, metadata & -5, 2);
                    world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
                }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ)
    {
        super.onBlockActivated(world, x, y, z, player, facing, hitX, hitY, hitZ);
        if (container.redstoneOnly)
            return false;
        int metadata = world.getBlockMetadata(x, y, z);

        if (isFenceGateOpen(metadata))
        {
            world.setBlockMetadataWithNotify(x, y, z, metadata & -5, 2);
        } else
        {
            int playerDirection = (MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) % 4;
            int direction = getDirection(metadata);

            if (direction == (playerDirection + 2) % 4)
            {
                metadata = playerDirection;
            }

            world.setBlockMetadataWithNotify(x, y, z, metadata | 4, 2);
        }

        world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        int playerDirection = (MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) % 4;
        world.setBlockMetadataWithNotify(x, y, z, playerDirection, 2);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (!container.hasCollision)
            return null;
        int metadata = world.getBlockMetadata(x, y, z);
        return isFenceGateOpen(metadata) ? null : metadata != 2 && metadata != 0 ? AxisAlignedBB.getBoundingBox(x + 0.375F, y, z, x + 0.625F, y + 1.5F, z + 1) : AxisAlignedBB.getBoundingBox(x, y, z + 0.375F, x + 1, y + 1.5F, z + 0.625F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int direction = getDirection(world.getBlockMetadata(x, y, z));

        if (direction != 2 && direction != 0)
        {
            this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
        } else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
    {
        return isFenceGateOpen(world.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.fenceGateRenderId;
    }

    public static boolean isFenceGateOpen(int md)
    {
        return (md & 4) != 0;
    }

    public static int getDirection(int md)
    {
        return md & 3;
    }
}
