package cubex2.cs3.block;

import cubex2.cs3.block.attributes.TorchAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.*;

public class BlockCSTorch extends BlockCS
{
    private TorchAttributes container;

    public BlockCSTorch(WrappedBlock block)
    {
        super(block);
        container = (TorchAttributes) block.container;

        setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
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
        return RenderIds.torchRenderId;
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return wrappedBlock.getIcon(0, 0);
    }

    private boolean canPlaceTorchOn(World world, int x, int y, int z)
    {
        if (!container.canPlaceOnFloor)
            return false;
        if (World.doesBlockHaveSolidTopSurface(world, x, y, z))
            return true;
        else
        {
            Block block = world.getBlock(x, y, z);

            if (block != Blocks.fence && block != Blocks.nether_brick_fence && block != Blocks.glass && !(block instanceof BlockCSFence))
            {
                if (block != null && (block instanceof BlockStairs || block instanceof BlockCSStairs))
                {
                    int metadata = world.getBlockMetadata(x, y, z);

                    if ((4 & metadata) != 0)
                        return true;
                }

                return false;
            } else
                return true;
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        boolean floor = canPlaceTorchOn(world, x, y - 1, z) && container.canPlaceOnFloor;
        boolean wall = (world.isSideSolid(x - 1, y, z, EAST)
                || world.isSideSolid(x + 1, y, z, WEST)
                || world.isSideSolid(x, y, z - 1, SOUTH)
                || world.isSideSolid(x, y, z + 1, NORTH)) && container.canPlaceOnWall;
        boolean ceiling = world.isSideSolid(x, y + 1, z, DOWN) && container.canPlaceOnCeiling;

        return floor || wall || ceiling;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, metadata);
        int meta = metadata;

        if (container.canPlaceOnCeiling && facing == 0 && world.isSideSolid(x, y + 1, z, DOWN))
        {
            meta = 6;
        }

        if (container.canPlaceOnFloor && facing == 1 && this.canPlaceTorchOn(world, x, y - 1, z))
        {
            meta = 5;
        }

        if (container.canPlaceOnWall)
        {
            if (facing == 2 && world.isSideSolid(x, y, z + 1, NORTH))
            {
                meta = 4;
            }

            if (facing == 3 && world.isSideSolid(x, y, z - 1, SOUTH))
            {
                meta = 3;
            }

            if (facing == 4 && world.isSideSolid(x + 1, y, z, WEST))
            {
                meta = 2;
            }

            if (facing == 5 && world.isSideSolid(x - 1, y, z, EAST))
            {
                meta = 1;
            }
        }
        return meta;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        super.updateTick(world, x, y, z, random);

        if (world.getBlockMetadata(x, y, z) == 0)
        {
            setMetadata(world, x, y, z);
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (world.getBlockMetadata(x, y, z) == 0)
        {
            setMetadata(world, x, y, z);
        }

        this.dropTorchIfCantStay(world, x, y, z);
    }

    private void setMetadata(World world, int x, int y, int z)
    {
        if (container.canPlaceOnWall)
            if (world.isSideSolid(x - 1, y, z, EAST))
            {
                world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            } else if (world.isSideSolid(x + 1, y, z, WEST))
            {
                world.setBlockMetadataWithNotify(x, y, z, 2, 2);
            } else if (world.isSideSolid(x, y, z - 1, SOUTH))
            {
                world.setBlockMetadataWithNotify(x, y, z, 3, 2);
            } else if (world.isSideSolid(x, y, z + 1, NORTH))
            {
                world.setBlockMetadataWithNotify(x, y, z, 4, 2);
            }

        if (container.canPlaceOnFloor && this.canPlaceTorchOn(world, x, y - 1, z))
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (container.canPlaceOnCeiling && world.isSideSolid(x, y + 1, z, DOWN))
        {
            world.setBlockMetadataWithNotify(x, y, z, 6, 2);
        }

        this.dropTorchIfCantStay(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);

        if (this.dropTorchIfCantStay(world, x, y, z))
        {
            int metadata = world.getBlockMetadata(x, y, z);
            boolean removeTorch = false;

            if ((!world.isSideSolid(x - 1, y, z, EAST) || !container.canPlaceOnWall) && metadata == 1)
            {
                removeTorch = true;
            }

            if ((!world.isSideSolid(x + 1, y, z, WEST) || !container.canPlaceOnWall) && metadata == 2)
            {
                removeTorch = true;
            }

            if ((!world.isSideSolid(x, y, z - 1, SOUTH) || !container.canPlaceOnWall) && metadata == 3)
            {
                removeTorch = true;
            }

            if ((!world.isSideSolid(x, y, z + 1, NORTH) || !container.canPlaceOnWall) && metadata == 4)
            {
                removeTorch = true;
            }

            if ((!this.canPlaceTorchOn(world, x, y - 1, z) || !container.canPlaceOnFloor) && metadata == 5)
            {
                removeTorch = true;
            }

            if ((!world.isSideSolid(x, y + 1, z, DOWN) || !container.canPlaceOnCeiling) && metadata == 6)
            {
                removeTorch = true;
            }

            if (removeTorch)
            {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
            }
        }
    }

    private boolean dropTorchIfCantStay(World world, int x, int y, int z)
    {
        if (!this.canPlaceBlockAt(world, x, y, z))
        {
            if (world.getBlock(x, y, z) == this)
            {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
            }

            return false;
        } else
            return true;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec1, Vec3 vec2)
    {
        int metadata = world.getBlockMetadata(x, y, z) & 7;
        float f = 0.15F;

        if (metadata == 1)
        {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else if (metadata == 2)
        {
            this.setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else if (metadata == 3)
        {
            this.setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else if (metadata == 4)
        {
            this.setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        } else if (metadata == 5)
        {
            f = 0.1F;
            this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        } else
        {
            f = 0.1F;
            this.setBlockBounds(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        }

        return super.collisionRayTrace(world, x, y, z, vec1, vec2);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if (container.particles)
        {
            int l = world.getBlockMetadata(x, y, z);
            double posX = (double) ((float) x + 0.5F);
            double posY = (double) ((float) y + 0.7F);
            double posY2 = (double) ((float) y + 0.4F);
            double posZ = (double) ((float) z + 0.5F);
            double d3 = 0.2199999988079071D;
            double d4 = 0.27000001072883606D;

            if (l == 1)
            {
                world.spawnParticle("smoke", posX - d4, posY + d3, posZ, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", posX - d4, posY + d3, posZ, 0.0D, 0.0D, 0.0D);
            } else if (l == 2)
            {
                world.spawnParticle("smoke", posX + d4, posY + d3, posZ, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", posX + d4, posY + d3, posZ, 0.0D, 0.0D, 0.0D);
            } else if (l == 3)
            {
                world.spawnParticle("smoke", posX, posY + d3, posZ - d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", posX, posY + d3, posZ - d4, 0.0D, 0.0D, 0.0D);
            } else if (l == 4)
            {
                world.spawnParticle("smoke", posX, posY + d3, posZ + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", posX, posY + d3, posZ + d4, 0.0D, 0.0D, 0.0D);
            } else if (l == 5)
            {
                world.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            } else
            {
                world.spawnParticle("smoke", posX, posY2, posZ, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", posX, posY2, posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
