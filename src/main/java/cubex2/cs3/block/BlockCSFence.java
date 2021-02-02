package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCSFence extends BlockCS
{
    public BlockCSFence(WrappedBlock block)
    {
        super(block);
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.fenceRenderId;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        if (!container.hasCollision)
            return;

        boolean connectZN = this.canConnectFenceTo(world, x, y, z - 1);
        boolean connectZP = this.canConnectFenceTo(world, x, y, z + 1);
        boolean connectXN = this.canConnectFenceTo(world, x - 1, y, z);
        boolean connectXP = this.canConnectFenceTo(world, x + 1, y, z);
        float x1 = 0.375F;
        float x2 = 0.625F;
        float z1 = 0.375F;
        float z2 = 0.625F;

        if (connectZN)
        {
            z1 = 0.0F;
        }

        if (connectZP)
        {
            z2 = 1.0F;
        }

        if (connectZN || connectZP)
        {
            this.setBlockBounds(x1, 0.0F, z1, x2, 1.5F, z2);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }

        z1 = 0.375F;
        z2 = 0.625F;

        if (connectXN)
        {
            x1 = 0.0F;
        }

        if (connectXP)
        {
            x2 = 1.0F;
        }

        if (connectXN || connectXP || !connectZN && !connectZP)
        {
            this.setBlockBounds(x1, 0.0F, z1, x2, 1.5F, z2);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }

        if (connectZN)
        {
            z1 = 0.0F;
        }

        if (connectZP)
        {
            z2 = 1.0F;
        }

        this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
    }

   /* @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (!container.hasCollision)
            return null;

        boolean connectZN = this.canConnectFenceTo(world, x, y, z - 1);
        boolean connectZP = this.canConnectFenceTo(world, x, y, z + 1);
        boolean connectXN = this.canConnectFenceTo(world, x - 1, y, z);
        boolean connectXP = this.canConnectFenceTo(world, x + 1, y, z);
        float x1 = 0.375F;
        float x2 = 0.625F;
        float z1 = 0.375F;
        float z2 = 0.625F;

        if (connectZN)
        {
            z1 = 0.0F;
        }

        if (connectZP)
        {
            z2 = 1.0F;
        }

        if (connectXN)
        {
            x1 = 0.0F;
        }

        if (connectXP)
        {
            x2 = 1.0F;
        }

        return AxisAlignedBB.getBoundingBox(x + x1, y, z + z1, x + x2, y + 1.5F, z + z2);
    }*/

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        boolean connectZN = this.canConnectFenceTo(world, x, y, z - 1);
        boolean connectZP = this.canConnectFenceTo(world, x, y, z + 1);
        boolean connectXN = this.canConnectFenceTo(world, x - 1, y, z);
        boolean connectXP = this.canConnectFenceTo(world, x + 1, y, z);
        float x1 = 0.375F;
        float x2 = 0.625F;
        float z1 = 0.375F;
        float z2 = 0.625F;

        if (connectZN)
        {
            z1 = 0.0F;
        }

        if (connectZP)
        {
            z2 = 1.0F;
        }

        if (connectXN)
        {
            x1 = 0.0F;
        }

        if (connectXP)
        {
            x2 = 1.0F;
        }

        this.setBlockBounds(x1, 0.0F, z1, x2, 1.0F, z2);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
    {
        return true;
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
        return false;
    }

    // TODO add attribute for allowed connecting
    public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z)
    {
        Block base = world.getBlock(x, y, z);

        if (base != this && base != Blocks.fence_gate && !(base instanceof BlockFenceGate) /*&& !(base instanceof BlockCSFenceGate)*/)
            return base != null && base.getMaterial().isOpaque() && base.renderAsNormalBlock() ? base.getMaterial() != Material.gourd : false;
        else
            return true;
    }

    public static boolean isAFence(Block block)
    {
        return block != null && (block instanceof BlockCSFence || block instanceof BlockFence);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        //return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
        return true;
    }
}
