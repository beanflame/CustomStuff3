package cubex2.cs3.block;

import cubex2.cs3.block.attributes.ButtonAttributes;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.*;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;

public class BlockCSButton extends BlockCS
{
    private ButtonAttributes container;

    public BlockCSButton(WrappedBlock block)
    {
        super(block);
        container = (ButtonAttributes) block.container;
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
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        return dir == NORTH && world.isSideSolid(x, y, z + 1, NORTH) || dir == SOUTH && world.isSideSolid(x, y, z - 1, SOUTH) || dir == WEST && world.isSideSolid(x + 1, y, z, WEST) || dir == EAST && world.isSideSolid(x - 1, y, z, EAST);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isSideSolid(x - 1, y, z, EAST) || world.isSideSolid(x + 1, y, z, WEST) || world.isSideSolid(x, y, z - 1, SOUTH) || world.isSideSolid(x, y, z + 1, NORTH);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, metadata);
        int var10 = world.getBlockMetadata(x, y, z);
        int var11 = var10 & 8;
        var10 &= 7;

        ForgeDirection dir = ForgeDirection.getOrientation(facing);

        if (dir == NORTH && world.isSideSolid(x, y, z + 1, NORTH))
        {
            var10 = 4;
        } else if (dir == SOUTH && world.isSideSolid(x, y, z - 1, SOUTH))
        {
            var10 = 3;
        } else if (dir == WEST && world.isSideSolid(x + 1, y, z, WEST))
        {
            var10 = 2;
        } else if (dir == EAST && world.isSideSolid(x - 1, y, z, EAST))
        {
            var10 = 1;
        } else
        {
            var10 = this.getOrientation(world, x, y, z);
        }

        return var10 + var11;
    }

    private int getOrientation(World world, int x, int y, int z)
    {
        if (world.isSideSolid(x - 1, y, z, EAST))
            return 1;
        if (world.isSideSolid(x + 1, y, z, WEST))
            return 2;
        if (world.isSideSolid(x, y, z - 1, SOUTH))
            return 3;
        if (world.isSideSolid(x, y, z + 1, NORTH))
            return 4;
        return 1;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        if (this.redundantCanPlaceBlockAt(world, x, y, z))
        {
            int metadata = world.getBlockMetadata(x, y, z) & 7;
            boolean dropButton = false;

            if (!world.isSideSolid(x - 1, y, z, EAST) && metadata == 1)
            {
                dropButton = true;
            }

            if (!world.isSideSolid(x + 1, y, z, WEST) && metadata == 2)
            {
                dropButton = true;
            }

            if (!world.isSideSolid(x, y, z - 1, SOUTH) && metadata == 3)
            {
                dropButton = true;
            }

            if (!world.isSideSolid(x, y, z + 1, NORTH) && metadata == 4)
            {
                dropButton = true;
            }

            if (dropButton)
            {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
            }
        }
    }

    private boolean redundantCanPlaceBlockAt(World world, int x, int y, int z)
    {
        if (!this.canPlaceBlockAt(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
            return false;
        } else
            return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        this.func_82534_e(metadata);
    }

    private void func_82534_e(int metadata)
    {
        int btnDirection = metadata & 7;
        boolean var3 = (metadata & 8) > 0;
        float minY = 0.375F;
        float maxY = 0.625F;
        float var6 = 0.1875F;
        float var7 = 0.125F;

        if (var3)
        {
            var7 = 0.0625F;
        }

        if (btnDirection == 1)
        {
            this.setBlockBounds(0.0F, minY, 0.5F - var6, var7, maxY, 0.5F + var6);
        } else if (btnDirection == 2)
        {
            this.setBlockBounds(1.0F - var7, minY, 0.5F - var6, 1.0F, maxY, 0.5F + var6);
        } else if (btnDirection == 3)
        {
            this.setBlockBounds(0.5F - var6, minY, 0.0F, 0.5F + var6, maxY, var7);
        } else if (btnDirection == 4)
        {
            this.setBlockBounds(0.5F - var6, minY, 1.0F - var7, 0.5F + var6, maxY, 1.0F);
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
        int metadata = world.getBlockMetadata(x, y, z);
        int btnDirection = metadata & 7;
        int var12 = 8 - (metadata & 8);

        if (var12 == 0)
            return true;
        else
        {
            world.setBlockMetadataWithNotify(x, y, z, btnDirection + var12, 3);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
            this.notifyNeighbor(world, x, y, z, btnDirection);
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
            return true;
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        if ((meta & 8) > 0)
        {
            int btnDirection = meta & 7;
            this.notifyNeighbor(world, x, y, z, btnDirection);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
    {
        return (world.getBlockMetadata(x, y, z) & 8) > 0 ? 15 : 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if ((metadata & 8) == 0)
            return 0;
        else
        {
            int btnDirection = metadata & 7;
            return btnDirection == 5 && side == 1 ? 15 : btnDirection == 4 && side == 2 ? 15 : btnDirection == 3 && side == 3 ? 15 : btnDirection == 2 && side == 4 ? 15 : btnDirection == 1 && side == 5 ? 15 : 0;
        }
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        super.updateTick(world, x, y, z, random);
        if (!world.isRemote)
        {
            int metadata = world.getBlockMetadata(x, y, z);

            if ((metadata & 8) != 0)
            {
                if (container.isSensible)
                {
                    this.func_82535_o(world, x, y, z);
                } else
                {
                    world.setBlockMetadataWithNotify(x, y, z, metadata & 7, 3);
                    int btnDirection = metadata & 7;
                    this.notifyNeighbor(world, x, y, z, btnDirection);
                    world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.5F);
                    world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
                }
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (!world.isRemote)
        {
            if (container.isSensible)
            {
                if ((world.getBlockMetadata(x, y, z) & 8) == 0)
                {
                    this.func_82535_o(world, x, y, z);
                }
            }
        }
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        float i = 0.1875F;
        float j = 0.125F;
        float k = 0.125F;
        this.setBlockBounds(0.5F - i, 0.5F - j, 0.5F - k, 0.5F + i, 0.5F + j, 0.5F + k);
    }

    private void func_82535_o(World world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int btnDirection = metadata & 7;
        boolean var7 = (metadata & 8) != 0;
        this.func_82534_e(metadata);
        List list = world.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ));
        boolean arrowTriggersButton = !list.isEmpty();

        if (arrowTriggersButton && !var7)
        {
            world.setBlockMetadataWithNotify(x, y, z, btnDirection | 8, 3);
            this.notifyNeighbor(world, x, y, z, btnDirection);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!arrowTriggersButton && var7)
        {
            world.setBlockMetadataWithNotify(x, y, z, btnDirection, 3);
            this.notifyNeighbor(world, x, y, z, btnDirection);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (arrowTriggersButton)
        {
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
        }
    }

    private void notifyNeighbor(World world, int x, int y, int z, int side)
    {
        world.notifyBlocksOfNeighborChange(x, y, z, this);

        if (side == 1)
        {
            world.notifyBlocksOfNeighborChange(x - 1, y, z, this);
        } else if (side == 2)
        {
            world.notifyBlocksOfNeighborChange(x + 1, y, z, this);
        } else if (side == 3)
        {
            world.notifyBlocksOfNeighborChange(x, y, z - 1, this);
        } else if (side == 4)
        {
            world.notifyBlocksOfNeighborChange(x, y, z + 1, this);
        } else
        {
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
        }
    }
}
