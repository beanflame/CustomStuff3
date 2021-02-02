package cubex2.cs3.worldgen;

import cubex2.cs3.block.BlockCS;
import cubex2.cs3.block.BlockCSFlat;
import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.util.SimulatedWorld;
import cubex2.cs3.worldgen.attributes.WorldGenFlowerAttributes;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldGenCSFlower extends WorldGenCS
{
    private WorldGenFlowerAttributes worldGen;

    public WorldGenCSFlower(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen);
        this.worldGen = (WorldGenFlowerAttributes) wrappedWorldGen.container;
    }

    @Override
    protected void doGenerate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int generations = MathHelper.floor_float(worldGen.generationsPerChunk);
        for (int i = 0; i < generations; i++)
        {
            int x = chunkX * 16 + random.nextInt(16) + 8;
            int y = random.nextInt(worldGen.maxHeight + 1 - worldGen.minHeight) + worldGen.minHeight;
            int z = chunkZ * 16 + random.nextInt(16) + 8;
            if (shouldGenerate(x, z, world))
            {
                generateAt(random, world, x, y, z);
            }
        }
        if (random.nextFloat() <= worldGen.generationsPerChunk - generations)
        {
            int x = chunkX * 16 + random.nextInt(16) + 8;
            int y = random.nextInt(worldGen.maxHeight + 1 - worldGen.minHeight) + worldGen.minHeight;
            int z = chunkZ * 16 + random.nextInt(16) + 8;
            if (shouldGenerate(x, z, world))
            {
                generateAt(random, world, x, y, z);
            }
        }
    }

    public void generateAt(Random random, World world, int x, int y, int z)
    {
        Block block = Block.getBlockFromItem(container.generatedBlock.getItem());
        int meta = container.generatedBlock.getItemDamage();
        if (block instanceof BlockCS)
        {
            meta = ((BlockCS) block).getMetaForFlowerGen(meta);
        }

        for (int i = 0; i < worldGen.blockRate; ++i)
        {
            int genX = x + random.nextInt(8) - random.nextInt(8);
            int genY = y + random.nextInt(4) - random.nextInt(4);
            int genZ = z + random.nextInt(8) - random.nextInt(8);


            if (world.isAirBlock(genX, genY, genZ))
            {
                boolean canStay = block instanceof BlockCSFlat ? ((BlockCSFlat) block).canBlockStay(world, genX, genY, genZ, meta) : block.canBlockStay(world, genX, genY, genZ);
                if (canStay && canPlaceBlock(world, genX, genY - 1, genZ))
                {
                    world.setBlock(genX, genY, genZ, block, meta, 2);
                }
            }
        }
    }

    private boolean canPlaceBlock(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != null)
        {
            int meta = world.getBlockMetadata(x, y, z);
            return canReplaceBlock(world.provider.dimensionId, block, meta);
        }
        return false;
    }

    @Override
    public void generateInSimWorld(SimulatedWorld world, Random random)
    {

    }
}
