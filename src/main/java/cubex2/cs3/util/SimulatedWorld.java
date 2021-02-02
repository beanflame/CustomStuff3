package cubex2.cs3.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class SimulatedWorld implements IBlockAccess
{
    private Block[] blocks;
    private int[] meta;
    public final int minX, minY, minZ;
    public final int maxX, maxY, maxZ;
    private final int xSize, ySize, zSize;

    public SimulatedWorld(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        xSize = maxX - minX + 1;
        ySize = maxY - minY + 1;
        zSize = maxZ - minZ + 1;

        blocks = new Block[xSize * ySize * zSize];
        meta = new int[xSize * ySize * zSize];
    }

    @Override
    public Block getBlock(int x, int y, int z)
    {
        if (x < minX || y < minY || z < minZ || x > maxX || y > maxY || z > maxZ)
            return Blocks.air;

        x += Math.abs(Math.min(0, minX));
        y += Math.abs(Math.min(0, minY));
        z += Math.abs(Math.min(0, minZ));

        Block block = blocks[x + y * xSize + z * ySize * xSize];
        return block == null ? Blocks.air : block;
    }

    public void setBlock(Block block, int x, int y, int z)
    {
        if (x < minX || y < minY || z < minZ || x > maxX || y > maxY || z > maxZ)
            return;

        x += Math.abs(Math.min(0, minX));
        y += Math.abs(Math.min(0, minY));
        z += Math.abs(Math.min(0, minZ));

        blocks[x + y * xSize + z * ySize * xSize] = block;
    }

    @Override
    public TileEntity getTileEntity(int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getLightBrightnessForSkyBlocks(int x, int y, int z, int side)
    {
        return 152;
    }

    @Override
    public int getBlockMetadata(int x, int y, int z)
    {
        if (x < minX || y < minY || z < minZ || x > maxX || y > maxY || z > maxZ)
            return 0;

        x += Math.abs(Math.min(0, minX));
        y += Math.abs(Math.min(0, minY));
        z += Math.abs(Math.min(0, minZ));

        return meta[x + y * xSize + z * ySize * xSize];
    }

    public void setMetadata(int metadata, int x, int y, int z)
    {
        if (x < minX || y < minY || z < minZ || x > maxX || y > maxY || z > maxZ)
            return;

        x += Math.abs(Math.min(0, minX));
        y += Math.abs(Math.min(0, minY));
        z += Math.abs(Math.min(0, minZ));

        meta[x + y * xSize + z * ySize * xSize] = metadata;
    }

    @Override
    public int isBlockProvidingPowerTo(int x, int y, int z, int side)
    {
        return 0;
    }

    @Override
    public boolean isAirBlock(int x, int y, int z)
    {
        return getBlock(x, y, z) == Blocks.air;
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int y)
    {
        return BiomeGenBase.plains;
    }

    @Override
    public int getHeight()
    {
        return ySize;
    }

    @Override
    public boolean extendedLevelsInChunkCache()
    {
        return false;
    }

    @Override
    public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default)
    {
        if (x < minX || y < minY || z < minZ || x > maxX || y > maxY || z > maxZ)
            return _default;

        return getBlock(x, y, z).isSideSolid(this, x, y, z, side);
    }

    public void clear()
    {
        for (int i = 0; i < blocks.length; i++)
        {
            blocks[i] = null;
            meta[i] = 0;
        }
    }
}
