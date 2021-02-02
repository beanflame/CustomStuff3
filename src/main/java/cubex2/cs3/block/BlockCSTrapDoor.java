package cubex2.cs3.block;

import cubex2.cs3.block.attributes.TrapDoorAttributes;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCSTrapDoor extends BlockCS
{
    private TrapDoorAttributes container;

    public BlockCSTrapDoor(WrappedBlock block)
    {
        super(block);
        container = (TrapDoorAttributes) block.container;

        disableStats();

        float f = 0.5F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
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
        return !isTrapdoorOpen(world.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType()
    {
        return 0;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        this.setBlockBoundsForBlockRender(world.getBlockMetadata(x, y, z));
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        float var1 = 0.1875F;
        this.setBlockBounds(0.0F, 0.5F - var1 / 2.0F, 0.0F, 1.0F, 0.5F + var1 / 2.0F, 1.0F);
    }

    public void setBlockBoundsForBlockRender(int md)
    {
        float var2 = 0.1875F;

        if ((md & 8) != 0)
        {
            this.setBlockBounds(0.0F, 1.0F - var2, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
        }

        if (isTrapdoorOpen(md))
        {
            if ((md & 3) == 0)
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }

            if ((md & 3) == 1)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }

            if ((md & 3) == 2)
            {
                this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if ((md & 3) == 3)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        super.onBlockClicked(world, x, y, z, player);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ)
    {
        super.onBlockActivated(world, x, y, z, player, facing, hitX, hitY, hitZ);
        switchState(world, x, y, z, player);
        return true;
    }

    public void switchState(World world, int x, int y, int z, EntityPlayer player)
    {
        if (!container.redstoneOnly)
        {
            int meta = world.getBlockMetadata(x, y, z);
            world.setBlockMetadataWithNotify(x, y, z, meta ^ 4, 2);
            world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
        }
    }

    public void onPoweredBlockChange(World world, int x, int y, int z, boolean indirectly)
    {
        int meta = world.getBlockMetadata(x, y, z);
        boolean var7 = (meta & 4) > 0;

        if (var7 != indirectly)
        {
            world.setBlockMetadataWithNotify(x, y, z, meta ^ 4, 2);
            world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(x, y, z);
            int var7 = x;
            int var8 = z;

            if ((meta & 3) == 0)
            {
                var8 = z + 1;
            }

            if ((meta & 3) == 1)
            {
                --var8;
            }

            if ((meta & 3) == 2)
            {
                var7 = x + 1;
            }

            if ((meta & 3) == 3)
            {
                --var7;
            }

            if (!(isValidSupportBlock(world.getBlock(var7, y, var8)) || world.isSideSolid(var7, y, var8, ForgeDirection.getOrientation((meta & 3) + 2))))
            {
                world.setBlockToAir(x, y, z);
                this.dropBlockAsItem(world, x, y, z, meta, 0);
            }

            boolean var9 = world.isBlockIndirectlyGettingPowered(x, y, z);

            if (var9 || neighborBlock.canProvidePower())
            {
                this.onPoweredBlockChange(world, x, y, z, var9);
            }
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 par5Vec3D, Vec3 par6Vec3D)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, par5Vec3D, par6Vec3D);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, metadata);
        int meta = 0;

        if (facing == 2)
        {
            meta = 0;
        }

        if (facing == 3)
        {
            meta = 1;
        }

        if (facing == 4)
        {
            meta = 2;
        }

        if (facing == 5)
        {
            meta = 3;
        }

        if (facing != 1 && facing != 0 && hitY > 0.5F)
        {
            meta |= 8;
        }

        return meta;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        if (side == 0)
            return false;
        else if (side == 1)
            return false;
        else
        {
            if (side == 2)
            {
                ++z;
            }

            if (side == 3)
            {
                --z;
            }

            if (side == 4)
            {
                ++x;
            }

            if (side == 5)
            {
                --x;
            }

            return isValidSupportBlock(world.getBlock(x, y, z)) || world.isSideSolid(x, y, z, ForgeDirection.UP);
        }
    }

    public static boolean isTrapdoorOpen(int md)
    {
        return (md & 4) != 0;
    }

    private static boolean isValidSupportBlock(Block block)
    {
        if (block == Blocks.air)
            return false;
        else
        {
            return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() || block == Blocks.glowstone;
        }
    }
}
