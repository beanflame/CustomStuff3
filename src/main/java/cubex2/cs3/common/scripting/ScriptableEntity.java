package cubex2.cs3.common.scripting;

import cubex2.cs3.util.GeneralHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

public class ScriptableEntity
{
    private Entity entity;

    public ScriptableEntity(Entity entity)
    {
        super();
        this.entity = entity;
    }

    /**
     * Sets the entity on fire
     *
     * @param duration The duration in seconds
     */
    public void setFire(int duration)
    {
        entity.setFire(duration);
    }

    /**
     * Removes fire from entity
     */
    public void extinguish()
    {
        entity.extinguish();
    }

    /**
     * Sets the air of the entity
     *
     * @param air
     */
    public void setAir(int air)
    {
        entity.setAir(air);
    }

    /**
     * Drops an item at the position of the entity
     *
     * @param itemName    The item's name
     * @param damageValue The items damage value
     * @param stacksize   The item's stacksize
     */
    public void dropItem(String itemName, int damageValue, int stacksize)
    {
        if (!entity.worldObj.isRemote)
        {
            entity.entityDropItem(new ItemStack(GeneralHelper.getItem(itemName), stacksize, damageValue), 0.0f);
        }
    }

    /**
     * @return The x-position of the entity
     */
    public double getPosX()
    {
        return entity.posX;
    }

    /**
     * @return The y-position of the entity
     */
    public double getPosY()
    {
        if (entity.worldObj.isRemote)
            return entity.serverPosY;
        return entity.posY;
    }

    /**
     * @return The z-position of the entity
     */
    public double getPosZ()
    {
        return entity.posZ;
    }

    /**
     * Sets the x-position of the entity
     *
     * @param x The x-position
     */
    public void setPosX(double x)
    {
        entity.setPosition(x, getPosY(), getPosZ());
    }

    /**
     * Sets the y-position of the entity
     *
     * @param y The y-position
     */
    public void setPosY(double y)
    {
        entity.setPosition(getPosX(), y, getPosZ());
    }

    /**
     * Sets the z-position of the entity
     *
     * @param z The z-position
     */
    public void setPosZ(double z)
    {
        entity.setPosition(getPosX(), getPosY(), z);
    }

    /**
     * Sets the position of the entity
     *
     * @param x The x-position
     * @param y The y-position
     * @param z The z-position
     */
    public void setPosition(double x, double y, double z)
    {
        entity.setPosition(x, y, z);
    }

    /**
     * Sets the position of the entity
     *
     * @param position the position
     */
    public void setPosition(ScriptablePosition position)
    {
        entity.setPosition(position.jsGet_x(), position.jsGet_y(), position.jsGet_z());
    }

    /**
     * Adds the given position to the living's position
     *
     * @param pos
     */
    public void move(ScriptablePosition pos)
    {
        move(pos.x, pos.y, pos.z);
    }

    /**
     * Adds the given values to the living's position
     *
     * @param x The x-value
     * @param y The y-value
     * @param z The z-value
     */
    public void move(double x, double y, double z)
    {
        entity.moveEntity(x, y, z);
    }

    /**
     * Adds velocity to the entity into the direction it looks
     *
     * @param velocity The amount of velocity
     */
    public void addVelocityToLookingDirection(double velocity)
    {
        float yaw = entity.rotationYaw;
        float pitch = entity.rotationPitch;
        double xHeading = -MathHelper.sin(yaw * 3.141593F / 180F);
        double zHeading = MathHelper.cos(yaw * 3.141593F / 180F);
        double x = velocity * xHeading * MathHelper.cos(pitch / 180F * 3.141593F);
        double y = -velocity * MathHelper.sin(pitch / 180F * 3.141593F);
        double z = velocity * zHeading * MathHelper.cos(pitch / 180F * 3.141593F);
        entity.addVelocity(x, y, z);
    }

    /**
     * Sets the velocity to the entity into the direction it looks
     *
     * @param velocity The amount of velocity
     */
    public void setVelocityToLookingDirection(double velocity)
    {
        float yaw = entity.rotationYaw;
        float pitch = entity.rotationPitch;
        double xHeading = -MathHelper.sin(yaw * 3.141593F / 180F);
        double zHeading = MathHelper.cos(yaw * 3.141593F / 180F);
        double x = velocity * xHeading * MathHelper.cos(pitch / 180F * 3.141593F);
        double y = -velocity * MathHelper.sin(pitch / 180F * 3.141593F);
        double z = velocity * zHeading * MathHelper.cos(pitch / 180F * 3.141593F);
        entity.setVelocity(x, y, z);
    }

    /**
     * Adds velocity to the entity
     *
     * @param x The velocity in x-direction
     * @param y The velocity in y-direction
     * @param z The velocity in z-direction
     */
    public void addVelocity(double x, double y, double z)
    {
        entity.addVelocity(x, y, z);
    }

    /**
     * Sets the velocity of the entity
     *
     * @param x The velocity in x-direction
     * @param y The velocity in y-direction
     * @param z The velocity in z-direction
     */
    public void setVelocity(double x, double y, double z)
    {
        entity.setVelocity(x, y, z);
    }

    /**
     * Sets the x-direction velocity
     *
     * @param value The new velocity
     */
    public void setVelocityX(double value)
    {
        entity.motionX = value;
    }

    /**
     * Sets the y-direction velocity
     *
     * @param value The new velocity
     */
    public void setVelocityY(double value)
    {
        entity.motionY = value;
    }

    /**
     * Sets the z-direction velocity
     *
     * @param value The new velocity
     */
    public void setVelocityZ(double value)
    {
        entity.motionZ = value;
    }

    /**
     * Gets the x-direction velocity
     */
    public double getVelocityX()
    {
        return entity.motionX;
    }

    /**
     * Gets the y-direction velocity
     */
    public double getVelocityY()
    {
        return entity.motionY;
    }

    /**
     * Gets the z-direction velocity
     */
    public double getVelocityZ()
    {
        return entity.motionZ;
    }

    /**
     * Gets the entitiy's fall distance. This is the amount of blocks that the player has already been fallen.
     *
     * @return the entity's fall distance
     */
    public float getFallDistance()
    {
        return entity.fallDistance;
    }

    /**
     * Sets the entity's fall distance which is the amount of blocks that the player has already been fallen.
     *
     * @param distance The new fall distance
     */
    public void setFallDistance(float distance)
    {
        entity.fallDistance = distance;
    }

    /**
     * Gets the name of the entity
     *
     * @return The name of the entity (Creeper, Sheep, ...)
     */
    public String getName()
    {
        return EntityList.getEntityString(entity);
    }

    /**
     * Shears the entity. This does the same as using shears on the entity
     */
    public void shear()
    {
        if (entity instanceof IShearable)
        {
            IShearable target = (IShearable) entity;
            if (target.isShearable(null, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ))
            {
                ArrayList<ItemStack> drops = target.onSheared(null, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, null));
                Random rand = new Random();
                for (ItemStack stack : drops)
                {
                    EntityItem ent = entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
            }
        }
    }

    /**
     * Checks if the entity is a living
     *
     * @return true if the entity is a living
     */
    public boolean isLiving()
    {
        return entity instanceof EntityLivingBase;
    }

    /**
     * Gets this entity as a living.
     *
     * @return
     */
    public ScriptableEntityLiving asLiving()
    {
        return new ScriptableEntityLiving((EntityLivingBase) entity);
    }

    /**
     * Checks if the entity is a player
     *
     * @return true if the entity is a player
     */
    public boolean isPlayer()
    {
        return entity instanceof EntityPlayer;
    }

    /**
     * Gets this entity as a player
     *
     * @return
     */
    public ScriptableEntityPlayer asPlayer()
    {
        return new ScriptableEntityPlayer((EntityPlayer) entity);
    }

    /**
     * Checks if the entity is an item
     *
     * @return true if the entity is an item
     */
    public boolean isItem()
    {
        return entity instanceof EntityItem;
    }

    /**
     * Gets this entity as an entityItem
     *
     * @return
     */
    public ScriptableEntityItem asItem()
    {
        return new ScriptableEntityItem((EntityItem) entity);
    }


    /**
     * Instantly removes the entity from the world.
     */
    public void setDead()
    {
        entity.setDead();
    }
}
