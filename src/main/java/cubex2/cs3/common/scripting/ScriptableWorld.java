package cubex2.cs3.common.scripting;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.tileentity.TileEntityCS;
import cubex2.cs3.util.GeneralHelper;
import cubex2.cs3.util.NBTHelper;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScriptableWorld
{
    private World world;

    public ScriptableWorld(World world)
    {
        super();
        this.world = world;
    }

    public void setMod(BaseContentPack mod)
    {
    }

    /**
     * Sets the block at the given position
     *
     * @param position  The position
     * @param blockName The name of the block
     */
    public void setBlock(ScriptablePosition position, String blockName)
    {
        setBlock((int) position.x, (int) position.y, (int) position.z, blockName);
    }

    /**
     * Sets the block at the given position
     *
     * @param position        The position
     * @param blockName       The name of the block
     * @param notifyNeighbors Should neighbor blocks notified about the change
     */
    public void setBlock(ScriptablePosition position, String blockName, boolean notifyNeighbors)
    {
        setBlock((int) position.x, (int) position.y, (int) position.z, blockName, notifyNeighbors);
    }

    /**
     * Sets the block at the given coordinates
     *
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param z         The z coordinate
     * @param blockName The name of the block
     */
    public void setBlock(int x, int y, int z, String blockName)
    {
        setBlock(x, y, z, blockName, true);
    }

    /**
     * Sets the block at the given coordinates
     *
     * @param x               The x coordinate
     * @param y               The y coordinate
     * @param z               The z coordinate
     * @param blockName       The name of the block
     * @param notifyNeighbors Should neighbor blocks notified about the change
     */
    public void setBlock(int x, int y, int z, String blockName, boolean notifyNeighbors)
    {
        Block block = GeneralHelper.getBlock(blockName);
        world.setBlock(x, y, z, block);
        if (notifyNeighbors)
        {
            world.notifyBlockChange(x, y, z, block);
        }
    }

    /**
     * Sets the damage value of a block
     *
     * @param position The position
     * @param metadata The damage value of the block
     */
    public void setBlockMetadata(ScriptablePosition position, int metadata)
    {
        setBlockMetadata((int) position.x, (int) position.y, (int) position.z, metadata);
    }

    /**
     * Sets the damage value of a block
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param metadata The damage value of the block
     */
    public void setBlockMetadata(int x, int y, int z, int metadata)
    {
        world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
        world.markBlockForUpdate(x, y, z);
    }

    /**
     * Sets the id and damage value of a block
     *
     * @param position  The position
     * @param blockName The block's name
     * @param metadata  The block's damage value
     */
    public void setBlockAndMetadata(ScriptablePosition position, String blockName, int metadata)
    {
        setBlockAndMetadata((int) position.x, (int) position.y, (int) position.z, blockName, metadata);
    }

    /**
     * Sets the id and damage value of a block
     *
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param z         The z coordinate
     * @param blockName The block's name
     * @param metadata  The block's damage value
     */
    public void setBlockAndMetadata(int x, int y, int z, String blockName, int metadata)
    {
        world.setBlock(x, y, z, GeneralHelper.getBlock(blockName), metadata, 3);
    }

    /**
     * Harvests a block
     *
     * @param position The position
     */
    public void harvestBlock(ScriptablePosition position)
    {
        harvestBlock((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Harvests a block
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public void harvestBlock(int x, int y, int z)
    {
        new Random();
        Block block = world.getBlock(x, y, z);
        int damage = world.getBlockMetadata(x, y, z);
        ArrayList<ItemStack> is = block.getDrops(world, x, y, z, damage, 0);

        world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
        world.setBlockToAir(x, y, z);
        world.notifyBlockChange(x, y, z, Blocks.air);
        float f = 0.7F;
        double d = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        if (is.size() > 0)
        {
            for (ItemStack is1 : is)
                if (is1.stackSize != 0)
                {
                    if (!world.isRemote)
                    {
                        EntityItem entityitem = new EntityItem(world, x + d, y + d1, z + d2, new ItemStack(is1.getItem(), is1.stackSize, is1.getItemDamage()));
                        entityitem.delayBeforeCanPickup = 10;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
        }
    }

    /**
     * Sets the time
     *
     * @param time The time
     */
    public void setTime(long time)
    {
        world.setWorldTime(time);
    }

    /**
     * Gets the world time
     *
     * @return The world time
     */
    public long getTime()
    {
        return world.getWorldTime();
    }

    /**
     * Creates a explosion
     *
     * @param position The position
     * @param strength The strength of the explosion
     * @param flaming  True if the explosion is a flaming explosion
     */
    public void createExplosion(ScriptablePosition position, float strength, boolean flaming)
    {
        createExplosion(position.x, position.y, position.z, strength, flaming);
    }

    /**
     * Creates a explosion
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param strength The strength of the explosion
     * @param flaming  True if the explosion is a flaming explosion
     */
    public void createExplosion(double x, double y, double z, float strength, boolean flaming)
    {
        world.newExplosion(null, x, y, z, strength, flaming, /* Huge explosion */true);
    }

    /**
     * Creates a thunderbolt
     *
     * @param position The position
     */
    public void createThunderbolt(ScriptablePosition position)
    {
        createThunderbolt(position.x, position.y, position.z);
    }

    /**
     * Creates a thunderbolt
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public void createThunderbolt(double x, double y, double z)
    {
        EntityLightningBolt entitylightningbolt = new EntityLightningBolt(world, x, y, z);
        world.spawnEntityInWorld(entitylightningbolt);
    }

    /**
     * Spawns a mob
     *
     * @param position The position
     * @param name     The name of the mob
     */
    public void spawnMob(ScriptablePosition position, String name)
    {
        spawnMob(position.x, position.y, position.z, name);
    }

    /**
     * Spawns a mob
     *
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param name The name of the mob
     */
    public void spawnMob(double x, double y, double z, String name)
    {
        if (!world.isRemote)
        {
            Entity entity = EntityList.createEntityByName(name, world);
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntityInWorld(entity);
        }
    }

    /**
     * Spawns an item
     *
     * @param position The position
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param damage   The item's damage value
     */
    public void spawnItem(ScriptablePosition position, String itemName, int count, int damage)
    {
        spawnItem(position.x, position.y, position.z, itemName, count, damage);
    }

    /**
     * Spawns an item
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param damage   The item's damage value
     */
    public void spawnItem(double x, double y, double z, String itemName, int count, int damage)
    {
        if (world.isRemote)
            return;
        float f = 0.7F;
        double d = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, x + d, y + d1, z + d2, new ItemStack(GeneralHelper.getItem(itemName), count, damage));
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
    }

    /**
     * Sets the weather
     *
     * @param weather  sun, rain or thundering
     * @param duration The duration
     */
    public void setWeather(String weather, int duration)
    {
        if (weather.equals("sun"))
        {
            world.getWorldInfo().setRaining(false);
            world.getWorldInfo().setRainTime(0);
            world.getWorldInfo().setThundering(false);
            world.getWorldInfo().setThunderTime(0);
        } else if (weather.equals("rain"))
        {
            world.getWorldInfo().setRaining(true);
            world.getWorldInfo().setRainTime(duration);
            world.getWorldInfo().setThundering(false);
            world.getWorldInfo().setThunderTime(0);
        } else if (weather.equals("thundering"))
        {
            world.getWorldInfo().setRaining(true);
            world.getWorldInfo().setRainTime(duration);
            world.getWorldInfo().setThundering(true);
            world.getWorldInfo().setThunderTime(duration);
        }
    }

    /**
     * Check if it is raining.
     *
     * @return true if it is raining, false otherwise
     */
    public boolean isRaining()
    {
        return world.getWorldInfo().isRaining();
    }

    /**
     * Check if it is thundering.
     *
     * @return true if it is thundering, false otherwise
     */
    public boolean isThundering()
    {
        world.isThundering();
        return world.getWorldInfo().isThundering();
    }

    /**
     * Gets the block name
     *
     * @param position The position
     * @return The block's name
     */
    public String getBlockName(ScriptablePosition position)
    {
        return getBlockName((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the block name
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The block's name
     */
    public String getBlockName(int x, int y, int z)
    {
        String name = GeneralHelper.getBlockName(world.getBlock(x, y, z));
        return name != null ? name : "minecraft:air";
    }

    /**
     * Gets the block's damage value
     *
     * @param position The position
     * @return The block's damage value
     */
    public int getBlockMetadata(ScriptablePosition position)
    {
        return getBlockMetadata((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the block's damage value
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The block's damage value
     */
    public int getBlockMetadata(int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

    /**
     * Gets the biome
     *
     * @param position The position
     * @return The name of the biome
     */
    public String getBiome(ScriptablePosition position)
    {
        return getBiome((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the biome
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The name of the biome
     */
    public String getBiome(int x, int y, int z)
    {
        return world.getWorldChunkManager().getBiomeGenAt(x, z).biomeName;
    }

    /**
     * Shears a block if it can be sheared.
     *
     * @param position The position
     */
    public void shear(ScriptablePosition position)
    {
        shear((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Shears a block if it can be sheared.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public void shear(int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block instanceof IShearable)
        {
            IShearable target = (IShearable) block;
            if (target.isShearable(null, world, x, y, z))
            {
                ArrayList<ItemStack> drops = target.onSheared(null, world, x, y, z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, null));
                Random rand = new Random();
                for (ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(world, x + d, y + d1, z + d2, stack);
                    entityitem.delayBeforeCanPickup = 10;
                    world.spawnEntityInWorld(entityitem);
                }
            }
        }
    }

    /**
     * Gets the amount of entities within the given cube with the given radius.
     *
     * @param pos      The position
     * @param radius   The radius of the lookup cube
     * @param entities The entities to search. Allowed values are 'hostile', 'animal', 'mob', 'player', 'item', 'all', any entityID and any
     *                 entity name. You can give multiple values by dividing them with a ','
     * @return The amount of entities
     */
    public int countEntities(ScriptablePosition pos, float radius, String entities)
    {
        return countEntities((int) pos.x, (int) pos.y, (int) pos.z, radius, entities);
    }

    /**
     * Gets the amount of entities within the given cube with the given radius.
     *
     * @param x        The x-coordinate
     * @param y        The y-coordinate
     * @param z        The z-coordinate
     * @param radius   The radius of the lookup cube
     * @param entities The entities to search. Allowed values are 'hostile', 'animal', 'mob', 'player', 'item', 'all', any entityID and any
     *                 entity name. You can give multiple values by dividing them with a ','
     * @return The amount of entities
     */
    public int countEntities(int x, int y, int z, float radius, String entities)
    {
        int amount = 0;
        for (String entity : entities.split(","))
        {
            Class<? extends Entity> mobClass;
            if (entity.equals("hostile"))
            {
                mobClass = EntityMob.class;
            } else if (entity.equals("animal"))
            {
                mobClass = EntityAnimal.class;
            } else if (entity.equals("mob"))
            {
                mobClass = EntityLiving.class;
            } else if (entity.equals("player"))
            {
                mobClass = EntityPlayer.class;
            } else if (entity.equals("item"))
            {
                mobClass = EntityItem.class;
            } else if (entity.equals("all"))
            {
                mobClass = null;
            } else
            {
                if (entity.matches("[0-9]+"))
                {
                    mobClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(EntityList.getStringFromID(Integer.parseInt(entity)));
                } else
                {
                    mobClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(entity);
                }
            }
            AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(x + 0.5 - radius, y + 0.5 - radius, z + 0.5 - radius, x + 0.5 + radius, y + 0.5 + radius, z + 0.5 + radius);
            amount += mobClass == null ? world.getEntitiesWithinAABBExcludingEntity((Entity) null, axis).size() : world.getEntitiesWithinAABB(mobClass, axis).size();
        }
        return amount;
    }

    /**
     * Gets all entities in the given radius.
     *
     * @param pos      The position
     * @param radius   The radius of the lookup cube
     * @param entities The entities to search. Allowed values are 'hostile', 'animal', 'mob', 'player', 'item', 'all', any entityID and any
     *                 entity name. You can give multiple values by dividing them with a ','
     * @return The entities.
     */
    public ScriptableEntity[] enumEntities(ScriptablePosition pos, float radius, String entities)
    {
        return enumEntities((int) pos.x, (int) pos.y, (int) pos.z, radius, entities);
    }

    /**
     * Gets all entities in the given radius.
     *
     * @param x        The x-coordinate
     * @param y        The y-coordinate
     * @param z        The z-coordinate
     * @param radius   The radius of the lookup cube
     * @param entities The entities to search. Allowed values are 'hostile', 'animal', 'mob', 'player', 'item', 'all', any entityID and any
     *                 entity name. You can give multiple values by dividing them with a ','
     * @return The entities.
     */
    public ScriptableEntity[] enumEntities(int x, int y, int z, float radius, String entities)
    {
        List<ScriptableEntity> ret = Lists.newArrayList();
        for (String entity : entities.split(","))
        {
            Class<? extends Entity> mobClass = null;
            if (entity.equals("hostile"))
            {
                mobClass = EntityMob.class;
            } else if (entity.equals("animal"))
            {
                mobClass = EntityAnimal.class;
            } else if (entity.equals("mob"))
            {
                mobClass = EntityLiving.class;
            } else if (entity.equals("player"))
            {
                mobClass = EntityPlayer.class;
            } else if (entity.equals("item"))
            {
                mobClass = EntityItem.class;
            } else if (entity.equals("all"))
            {
                mobClass = null;
            } else
            {
                if (entity.matches("[0-9]+"))
                {
                    mobClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(EntityList.getStringFromID(Integer.parseInt(entity)));
                } else
                {
                    mobClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(entity);
                }
            }
            AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(x + 0.5 - radius, y + 0.5 - radius, z + 0.5 - radius, x + 0.5 + radius, y + 0.5 + radius, z + 0.5 + radius);
            List<Entity> list = mobClass == null ? world.getEntitiesWithinAABBExcludingEntity((Entity) null, axis) : world.getEntitiesWithinAABB(mobClass, axis);
            for (Entity e : list)
            {
                ret.add(new ScriptableEntity(e));
            }
        }

        return ret.toArray(new ScriptableEntity[ret.size()]);
    }

    /**
     * Gets the light level of the block at the given position.
     *
     * @param position The block's position
     * @return
     */
    public int getBlockLightLevel(ScriptablePosition position)
    {
        return getBlockLightLevel((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the light level of the block at the given position.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     * @return
     */
    public int getBlockLightLevel(int x, int y, int z)
    {
        return world.getBlockLightValue(x, y, z);
    }

    /**
     * Sends a chat message to all players in the world.
     *
     * @param message The message to send.
     */
    public void sendMessageToAllPlayers(String message)
    {
        if (!world.isRemote)
        {
            for (Object o : world.playerEntities)
            {
                if (o instanceof EntityPlayer)
                {
                    ((EntityPlayer) o).addChatComponentMessage(new ChatComponentText(message));
                }
            }
        }
    }

    /**
     * Sends a chat message to the given player.
     *
     * @param player  The player's username
     * @param message The message to send
     */
    public void sendMessageToPlayer(String player, String message)
    {
        if (!world.isRemote)
        {
            EntityPlayer entityPlayer = world.getPlayerEntityByName(player);
            if (entityPlayer != null)
            {
                entityPlayer.addChatComponentMessage(new ChatComponentText(message));
            }
        }
    }

    /**
     * Gets the type of the block with the given id. This works for all CS2 blocks and for vanilla buttons, chests, doors, fences, fence
     * gates, fluids, furnaces, ladders, panes, pressure plates, slabs, torches, trap doors and walls. It may work some blocks from other
     * non-CS2 mods.
     *
     * @param position The position
     * @return The type of the block or unknown if the block doesn't exist or isn't supported. The types equal to the CS2 block types.
     */
    public String getBlockType(ScriptablePosition position)
    {
        return getBlockType((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the type of the block with the given id. This works for all CS2 blocks and for vanilla buttons, carpets, chests, doors, fences, fence
     * gates, fluids, furnaces, ladders, panes, pressure plates, slabs, torches, trap doors and walls. It may work some blocks from other
     * non-CS2 mods.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     * @return The type of the block or unknown if the block doesn't exist or isn't supported. The types equal to the CS2 block types.
     */
    public String getBlockType(int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block == null)
            return "unknown";
            //else if (block instanceof ICSBlock)
            //    return ((ICSBlock) block).getAttributes().type.name;
        else if (block instanceof BlockButton)
            return "button";
        else if (block instanceof BlockCarpet)
            return "carpet";
        else if (block instanceof BlockChest)
            return "chest";
        else if (block instanceof BlockDoor)
            return "door";
        else if (block instanceof BlockFence)
            return "fence";
        else if (block instanceof BlockFenceGate)
            return "fenceGate";
        else if (block instanceof BlockFluidBase)
            return "fluid";
        else if (block instanceof BlockFurnace)
            return "furnace";
        else if (block instanceof BlockLadder)
            return "ladder";
        else if (block instanceof BlockPane)
            return "pane";
        else if (block instanceof BlockBasePressurePlate)
            return "pressurePlate";
        else if (block instanceof BlockSlab)
            return "slab";
        else if (block instanceof BlockTorch)
            return "torch";
        else if (block instanceof BlockTrapDoor)
            return "trapDoor";
        else if (block instanceof BlockWall)
            return "wall";

        return "unknown";
    }

    /**
     * Spawns a particle at the given position and the given velocity
     *
     * @param name     The name of the particle: bubble, suspended, depthsuspend, townaura, crit, magicCrit, smoke,
     *                 mobSpell, mobSpellAmbient, spell, instantSpell, witchMagic, note, portal, enchantmenttable,
     *                 explode, flame, lava, footstep, splash, wake, largesmoke, cloud, reddust, snowballpoof, dripWater,
     *                 dripLava, snowshovel, slime, heart, angryVillager, happyVillager
     * @param position The position
     * @param velX     The x velocity
     * @param velY     The y velocity
     * @param velZ     The z velocity
     */
    public void spawnParticle(String name, ScriptablePosition position, double velX, double velY, double velZ)
    {
        spawnParticle(name, position.x, position.y, position.z, velX, velY, velZ);
    }

    /**
     * Spawns a particle at the given position and the given velocity
     *
     * @param name The name of the particle: bubble, suspended, depthsuspend, townaura, crit, magicCrit, smoke,
     *             mobSpell, mobSpellAmbient, spell, instantSpell, witchMagic, note, portal, enchantmenttable,
     *             explode, flame, lava, footstep, splash, wake, largesmoke, cloud, reddust, snowballpoof, dripWater,
     *             dripLava, snowshovel, slime, heart, angryVillager, happyVillager
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param velX The x velocity
     * @param velY The y velocity
     * @param velZ The z velocity
     */
    public void spawnParticle(String name, double x, double y, double z, double velX, double velY, double velZ)
    {
        world.spawnParticle(name, x, y, z, velX, velY, velZ);
    }

    /**
     * Gets the inventory of a block
     *
     * @param position The blocks position
     * @return The inventory or null if there wasn't any
     */
    public ScriptableInventory getInventory(ScriptablePosition position)
    {
        return getInventory((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the inventory of a block
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The inventory or null if there wasn't any
     */
    public ScriptableInventory getInventory(int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof IInventory)
        {
            return new ScriptableInventory((IInventory) te);
        }
        return null;
    }


    /**
     * Gets the tile entity's int data
     *
     * @param pos  The position
     * @param name The data's name
     * @return The data or -1 if the data doesn't exist
     */
    public int getTileEntityIntData(ScriptablePosition pos, String name)
    {
        return getTileEntityIntData((int) pos.x, (int) pos.y, (int) pos.z, name);
    }

    /**
     * Gets the tile entity's int data
     *
     * @param x    The x-coordinate
     * @param y    The y-coordinate
     * @param z    The z-coordinate
     * @param name The data's name
     * @return The data or -1 if the data doesn't exist
     */
    public int getTileEntityIntData(int x, int y, int z, String name)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityCS)
        {
            TileEntityCS tile = (TileEntityCS) tileEntity;
            return NBTHelper.getCSIntData(tile.getCompound(), name);
        }
        return -1;
    }

    /**
     * Gets the tile entity's float data
     *
     * @param pos  The position
     * @param name The data's name
     * @return The data or -1.0 if the data doesn't exist
     */
    public float getTileEntityFloatData(ScriptablePosition pos, String name)
    {
        return getTileEntityFloatData((int) pos.x, (int) pos.y, (int) pos.z, name);
    }

    /**
     * Gets the tile entity's float data
     *
     * @param x    The x-coordinate
     * @param y    The y-coordinate
     * @param z    The z-coordinate
     * @param name The data's name
     * @return The data or -1.0 if the data doesn't exist
     */
    public float getTileEntityFloatData(int x, int y, int z, String name)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityCS)
        {
            TileEntityCS tile = (TileEntityCS) tileEntity;
            return NBTHelper.getCSFloatData(tile.getCompound(), name);
        }
        return -1.0f;
    }

    /**
     * Gets the tile entity's string data
     *
     * @param pos  The position
     * @param name The data's name
     * @return The data or null if the data doesn't exist
     */
    public String getTileEntityStringData(ScriptablePosition pos, String name)
    {
        return getTileEntityStringData((int) pos.x, (int) pos.y, (int) pos.z, name);
    }

    /**
     * Gets the tile entity's string data
     *
     * @param x    The x-coordinate
     * @param y    The y-coordinate
     * @param z    The z-coordinate
     * @param name The data's name
     * @return The data or null if the data doesn't exist
     */
    public String getTileEntityStringData(int x, int y, int z, String name)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityCS)
        {
            TileEntityCS tile = (TileEntityCS) tileEntity;
            return NBTHelper.getCSStringData(tile.getCompound(), name);
        }
        return null;
    }

    /**
     * Sets the tile entity's int data
     *
     * @param pos  The position
     * @param name The data's name
     * @param data The data
     */
    public void setTileEntityIntData(ScriptablePosition pos, String name, int data)
    {
        setTileEntityIntData((int) pos.x, (int) pos.y, (int) pos.z, name, data);
    }

    /**
     * Sets the tile entity's int data.
     *
     * @param x    The x-coordinate
     * @param y    The y-coordinate
     * @param z    The z-coordinate
     * @param name The data's name
     * @param data The data
     */
    public void setTileEntityIntData(int x, int y, int z, String name, int data)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityCS)
        {
            TileEntityCS tile = (TileEntityCS) tileEntity;
            NBTHelper.setCSIntData(tile.getCompound(), name, data);
        }
    }

    /**
     * Sets the tile entity's float data
     *
     * @param pos  The position
     * @param name The data's name
     * @param data The data
     */
    public void setTileEntityFloatData(ScriptablePosition pos, String name, float data)
    {
        setTileEntityFloatData((int) pos.x, (int) pos.y, (int) pos.z, name, data);
    }

    /**
     * Sets the tile entity's float data.
     *
     * @param x    The x-coordinate
     * @param y    The y-coordinate
     * @param z    The z-coordinate
     * @param name The data's name
     * @param data The data
     */
    public void setTileEntityFloatData(int x, int y, int z, String name, float data)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityCS)
        {
            TileEntityCS tile = (TileEntityCS) tileEntity;
            NBTHelper.setCSFloatData(tile.getCompound(), name, data);
        }
    }

    /**
     * Sets the tile entity's string data
     *
     * @param pos  The position
     * @param name The data's name
     * @param data The data
     */
    public void setTileEntityStringData(ScriptablePosition pos, String name, String data)
    {
        setTileEntityStringData((int) pos.x, (int) pos.y, (int) pos.z, name, data);
    }

    /**
     * Sets the tile entity's string data.
     *
     * @param x    The x-coordinate
     * @param y    The y-coordinate
     * @param z    The z-coordinate
     * @param name The data's name
     * @param data The data
     */
    public void setTileEntityStringData(int x, int y, int z, String name, String data)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityCS)
        {
            TileEntityCS tile = (TileEntityCS) tileEntity;
            NBTHelper.setCSStringData(tile.getCompound(), name, data);
        }
    }
}

