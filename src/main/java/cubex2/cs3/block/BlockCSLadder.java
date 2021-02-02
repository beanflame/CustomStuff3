package cubex2.cs3.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static net.minecraftforge.common.util.ForgeDirection.*;

public class BlockCSLadder extends BlockCS
{
    public BlockCSLadder(WrappedBlock block)
    {
        super(block);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        float f = 0.125F;
        if (meta == 2)
        {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }

        if (meta == 3)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }

        if (meta == 4)
        {
            this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (meta == 5)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
    }


    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        int meta = world.getBlockMetadata(x, y, z);
        boolean stay = false;

        if (meta == 2 && world.isSideSolid(x, y, z + 1, NORTH))
        {
            stay = true;
        }

        if (meta == 3 && world.isSideSolid(x, y, z - 1, SOUTH))
        {
            stay = true;
        }

        if (meta == 4 && world.isSideSolid(x + 1, y, z, WEST))
        {
            stay = true;
        }

        if (meta == 5 && world.isSideSolid(x - 1, y, z, EAST))
        {
            stay = true;
        }

        if (!stay)
        {
            this.dropBlockAsItem(world, x, y, z, meta, 0);
            world.setBlockToAir(x, y, z);
        }

        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, metadata);
        int meta = metadata;

        if ((meta == 0 || facing == 2) && world.isSideSolid(x, y, z + 1, NORTH))
        {
            meta = 2;
        }

        if ((meta == 0 || facing == 3) && world.isSideSolid(x, y, z - 1, SOUTH))
        {
            meta = 3;
        }

        if ((meta == 0 || facing == 4) && world.isSideSolid(x + 1, y, z, WEST))
        {
            meta = 4;
        }

        if ((meta == 0 || facing == 5) && world.isSideSolid(x - 1, y, z, EAST))
        {
            meta = 5;
        }
        return meta;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (!container.hasCollision)
            return null;
        this.setBlockBoundsBasedOnState(world, x, y, z);

        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
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
        return 8;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isSideSolid(x - 1, y, z, EAST) || world.isSideSolid(x + 1, y, z, WEST) || world.isSideSolid(x, y, z - 1, SOUTH) || world.isSideSolid(x, y, z + 1, NORTH);
    }

    /*@Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return canPlaceBlockAt(world, x, y, z);
    }*/

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
    {
        return true;
    }
}
