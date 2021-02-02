package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCSPane extends BlockCS
{
    public BlockCSPane(WrappedBlock block)
    {
        super(block);
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
    public int getRenderType()
    {
        return RenderIds.paneRenderId;
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return super.getIcon(0, meta);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        Block block = world.getBlock(x, y, z);
        return block != this && super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {

        if (!container.hasCollision)
            return;
        boolean var8 = this.canThisPaneConnectToThisBlockID(world.getBlock(x, y, z - 1));
        boolean var9 = this.canThisPaneConnectToThisBlockID(world.getBlock(x, y, z + 1));
        boolean var10 = this.canThisPaneConnectToThisBlockID(world.getBlock(x - 1, y, z));
        boolean var11 = this.canThisPaneConnectToThisBlockID(world.getBlock(x + 1, y, z));

        if ((!var10 || !var11) && (var10 || var11 || var8 || var9))
        {
            if (var10 && !var11)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
            } else if (!var10 && var11)
            {
                this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
            }
        } else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }

        if ((!var8 || !var9) && (var10 || var11 || var8 || var9))
        {
            if (var8 && !var9)
            {
                this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
            } else if (!var8 && var9)
            {
                this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
                super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
            }
        } else
        {
            this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        float var5 = 0.4375F;
        float var6 = 0.5625F;
        float var7 = 0.4375F;
        float var8 = 0.5625F;
        boolean var9 = this.canThisPaneConnectToThisBlockID(world.getBlock(x, y, z - 1));
        boolean var10 = this.canThisPaneConnectToThisBlockID(world.getBlock(x, y, z + 1));
        boolean var11 = this.canThisPaneConnectToThisBlockID(world.getBlock(x - 1, y, z));
        boolean var12 = this.canThisPaneConnectToThisBlockID(world.getBlock(x + 1, y, z));

        if ((!var11 || !var12) && (var11 || var12 || var9 || var10))
        {
            if (var11 && !var12)
            {
                var5 = 0.0F;
            } else if (!var11 && var12)
            {
                var6 = 1.0F;
            }
        } else
        {
            var5 = 0.0F;
            var6 = 1.0F;
        }

        if ((!var9 || !var10) && (var11 || var12 || var9 || var10))
        {
            if (var9 && !var10)
            {
                var7 = 0.0F;
            } else if (!var9 && var10)
            {
                var8 = 1.0F;
            }
        } else
        {
            var7 = 0.0F;
            var8 = 1.0F;
        }

        this.setBlockBounds(var5, 0.0F, var7, var6, 1.0F, var8);
    }

    public final boolean canThisPaneConnectToThisBlockID(Block block)
    {
        return block.func_149730_j() || block == this || block == Blocks.glass || block == Blocks.stained_glass || block == Blocks.stained_glass_pane;
    }
}
