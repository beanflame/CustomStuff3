package cubex2.cs3.block;

import cubex2.cs3.block.attributes.StepAttributes;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCSStep extends BlockCS
{
    private StepAttributes container;

    public BlockCSStep(WrappedBlock block)
    {
        super(block);
        container = (StepAttributes) block.container;

        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        useNeighborBrightness = true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        boolean var5 = (world.getBlockMetadata(x, y, z) & 8) != 0;

        if (var5)
        {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return wrappedBlock.getIcon(side, meta);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, metadata);
        return facing != 0 && (facing == 1 || hitY <= 0.5D) ? metadata : metadata | 8;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        if (side != 1 && side != 0 && !wrappedBlock.shouldSideBeRenderedDefault(world, x, y, z, side))
            return false;
        else
        {
            int var6 = x + Facing.offsetsXForSide[Facing.oppositeSide[side]];
            int var7 = y + Facing.offsetsYForSide[Facing.oppositeSide[side]];
            int var8 = z + Facing.offsetsZForSide[Facing.oppositeSide[side]];
            boolean topHalf = (world.getBlockMetadata(var6, var7, var8) & 8) != 0;
            if (!topHalf)
                return side == 1 || (side == 0 && wrappedBlock.shouldSideBeRenderedDefault(world, x, y, z, side) || (world.getBlock(x, y, z) != this || (world.getBlockMetadata(x, y, z) & 8) != 0));
            else
                return side == 0 || (side == 1 && wrappedBlock.shouldSideBeRenderedDefault(world, x, y, z, side) || (world.getBlock(x, y, z) != this || (world.getBlockMetadata(x, y, z) & 8) == 0));
        }
    }

    public Block getDoubleSlabBlock(int meta)
    {
        return container.doubleSlabBlock;
    }

    public int getDoubleSlabMeta(int meta)
    {
        return container.doubleSlabMeta;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & 8) == 8 || isOpaqueCube();
    }
}
