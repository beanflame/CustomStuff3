package cubex2.cs3.block;

import cubex2.cs3.block.attributes.GravityAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.entity.EntityCSGravityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCSGravity extends BlockCS
{
    private GravityAttributes container;

    public BlockCSGravity(WrappedBlock block)
    {
        super(block);
        container = (GravityAttributes) block.container;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);
        if (!world.isRemote)
        {
            this.tryToApplyGravity(world, x, y, z);
        }
    }

    private void tryToApplyGravity(World world, int x, int y, int z)
    {
        int md = world.getBlockMetadata(x, y, z);
        if (canFallAt(world, x, y + (container.hasAntiGravity ? 1 : -1), z) && y >= 0 && y <= 255)
        {
            byte byte1 = 32;
            if (world.checkChunksExist(x - byte1, y - byte1, z - byte1, x + byte1, y + byte1, z + byte1))
            {
                if (!world.isRemote)
                {
                    EntityCSGravityBlock gravityBlock = new EntityCSGravityBlock(world, x + 0.5f, y + 0.5f, z + 0.5f, this, md);
                    world.spawnEntityInWorld(gravityBlock);
                }
            }
        }
    }

    public static boolean canFallAt(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);

        if (block.isAir(world, x, y, z))
            return true;
        else if (block == Blocks.fire)
            return true;
        else
        {
            Material material = block.getMaterial();
            return material == Material.water ? true : material == Material.lava;
        }
    }
}
