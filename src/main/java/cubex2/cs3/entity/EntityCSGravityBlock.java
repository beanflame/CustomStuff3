package cubex2.cs3.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.BlockCSGravity;
import cubex2.cs3.block.attributes.GravityAttributes;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCSGravityBlock extends Entity implements IEntityAdditionalSpawnData
{
    public int meta;
    private BlockCSGravity block;

    public BlockCSGravity getBlock()
    {
        return block;
    }

    private boolean isAntiGravity;

    /**
     * How long the block has been falling for.
     */
    public int fallTime = 0;
    public boolean shouldDropItem;

    public EntityCSGravityBlock(World world)
    {
        super(world);
        fallTime = 0;
        shouldDropItem = true;
    }

    public EntityCSGravityBlock(World world, double x, double y, double z, BlockCSGravity block, int meta)
    {
        super(world);
        this.block = block;
        this.meta = meta;
        isAntiGravity = ((GravityAttributes) block.getWrappedBlock().container).hasAntiGravity;
        preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        this.setPosition(x, y, z);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
        shouldDropItem = true;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    @Override
    public void onUpdate()
    {
        if (worldObj.isRemote) System.out.println(posY);
        if (block.getMaterial() == Material.air)
        {
            setDead();
        } else
        {
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;
            ++fallTime;
            motionY -= 0.04D * (((GravityAttributes) block.getWrappedBlock().container).hasAntiGravity ? -1 : 1);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.98D;
            motionY *= 0.98D;
            motionZ *= 0.98D;

            if (!worldObj.isRemote)
            {
                int i = MathHelper.floor_double(posX);
                int j = MathHelper.floor_double(posY);
                int k = MathHelper.floor_double(posZ);

                if (fallTime == 1)
                {
                    if (worldObj.getBlock(i, j, k) != block)
                    {
                        setDead();
                        return;
                    }
                    worldObj.setBlockToAir(i, j, k);
                }

                if (isAntiGravity ? worldObj.getBlock(i, j + 1, k) != Blocks.air : onGround)
                {
                    motionX *= 0.7D;
                    motionZ *= 0.7D;
                    motionY *= -0.5D;

                    if (worldObj.getBlock(i, j, k) != Blocks.piston_extension)
                    {
                        setDead();

                        if (worldObj.canPlaceEntityOnSide(block, i, j, k, true, /*isAntiGravity ? 0 :*/ 1, null, null)
                                && !BlockCSGravity.canFallAt(worldObj, i, j + (isAntiGravity ? 1 : -1), k)
                                && worldObj.setBlock(i, j, k, block, meta, 3))
                        {

                        } else if (shouldDropItem)
                        {
                            for (ItemStack stack : block.getDrops(worldObj, i, j, k, meta, 0))
                            {
                                entityDropItem(stack.copy(), 0.0f);
                            }
                        }
                    }
                } else if (fallTime > 100 && !worldObj.isRemote && (j < 1 || j > 256) || fallTime > 600)
                {
                    if (shouldDropItem)
                    {
                        for (ItemStack stack : block.getDrops(worldObj, i, j, k, meta, 0))
                        {
                            entityDropItem(stack.copy(), 0.0f);
                        }
                    }

                    setDead();
                }
            }

        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setShort("Tile", (short) Block.getIdFromBlock(block));
        nbtTagCompound.setByte("Meta", (byte) meta);
        nbtTagCompound.setByte("Time", (byte) fallTime);
        nbtTagCompound.setBoolean("DropItem", shouldDropItem);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound)
    {
        block = (BlockCSGravity) Block.getBlockById(nbtTagCompound.getShort("Tile"));
        meta = nbtTagCompound.getByte("Meta") & 255;

        if (nbtTagCompound.hasKey("Time"))
        {
            fallTime = nbtTagCompound.getByte("Time") & 255;
        }

        if (nbtTagCompound.hasKey("DropItem"))
        {
            shouldDropItem = nbtTagCompound.getBoolean("DropItem");
        }
    }

    @Override
    public void addEntityCrashInfo(CrashReportCategory par1CrashReportCategory)
    {
        super.addEntityCrashInfo(par1CrashReportCategory);
        par1CrashReportCategory.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
        par1CrashReportCategory.addCrashSection("Immitating block data", Integer.valueOf(meta));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

    public World getWorld()
    {
        return worldObj;
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        data.writeInt(Block.getIdFromBlock(block));
        data.writeByte(meta);
        data.writeFloat((float) posX);
        data.writeFloat((float) posY);
        data.writeFloat((float) posZ);
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        block = (BlockCSGravity) Block.getBlockById(data.readInt());
        meta = data.readByte() & 255;
        float x = data.readFloat();
        float y = data.readFloat();
        float z = data.readFloat();

        preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        this.setPosition(x, y, z);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
        shouldDropItem = true;
    }
}
