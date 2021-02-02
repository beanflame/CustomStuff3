package cubex2.cs3.common.scripting;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class ScriptableEntityLiving extends ScriptableEntity
{
    private EntityLivingBase entityLiving;

    public ScriptableEntityLiving(EntityLivingBase entity)
    {
        super(entity);
        entityLiving = entity;
    }

    /**
     * @return The maximum health
     */
    public float getMaxHealth()
    {
        return entityLiving.getMaxHealth();
    }

    /**
     * Sets the living's health
     *
     * @param health
     */
    public void setHealth(int health)
    {
        entityLiving.setHealth(health);
    }

    /**
     * Damages the entity
     *
     * @param damage
     *         The damage to add to the entity
     */
    public void attack(int damage)
    {
        entityLiving.attackEntityFrom(DamageSource.generic, damage);
    }

    /**
     * @return The living's health
     */
    public float getHealth()
    {
        return entityLiving.getHealth();
    }

    /**
     * @return The side the living is looking at (0, 1, 2 or 3)
     */
    public int getLookingSide()
    {
        return MathHelper.floor_double(entityLiving.rotationYaw * 4F / 360F + 0.5D) & 3;
    }

    /**
     * Colors a sheep.
     *
     * @param color
     *         The metadata of the dye you want its color. So if you want to color the sheep red you have to set this to 1.
     * @return True if it was colored false otherwise
     */
    public boolean colorSheep(int color)
    {
        if (entityLiving instanceof EntitySheep)
        {
            EntitySheep sheep = (EntitySheep) entityLiving;

            int color1 = BlockColored.func_150032_b(color);

            if (!sheep.getSheared() && sheep.getFleeceColor() != color1)
            {
                sheep.setFleeceColor(color1);
                return true;
            }
        }
        return false;
    }

    /**
     * Grows the wool of a sheep
     *
     * @return True if the wool was grown false otherwise
     */
    public boolean growSheepWool()
    {
        if (entityLiving instanceof EntitySheep)
        {
            EntitySheep sheep = (EntitySheep) entityLiving;
            if (sheep.getSheared())
            {
                sheep.setSheared(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a potion effect to the living
     *
     * @param type
     *         The potion type
     * @param duration
     *         The duration in ticks (1/20 second)
     * @param amplifier
     *         The amplifier
     */
    public void addPotionEffect(String type, int duration, int amplifier)
    {
        for (Potion p : Potion.potionTypes)
        {
            if (p != null && p.getName().replaceFirst("potion\\.", "").equals(type))
            {
                entityLiving.addPotionEffect(new PotionEffect(p.getId(), duration, amplifier));
            }
        }
    }

}
