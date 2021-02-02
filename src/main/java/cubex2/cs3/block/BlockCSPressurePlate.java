package cubex2.cs3.block;

import cubex2.cs3.block.attributes.PressurePlateAttributes;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCSPressurePlate extends BlockCS
{
    private PressurePlateAttributes container;

    public BlockCSPressurePlate(WrappedBlock block)
    {
        super(block);
        container = (PressurePlateAttributes) block.container;

        setTickRandomly(true);
        float f = 0.0625F;
        setBlockBounds(f, 0.0f, f, 1.0F - f, 0.03125F, 1.0f - f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || BlockCSFence.isAFence(world.getBlock(x, y - 1, z)) || world.getBlock(x, y - 1, z) instanceof BlockCSFence;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);

        boolean var6 = false;

        if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && !BlockCSFence.isAFence(world.getBlock(x, y - 1, z)) && !(world.getBlock(x, y - 1, z) instanceof BlockCSFence))
        {
            var6 = true;
        }

        if (var6)
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);

        if (!world.isRemote)
        {
            if (world.getBlockMetadata(x, y, z) != 0)
            {
                this.setStateIfMobInteractsWithPlate(world, x, y, z);
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        super.onEntityCollidedWithBlock(world, x, y, z, entity);

        if (!world.isRemote)
        {
            if (world.getBlockMetadata(x, y, z) != 1)
            {
                this.setStateIfMobInteractsWithPlate(world, x, y, z);
            }
        }
    }

    private void setStateIfMobInteractsWithPlate(World world, int x, int y, int z)
    {
        boolean metadata = world.getBlockMetadata(x, y, z) == 1;
        boolean shouldTrigger = false;
        float triggerDistance = 0.125F;
        List<?> list = new ArrayList();

        if (container.include != null && container.include.length > 0)
        {
            for (String include : container.include)
            {
                if (include.equals("all"))
                {
                    list.addAll(world.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getBoundingBox(x + triggerDistance, y, z + triggerDistance, x + 1 - triggerDistance, y + 0.25D, z + 1 - triggerDistance)));
                } else
                {
                    Class<?> entityClass = getEntityClass(include);

                    if (entityClass != null)
                    {
                        list.addAll(world.getEntitiesWithinAABB(entityClass, AxisAlignedBB.getBoundingBox(x + triggerDistance, y, z + triggerDistance, x + 1 - triggerDistance, y + 0.25D, z + 1 - triggerDistance)));
                    }
                }
            }

            if (container.exclude != null && container.exclude.length > 0)
            {
                List<?> list1 = new ArrayList(list);
                for (int i = 0; i < list1.size(); i++)
                {
                    Entity entity = (Entity) list1.get(i);
                    for (String exclude : container.exclude)
                    {
                        Class<?> entityClass = getEntityClass(exclude);

                        if (entityClass.isAssignableFrom(entity.getClass()))
                        {
                            list.remove(entity);
                        }
                    }
                }
            }
        }

        if (list.size() > 0)
        {
            shouldTrigger = true;
        }

        if (shouldTrigger && !metadata)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            world.notifyBlocksOfNeighborChange(x, y, z, this);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.playSoundEffect(x + 0.5D, y + 0.1D, z + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!shouldTrigger && metadata)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
            world.notifyBlocksOfNeighborChange(x, y, z, this);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.playSoundEffect(x + 0.5D, y + 0.1D, z + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (shouldTrigger)
        {
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
        }
    }

    private Class<?> getEntityClass(String s)
    {
        Class<?> entityClass = null;

        if (s.equals("mobs"))
        {
            entityClass = EntityLiving.class;
        } else if (s.equals("players"))
        {
            entityClass = EntityPlayer.class;
        } else if (s.equals("hostiles"))
        {
            entityClass = EntityMob.class;
        } else if (s.equals("animals"))
        {
            entityClass = EntityAnimal.class;
        } else if (s.equals("items"))
        {
            entityClass = EntityItem.class;
        } else if (s.matches("[0-9]+"))
        {
            entityClass = EntityList.getClassFromID(Integer.parseInt(s));
        } else
        {
            entityClass = (Class<?>) EntityList.stringToClassMapping.get(s);
        }

        return entityClass;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {

        if (meta > 0)
        {
            world.notifyBlocksOfNeighborChange(x, y, z, this);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        boolean triggered = world.getBlockMetadata(x, y, z) == 1;
        float f = 0.0625F;

        if (triggered)
        {
            this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
        } else
        {
            this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
        }
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int direction)
    {
        return world.getBlockMetadata(x, y, z) > 0 ? 15 : 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int direction)
    {
        return direction == 1 ? world.getBlockMetadata(x, y, z) > 0 ? 15 : 0 : 0;
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        float var1 = 0.5F;
        float var2 = 0.125F;
        float var3 = 0.5F;
        this.setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
    }

    @Override
    public int getMobilityFlag()
    {
        return 1;
    }
}
