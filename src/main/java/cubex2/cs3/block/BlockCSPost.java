package cubex2.cs3.block;

import cubex2.cs3.block.attributes.PostAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCSPost extends BlockCSFacing
{
    protected PostAttributes container;

    public BlockCSPost(WrappedBlock block)
    {
        super(block);
        container = (PostAttributes) block.container;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) & 7;
        float thickness = container.thickness;

        if (meta == 0 || meta == 1)
        {
            this.setBlockBounds(0.5f - thickness / 2, 0.0f, 0.5f - thickness / 2, 0.5f + thickness / 2, 1.0f, 0.5f + thickness / 2);
        } else if (meta == 2 || meta == 3)
        {
            this.setBlockBounds(0.5f - thickness / 2, 0.5f - thickness / 2, 0.0f, 0.5f + thickness / 2, 0.5f + thickness / 2, 1.0f);
        } else if (meta == 4 || meta == 5)
        {
            this.setBlockBounds(0.0f, 0.5f - thickness / 2, 0.5f - thickness / 2, 1.0f, 0.5f + thickness / 2, 0.5f + thickness / 2);
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
    public int getRenderType()
    {
        return RenderIds.postRenderId;
    }

    public void setBlockBoundsForItemRender(int meta)
    {
        float thickness = container.thickness;
        this.setBlockBounds(0.5f - thickness / 2, 0.0f, 0.5f - thickness / 2, 0.5f + thickness / 2, 1.0f, 0.5f + thickness / 2);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) & 7;
        return meta == 0 || meta == 1;
    }

    public float getThickness()
    {
        return container.thickness;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return wrappedBlock.shouldSideBeRenderedDefault(world, x, y, z, side);
    }
}
