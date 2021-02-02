package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.WorldDisplay;
import cubex2.cs3.util.SimulatedWorld;
import cubex2.cs3.worldgen.attributes.WorldGenOreAttributes;
import net.minecraft.init.Blocks;

import java.util.Random;

public class WindowEditAmount extends WindowEditWorldGenAttribute
{
    private static final int MINX = -20;
    private static final int MINY = -20;
    private static final int MINZ = -20;
    private static final int MAXX = 20;
    private static final int MAXY = 20;
    private static final int MAXZ = 20;

    private Random random = new Random();
    private NumericUpDown nupAmount;
    private WorldDisplay display;
    private SimulatedWorld world;

    private WorldGenOreAttributes container;
    private int tickCounter;

    private int prevAmount;

    public WindowEditAmount(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen, "amount", EDIT | CANCEL, 150, 150);
        container = (WorldGenOreAttributes) wrappedWorldGen.container;
        prevAmount = container.amount;

        world = new SimulatedWorld(MINX, MINY, MINZ, MAXX, MAXY, MAXZ);


        nupAmount = numericUpDown().at(7, 7).fillWidth(7).add();
        nupAmount.setMaxValue(100);
        nupAmount.setValue(container.amount);
        display = worldDisplay(world).below(nupAmount).right(7).bottom(btnCancel, 5).add();
        display.rotate = false;
        display.setLook(0.5f, 0.5f, 0.5f);
        display.setCam(7.0f, 7.0f, 7.0f);

        generateBlocks();
    }

    @Override
    protected void applyChanges()
    {
        container.amount = nupAmount.getValue();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        tickCounter++;
        if ((tickCounter % 20) == 0)
        {
            generateBlocks();
        }
    }

    private void generateBlocks()
    {
        world.clear();
        container.amount = nupAmount.getValue();
        wrappedWorldGen.worldGen.generateInSimWorld(world, random);
        container.amount = prevAmount;

        int minX = findMinX();
        if (minX < MINX) return;
        int maxX = findMaxX();
        int minY = findMinY();
        int maxY = findMaxY();
        int minZ = findMinZ();
        int maxZ = findMaxZ();

        float lookX = minX + (maxX - minX) / 2;
        float lookY = minY + (maxY - minY) / 2;
        float lookZ = minZ + (maxZ - minZ) / 2;

        display.setLook(lookX, lookY, lookZ);
        display.setCam(lookX + 7, lookY + 7, lookZ + 7);
    }

    private int findMinX()
    {
        for (int x = MINX; x <= MAXX; x++)
        {
            for (int y = MINY; y <= MAXY; y++)
            {
                for (int z = MINZ; z <= MAXZ; z++)
                {
                    if (world.getBlock(x, y, z) != Blocks.air)
                        return x;
                }
            }
        }

        return MINX - 1;
    }

    private int findMinY()
    {
        for (int y = MINY; y <= MAXY; y++)
        {
            for (int x = MINX; x <= MAXX; x++)
            {
                for (int z = MINZ; z <= MAXZ; z++)
                {
                    if (world.getBlock(x, y, z) != Blocks.air)
                        return y;
                }
            }
        }

        return -11;
    }

    private int findMinZ()
    {
        for (int z = MINZ; z <= MAXZ; z++)
        {
            for (int y = MINY; y <= MAXY; y++)
            {
                for (int x = MINX; x <= MAXX; x++)
                {
                    if (world.getBlock(x, y, z) != Blocks.air)
                        return z;
                }
            }
        }

        return -11;
    }

    private int findMaxX()
    {
        for (int x = MAXX; x >= MINX; x--)
        {
            for (int y = MINY; y <= MAXY; y++)
            {
                for (int z = MINZ; z <= MAXZ; z++)
                {
                    if (world.getBlock(x, y, z) != Blocks.air)
                        return x;
                }
            }
        }

        return -11;
    }

    private int findMaxY()
    {
        for (int y = MAXY; y >= MINY; y--)
        {
            for (int x = MINX; x <= MAXX; x++)
            {
                for (int z = MINZ; z <= MAXZ; z++)
                {
                    if (world.getBlock(x, y, z) != Blocks.air)
                        return y;
                }
            }
        }

        return -11;
    }

    private int findMaxZ()
    {
        for (int z = MAXZ; z >= MINZ; z--)
        {
            for (int y = MINY; y <= MAXY; y++)
            {
                for (int x = MINX; x <= MAXX; x++)
                {
                    if (world.getBlock(x, y, z) != Blocks.air)
                        return z;
                }
            }
        }

        return -11;
    }
}
