package cubex2.cs3.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.util.SimulatedWorld;
import cubex2.cs3.worldgen.attributes.WorldGenOreAttributes;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldGenCSOre extends WorldGenCS
{
    private WorldGenOreAttributes worldGen;

    public WorldGenCSOre(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen);
        this.worldGen = (WorldGenOreAttributes) wrappedWorldGen.container;
    }

    @Override
    public void doGenerate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int generations = MathHelper.floor_float(worldGen.generationsPerChunk);
        for (int i = 0; i < generations; i++)
        {
            int x = chunkX * 16 + random.nextInt(16);
            int y = random.nextInt(worldGen.maxHeight + 1 - worldGen.minHeight) + worldGen.minHeight;
            int z = chunkZ * 16 + random.nextInt(16);
            if (shouldGenerate(x, z, world))
            {
                generateAt(random, world, x, y, z);
            }
        }
        if (random.nextFloat() <= worldGen.generationsPerChunk - generations)
        {
            int x = chunkX * 16 + random.nextInt(16);
            int y = random.nextInt(worldGen.maxHeight + 1 - worldGen.minHeight) + worldGen.minHeight;
            int z = chunkZ * 16 + random.nextInt(16);
            if (shouldGenerate(x, z, world))
            {
                generateAt(random, world, x, y, z);
            }
        }
    }


    public void generateInSimWorld(SimulatedWorld world, Random random)
    {
        int x = 0, y = 0, z = 0;
        Block block = Block.getBlockFromItem(container.generatedBlock.getItem());
        int meta = container.generatedBlock.getItemDamage();

        float angle = random.nextFloat() * (float) Math.PI;
        double d1 = x   + MathHelper.sin(angle) * worldGen.amount / 8.0F;
        double d2 = x   - MathHelper.sin(angle) * worldGen.amount  / 8.0F;
        double d3 = z   + MathHelper.cos(angle) * worldGen.amount / 8.0F;
        double d4 = z   - MathHelper.cos(angle) * worldGen.amount / 8.0F;
        double d5 = y + random.nextInt(3) - 2;
        double d6 = y + random.nextInt(3) - 2;

        int generatedBlocks = 0;
        for (int i = 0; i <= worldGen.amount; ++i)
        {
            double d7 = d1 + (d2 - d1) * i / worldGen.amount;
            double d8 = d5 + (d6 - d5) * i / worldGen.amount;
            double d9 = d3 + (d4 - d3) * i / worldGen.amount;
            double d10 = random.nextDouble() * worldGen.amount / 16.0D;
            double d11 = (MathHelper.sin(i * (float) Math.PI / worldGen.amount) + 1.0F) * d10 + 1.0D;
            double d12 = (MathHelper.sin(i * (float) Math.PI / worldGen.amount) + 1.0F) * d10 + 1.0D;
            int minGenX = MathHelper.floor_double(d7 - d11 / 2.0D);
            int minGenY = MathHelper.floor_double(d8 - d12 / 2.0D);
            int minGenZ = MathHelper.floor_double(d9 - d11 / 2.0D);
            int maxGenX = MathHelper.floor_double(d7 + d11 / 2.0D);
            int maxGenY = MathHelper.floor_double(d8 + d12 / 2.0D);
            int maxGenZ = MathHelper.floor_double(d9 + d11 / 2.0D);

            for (int genX = minGenX; genX <= maxGenX; ++genX)
            {
                double var39 = (genX + 0.5D - d7) / (d11 / 2.0D);

                if (var39 * var39 < 1.0D)
                {
                    for (int genY = minGenY; genY <= maxGenY; ++genY)
                    {
                        double var42 = (genY + 0.5D - d8) / (d12 / 2.0D);

                        if (var39 * var39 + var42 * var42 < 1.0D)
                        {
                            for (int genZ = minGenZ; genZ <= maxGenZ; ++genZ)
                            {
                                double var45 = (genZ + 0.5D - d9) / (d11 / 2.0D);
                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D)
                                {
                                    if (generatedBlocks++ < worldGen.amount)
                                    {
                                        world.setBlock(block, genX, genY, genZ);
                                        world.setMetadata(meta, genX, genY, genZ);
                                    } else
                                        return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateAt(Random random, World world, int x, int y, int z)
    {
        Block block = Block.getBlockFromItem(container.generatedBlock.getItem());
        int meta = container.generatedBlock.getItemDamage();

        float angle = random.nextFloat() * (float) Math.PI;
        double d1 = x + 8 + MathHelper.sin(angle) * worldGen.amount / 8.0F;
        double d2 = x + 8 - MathHelper.sin(angle) * worldGen.amount / 8.0F;
        double d3 = z + 8 + MathHelper.cos(angle) * worldGen.amount / 8.0F;
        double d4 = z + 8 - MathHelper.cos(angle) * worldGen.amount / 8.0F;
        double d5 = y + random.nextInt(3) - 2;
        double d6 = y + random.nextInt(3) - 2;

        int generatedBlocks = 0;
        for (int i = 0; i <= worldGen.amount; ++i)
        {
            double d7 = d1 + (d2 - d1) * i / worldGen.amount;
            double d8 = d5 + (d6 - d5) * i / worldGen.amount;
            double d9 = d3 + (d4 - d3) * i / worldGen.amount;
            double d10 = random.nextDouble() * worldGen.amount / 16.0D;
            double d11 = (MathHelper.sin(i * (float) Math.PI / worldGen.amount) + 1.0F) * d10 + 1.0D;
            double d12 = (MathHelper.sin(i * (float) Math.PI / worldGen.amount) + 1.0F) * d10 + 1.0D;
            int minGenX = MathHelper.floor_double(d7 - d11 / 2.0D);
            int minGenY = MathHelper.floor_double(d8 - d12 / 2.0D);
            int minGenZ = MathHelper.floor_double(d9 - d11 / 2.0D);
            int maxGenX = MathHelper.floor_double(d7 + d11 / 2.0D);
            int maxGenY = MathHelper.floor_double(d8 + d12 / 2.0D);
            int maxGenZ = MathHelper.floor_double(d9 + d11 / 2.0D);

            for (int genX = minGenX; genX <= maxGenX; ++genX)
            {
                double var39 = (genX + 0.5D - d7) / (d11 / 2.0D);

                if (var39 * var39 < 1.0D)
                {
                    for (int genY = minGenY; genY <= maxGenY; ++genY)
                    {
                        double var42 = (genY + 0.5D - d8) / (d12 / 2.0D);

                        if (var39 * var39 + var42 * var42 < 1.0D)
                        {
                            for (int genZ = minGenZ; genZ <= maxGenZ; ++genZ)
                            {
                                double var45 = (genZ + 0.5D - d9) / (d11 / 2.0D);
                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && canReplaceBlock(world, genX, genY, genZ))
                                {
                                    if (generatedBlocks++ < worldGen.amount)
                                    {
                                        world.setBlock(genX, genY, genZ, block, meta, 2);
                                    } else
                                        return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean canReplaceBlock(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != null)
        {
            int meta = world.getBlockMetadata(x, y, z);
            return canReplaceBlock(world.provider.dimensionId, block, meta);
        }
        return false;
    }
}
