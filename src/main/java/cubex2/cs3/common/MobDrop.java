package cubex2.cs3.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.StackLabelItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class MobDrop extends BaseContent implements StackLabelItem
{
    public String mob;
    public ItemStack stack;
    public float chance;
    public boolean playerKillOnly;

    private Class<? extends Entity> clazz;

    public MobDrop(BaseContentPack pack)
    {
        super(pack);
    }

    public MobDrop(String mob, ItemStack stack, float chance, boolean playerKillOnly, BaseContentPack pack)
    {
        super(pack);
        this.mob = mob;
        this.stack = stack;
        this.chance = chance;
        this.playerKillOnly = playerKillOnly;
    }

    private Class<? extends Entity> getEntityClass()
    {
        if (clazz == null)
            clazz = (Class<? extends Entity>) EntityList.stringToClassMapping.get(mob);
        return clazz;
    }

    @Override
    public void apply()
    {
        MinecraftForge.EVENT_BUS.register(this);

        super.apply();
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        Entity source = event.source.getEntity();
        Class<? extends Entity> eClass = getEntityClass();
        if (eClass != null && eClass == event.entity.getClass() &&
                (!playerKillOnly || (source != null && source instanceof EntityPlayer)))
        {
            if (event.entity.worldObj.rand.nextFloat() <= chance)
            {
                event.entity.entityDropItem(stack.copy(), 0.0f);
            }
        }
    }

    @Override
    public void remove()
    {
        MinecraftForge.EVENT_BUS.unregister(this);

        super.remove();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Mob", mob);
        compound.setTag("Stack", ItemStackHelper.writeToNBTNamed(stack));
        compound.setFloat("Chance", chance);
        compound.setBoolean("PlayerKillOnly", playerKillOnly);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        mob = compound.getString("Mob");
        stack = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Stack"));
        chance = compound.getFloat("Chance");
        playerKillOnly = compound.getBoolean("PlayerKillOnly");

        return stack != null;
    }

    @Override
    public ItemStack getStack()
    {
        return stack;
    }

    @Override
    public String getLabel()
    {
        return mob;
    }
}
