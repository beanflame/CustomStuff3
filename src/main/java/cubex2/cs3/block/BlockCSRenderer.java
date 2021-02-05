package cubex2.cs3.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.attributes.FacingAttributes;
import cubex2.cs3.block.attributes.GravityAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import cubex2.cs3.tileentity.TileEntityCS;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;


// 豆焰写的

// extends BlockCSFacing

public class BlockCSRenderer extends BlockCS {

    protected double rotationDegree = 0;
    private RenderGlobal worldObj;


    // BlockCSFlat   BlockCSFlat      BlockCSFlatRenderer

    public BlockCSRenderer(WrappedBlock block)
    {
        super(block);
        setTickRandomly(true);

    }

    @Override
    public int getMetaForFlowerGen(int itemMeta)
    {
        return 1;
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return wrappedBlock.getIcon(0, meta);
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
        return RenderIds.flatRenderId;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        ForgeDirection direction = ForgeDirection.getOrientation(side);
        return super.canPlaceBlockOnSide(world, x, y, z, side) ? world.isSideSolid(x - direction.offsetX, y - direction.offsetY, z - direction.offsetZ, direction) : false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        this.checkFlowerChange(world, x, y, z);
    }


    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);
        this.checkFlowerChange(world, x, y, z);

        if(rotationDegree > 0) {
            this.rotationDegree += 11.25;
            if (this.rotationDegree >= 360.0)
            {
                this.rotationDegree -= 360.0;
            }

            //信标

            //Matrix4f matrix = new Matrix4f();
            //matrix.rotY(((TileEntityCS) ).getRotation());
            //transform = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(matrix)).compose(transform);
        }
    }


    protected final void checkFlowerChange(World world, int x, int y, int z)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }


    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) & 7;
        return canBlockStay(world, x, y, z, meta);
    }

    public boolean canBlockStay(World world, int x, int y, int z, int meta)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(meta).getOpposite();
        return world.isSideSolid(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) & 7;
        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 1.0f;
        float maxY = 1.0f;
        float maxZ = 1.0f;
        if (meta == 0)
        {
            minY = maxY - 0.05f;
        } else if (meta == 1)
        {
            maxY = 0.05f;
        } else if (meta == 2)
        {
            minZ = maxZ - 0.05f;
        } else if (meta == 3)
        {
            maxZ = 0.05f;
        } else if (meta == 4)
        {
            minX = maxX - 0.05f;
        } else if (meta == 5)
        {
            maxX = 0.05f;
        }

        return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec)
    {
        int meta = world.getBlockMetadata(x, y, z) & 7;
        float minX = 0.0f;
        float minY = 0.0f;
        float minZ = 0.0f;
        float maxX = 1.0f;
        float maxY = 1.0f;
        float maxZ = 1.0f;
        if (meta == 0)
        {
            minY = maxY;
        } else if (meta == 1)
        {
            maxY = 0.0f;
        } else if (meta == 2)
        {
            minZ = maxZ - 0.05f;
        } else if (meta == 3)
        {
            maxZ = 0.05f;
        } else if (meta == 4)
        {
            minX = maxX - 0.05f;
        } else if (meta == 5)
        {
            maxX = 0.05f;
        }

        startVec = startVec.addVector(-x, -y, -z);
        endVec = endVec.addVector(-x, -y, -z);
        Vec3 vec32 = startVec.getIntermediateWithXValue(endVec, minX);
        Vec3 vec33 = startVec.getIntermediateWithXValue(endVec, maxX);
        Vec3 vec34 = startVec.getIntermediateWithYValue(endVec, minY);
        Vec3 vec35 = startVec.getIntermediateWithYValue(endVec, maxY);
        Vec3 vec36 = startVec.getIntermediateWithZValue(endVec, minZ);
        Vec3 vec37 = startVec.getIntermediateWithZValue(endVec, maxZ);

        if (!this.isVecInsideYZBounds(vec32, minY, maxY, minZ, maxZ))
        {
            vec32 = null;
        }

        if (!this.isVecInsideYZBounds(vec33, minY, maxY, minZ, maxZ))
        {
            vec33 = null;
        }

        if (!this.isVecInsideXZBounds(vec34, minX, maxX, minZ, maxZ))
        {
            vec34 = null;
        }

        if (!this.isVecInsideXZBounds(vec35, minX, maxX, minZ, maxZ))
        {
            vec35 = null;
        }

        if (!this.isVecInsideXYBounds(vec36, minX, maxX, minY, maxY))
        {
            vec36 = null;
        }

        if (!this.isVecInsideXYBounds(vec37, minX, maxX, minY, maxY))
        {
            vec37 = null;
        }

        Vec3 vec38 = null;

        if (vec32 != null && (vec38 == null || startVec.squareDistanceTo(vec32) < startVec.squareDistanceTo(vec38)))
        {
            vec38 = vec32;
        }

        if (vec33 != null && (vec38 == null || startVec.squareDistanceTo(vec33) < startVec.squareDistanceTo(vec38)))
        {
            vec38 = vec33;
        }

        if (vec34 != null && (vec38 == null || startVec.squareDistanceTo(vec34) < startVec.squareDistanceTo(vec38)))
        {
            vec38 = vec34;
        }

        if (vec35 != null && (vec38 == null || startVec.squareDistanceTo(vec35) < startVec.squareDistanceTo(vec38)))
        {
            vec38 = vec35;
        }

        if (vec36 != null && (vec38 == null || startVec.squareDistanceTo(vec36) < startVec.squareDistanceTo(vec38)))
        {
            vec38 = vec36;
        }

        if (vec37 != null && (vec38 == null || startVec.squareDistanceTo(vec37) < startVec.squareDistanceTo(vec38)))
        {
            vec38 = vec37;
        }

        if (vec38 == null)
            return null;
        else
        {
            byte b0 = -1;

            if (vec38 == vec32)
            {
                b0 = 4;
            }

            if (vec38 == vec33)
            {
                b0 = 5;
            }

            if (vec38 == vec34)
            {
                b0 = 0;
            }

            if (vec38 == vec35)
            {
                b0 = 1;
            }

            if (vec38 == vec36)
            {
                b0 = 2;
            }

            if (vec38 == vec37)
            {
                b0 = 3;
            }

            return new MovingObjectPosition(x, y, z, b0, vec38.addVector(x, y, z));
        }
    }

    private boolean isVecInsideYZBounds(Vec3 vec3, float minY, float maxY, float minZ, float maxZ)
    {
        return vec3 == null ? false : vec3.yCoord >= minY && vec3.yCoord <= maxY && vec3.zCoord >= minZ && vec3.zCoord <= maxZ;
    }

    private boolean isVecInsideXZBounds(Vec3 vec3, float minX, float maxX, float minZ, float maxZ)
    {
        return vec3 == null ? false : vec3.xCoord >= minX && vec3.xCoord <= maxX && vec3.zCoord >= minZ && vec3.zCoord <= maxZ;
    }

    private boolean isVecInsideXYBounds(Vec3 vec3, float minX, float maxX, float minY, float maxY)
    {
        return vec3 == null ? false : vec3.xCoord >= minX && vec3.xCoord <= maxX && vec3.yCoord >= minY && vec3.yCoord <= maxY;
    }
}
