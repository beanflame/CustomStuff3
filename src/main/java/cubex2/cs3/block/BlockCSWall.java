package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCSWall extends BlockCS
{
    public BlockCSWall(WrappedBlock block)
    {
        super(block);
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.wallRenderId;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        boolean connectZN = this.canConnectWallTo(world, x, y, z - 1);
        boolean connectZP = this.canConnectWallTo(world, x, y, z + 1);
        boolean connectXN = this.canConnectWallTo(world, x - 1, y, z);
        boolean connectXP = this.canConnectWallTo(world, x + 1, y, z);
        float minX = 0.25F;
        float maxX = 0.75F;
        float minZ = 0.25F;
        float maxZ = 0.75F;
        float maxY = 1.0F;

        if (connectZN)
        {
            minZ = 0.0F;
        }

        if (connectZP)
        {
            maxZ = 1.0F;
        }

        if (connectXN)
        {
            minX = 0.0F;
        }

        if (connectXP)
        {
            maxX = 1.0F;
        }

        if (connectZN && connectZP && !connectXN && !connectXP)
        {
            maxY = 0.8125F;
            minX = 0.3125F;
            maxX = 0.6875F;
        } else if (!connectZN && !connectZP && connectXN && connectXP)
        {
            maxY = 0.8125F;
            minZ = 0.3125F;
            maxZ = 0.6875F;
        }

        this.setBlockBounds(minX, 0.0F, minZ, maxX, maxY, maxZ);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        maxY = 1.5D;
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    public boolean canConnectWallTo(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);

        if (block != this && block != Blocks.fence_gate)
        {
            if (block != null)
            {
                if (block instanceof BlockCSFenceGate || block instanceof BlockCSWall)
                    return true;
            }
            return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd;
        } else
            return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return side != 0 || super.shouldSideBeRendered(world, x, y, z, side);
    }
}
