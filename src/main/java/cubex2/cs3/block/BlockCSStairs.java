package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCSStairs extends BlockCS
{
    private boolean flag = false;
    private int someInt = 0;
    private static final int[][] someArray = new int[][]{
            {2, 6},
            {3, 7},
            {2, 3},
            {6, 7},
            {0, 4},
            {1, 5},
            {0, 1},
            {4, 5}};

    public BlockCSStairs(WrappedBlock block)
    {
        super(block);
        useNeighborBrightness = true;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        if (flag)
        {
            this.setBlockBounds(0.5F * (someInt % 2), 0.5F * (someInt / 2 % 2), 0.5F * (someInt / 4 % 2), 0.5F + 0.5F * (someInt % 2), 0.5F + 0.5F * (someInt / 2 % 2), 0.5F + 0.5F * (someInt / 4 % 2));
        } else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
        return RenderIds.stairsRenderId;
    }

    public void func_82541_d(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if ((meta & 4) != 0)
        {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public static boolean isBlockStairs(Block block)
    {
        return block instanceof BlockCSStairs;
    }

    private boolean func_82540_f(IBlockAccess world, int x, int y, int z, int meta)
    {
        Block block = world.getBlock(x, y, z);
        return isBlockStairs(block) && world.getBlockMetadata(x, y, z) == meta;
    }

    public boolean func_82542_g(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        int var6 = meta & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((meta & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 1.0F;
        float var11 = 0.0F;
        float var12 = 0.5F;
        boolean var13 = true;
        Block var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var9 = 0.5F;
            var12 = 1.0F;

            var14 = world.getBlock(x + 1, y, z);
            var15 = world.getBlockMetadata(x + 1, y, z);

            if (isBlockStairs(var14) && (meta & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, x, y, z + 1, meta))
                {
                    var12 = 0.5F;
                    var13 = false;
                } else if (var16 == 2 && !this.func_82540_f(world, x, y, z - 1, meta))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        } else if (var6 == 1)
        {
            var10 = 0.5F;
            var12 = 1.0F;
            var14 = world.getBlock(x - 1, y, z);
            var15 = world.getBlockMetadata(x - 1, y, z);

            if (isBlockStairs(var14) && (meta & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, x, y, z + 1, meta))
                {
                    var12 = 0.5F;
                    var13 = false;
                } else if (var16 == 2 && !this.func_82540_f(world, x, y, z - 1, meta))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        } else if (var6 == 2)
        {
            var11 = 0.5F;
            var12 = 1.0F;
            var14 = world.getBlock(x, y, z + 1);
            var15 = world.getBlockMetadata(x, y, z + 1);

            if (isBlockStairs(var14) && (meta & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, x + 1, y, z, meta))
                {
                    var10 = 0.5F;
                    var13 = false;
                } else if (var16 == 0 && !this.func_82540_f(world, x - 1, y, z, meta))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        } else if (var6 == 3)
        {
            var14 = world.getBlock(x, y, z - 1);
            var15 = world.getBlockMetadata(x, y, z - 1);

            if (isBlockStairs(var14) && (meta & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, x + 1, y, z, meta))
                {
                    var10 = 0.5F;
                    var13 = false;
                } else if (var16 == 0 && !this.func_82540_f(world, x - 1, y, z, meta))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        }

        this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        return var13;
    }

    public boolean func_82544_h(IBlockAccess world, int x, int y, int z)
    {
        int var5 = world.getBlockMetadata(x, y, z);
        int var6 = var5 & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 0.5F;
        float var11 = 0.5F;
        float var12 = 1.0F;
        boolean var13 = false;
        Block var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var14 = world.getBlock(x - 1, y, z);
            var15 = world.getBlockMetadata(x - 1, y, z);

            if (isBlockStairs(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, x, y, z - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                } else if (var16 == 2 && !this.func_82540_f(world, x, y, z + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        } else if (var6 == 1)
        {
            var14 = world.getBlock(x + 1, y, z);
            var15 = world.getBlockMetadata(x + 1, y, z);

            if (isBlockStairs(var14) && (var5 & 4) == (var15 & 4))
            {
                var9 = 0.5F;
                var10 = 1.0F;
                var16 = var15 & 3;

                if (var16 == 3 && !this.func_82540_f(world, x, y, z - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                } else if (var16 == 2 && !this.func_82540_f(world, x, y, z + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        } else if (var6 == 2)
        {
            var14 = world.getBlock(x, y, z - 1);
            var15 = world.getBlockMetadata(x, y, z - 1);

            if (isBlockStairs(var14) && (var5 & 4) == (var15 & 4))
            {
                var11 = 0.0F;
                var12 = 0.5F;
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, x - 1, y, z, var5))
                {
                    var13 = true;
                } else if (var16 == 0 && !this.func_82540_f(world, x + 1, y, z, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        } else if (var6 == 3)
        {
            var14 = world.getBlock(x, y, z + 1);
            var15 = world.getBlockMetadata(x, y, z + 1);

            if (isBlockStairs(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.func_82540_f(world, x - 1, y, z, var5))
                {
                    var13 = true;
                } else if (var16 == 0 && !this.func_82540_f(world, x + 1, y, z, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        }

        if (var13)
        {
            this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        }

        return var13;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        this.func_82541_d(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        boolean var8 = this.func_82542_g(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

        if (var8 && this.func_82544_h(world, x, y, z))
        {
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        int var6 = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int var7 = world.getBlockMetadata(x, y, z) & 4;

        if (var6 == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2 | var7, 2);
        }

        if (var6 == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1 | var7, 2);
        }

        if (var6 == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3 | var7, 2);
        }

        if (var6 == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0 | var7, 2);
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int meta)
    {
        super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, meta);
        return facing != 0 && (facing == 1 || hitY <= 0.5D) ? meta : meta | 4;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec3, Vec3 vec3_1)
    {
        MovingObjectPosition[] mop = new MovingObjectPosition[8];
        int var8 = world.getBlockMetadata(x, y, z);
        int var9 = var8 & 3;
        boolean var10 = (var8 & 4) == 4;
        int[] var11 = someArray[var9 + (var10 ? 4 : 0)];
        flag = true;
        int var14;
        int var15;
        int var16;

        for (int var12 = 0; var12 < 8; ++var12)
        {
            someInt = var12;
            int[] var13 = var11;
            var14 = var11.length;

            for (var15 = 0; var15 < var14; ++var15)
            {
                var16 = var13[var15];

                if (var16 == var12)
                {
                    ;
                }
            }

            mop[var12] = super.collisionRayTrace(world, x, y, z, vec3, vec3_1);
        }

        int[] var21 = var11;
        int var24 = var11.length;

        for (var14 = 0; var14 < var24; ++var14)
        {
            var15 = var21[var14];
            mop[var15] = null;
        }

        MovingObjectPosition var23 = null;
        double var22 = 0.0D;
        MovingObjectPosition[] var25 = mop;
        var16 = mop.length;

        for (int var17 = 0; var17 < var16; ++var17)
        {
            MovingObjectPosition var18 = var25[var17];

            if (var18 != null)
            {
                double var19 = var18.hitVec.squareDistanceTo(vec3_1);

                if (var19 > var22)
                {
                    var23 = var18;
                    var22 = var19;
                }
            }
        }

        return var23;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & 4) != 0;
    }
}
