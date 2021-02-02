package cubex2.cs3.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.lib.Biomes;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.SimulatedWorld;
import cubex2.cs3.worldgen.attributes.WorldGenAttributes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderHell;

import java.util.Random;

public abstract class WorldGenCS implements IWorldGenerator
{
    protected WorldGenAttributes container;
    protected WrappedWorldGen wrappedWorldGen;

    public WorldGenCS(WrappedWorldGen wrappedWorldGen)
    {
        this.wrappedWorldGen = wrappedWorldGen;
        container = wrappedWorldGen.container;
    }

    protected abstract void doGenerate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider);

    public abstract void generateInSimWorld(SimulatedWorld world, Random random);

    protected boolean canReplaceBlock(int dimension, Block block, int meta)
    {
        ItemStack stack = new ItemStack(block, 1, meta);
        if (dimension == 0)
        {
            for (ItemStack stack1 : container.overworldReplacedBlocks)
            {
                if (ItemStackHelper.itemStackEqual(stack, stack1))
                    return true;
            }
        } else if (dimension == -1)
        {
            for (ItemStack stack1 : container.netherReplacedBlocks)
            {
                if (ItemStackHelper.itemStackEqual(stack, stack1))
                    return true;
            }
        } else if (dimension == 1)
        {
            for (ItemStack stack1 : container.endReplacedBlocks)
            {
                if (ItemStackHelper.itemStackEqual(stack, stack1))
                    return true;
            }
        }
        return false;
    }

    protected boolean shouldGenerate(int x, int z, World world)
    {
        if (container.allowedBiomes[0].equals("all"))
            return true;
        else
        {
            BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
            for (String s : container.allowedBiomes)
            {
                if (Biomes.getBiome(s) == biome)
                    return true;
            }
        }
        return false;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (container.generatedBlock == null)
            return;

        if (chunkGenerator instanceof ChunkProviderGenerate && !container.generateInOverworld)
        {
            return;
        }
        if (chunkGenerator instanceof ChunkProviderHell && !container.generateInNether)
        {
            return;
        }
        if (chunkGenerator instanceof ChunkProviderEnd && !container.generateInEnd)
        {
            return;
        }

        doGenerate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }
}
