package cubex2.cs3.common.scripting;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.common.inventory.Inventory;
import cubex2.cs3.common.inventory.WrappedInventory;
import cubex2.cs3.gui.ContainerBasic;
import cubex2.cs3.gui.EnumGuiType;
import cubex2.cs3.gui.InventoryItemStack;
import cubex2.cs3.gui.WindowNormal;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.network.PacketOpenCustomGuiServer;
import cubex2.cs3.network.PacketOpenUserContainerGuiClient;
import cubex2.cs3.registry.GuiRegistry;
import cubex2.cs3.tileentity.TileEntityInventory;
import cubex2.cs3.util.GeneralHelper;
import cubex2.cs3.util.JavaScriptHelper;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;

public class ScriptableEntityPlayer extends ScriptableEntityLiving
{

    private EntityPlayer player;
    private Inventory inventory;

    public ScriptableEntityPlayer(EntityPlayer player)
    {
        super(player);
        this.player = player;
        inventory = new Inventory(player.inventory);
    }

    /**
     * Opens a client-only gui for the player
     */
    public void openGui(String guiName)
    {
        BaseContentPack pack = JavaScriptHelper.executingPack;
        WrappedGui gui = ((GuiRegistry) pack.getContentRegistry(WrappedGui.class)).getGui(guiName);
        while (GuiBase.INSTANCE.window instanceof WindowNormal)
            GuiBase.openPrevWindow();

        if (gui.getType() == EnumGuiType.NORMAL && player.worldObj.isRemote)
        {
            FMLClientHandler.instance().showGuiScreen(GuiBase.INSTANCE);
            GuiBase.openWindow(gui.getType().createWindow(gui));
        }
    }

    /**
     * Opens an item inventory gui for the player.
     *
     * @param slotId The id of the slot of the itemstack
     */
    public void openItemGui(String guiName, int slotId)
    {
        BaseContentPack pack = JavaScriptHelper.executingPack;
        WrappedGui gui = ((GuiRegistry) pack.getContentRegistry(WrappedGui.class)).getGui(guiName);
        while (GuiBase.INSTANCE.window instanceof WindowNormal)
            GuiBase.openPrevWindow();

        if (gui.getType() == EnumGuiType.NORMAL && player.worldObj.isRemote)
        {
            FMLClientHandler.instance().showGuiScreen(GuiBase.INSTANCE);
            GuiBase.openWindow(gui.getType().createWindow(gui));
        } else if (gui.getType() == EnumGuiType.CONTAINER && !player.worldObj.isRemote)
        {
            PacketOpenCustomGuiServer.openGuiOnServer((EntityPlayerMP) player, new PacketOpenUserContainerGuiClient(gui, slotId),
                                                      new ContainerBasic(gui, player, new InventoryItemStack(gui, player, slotId)));
        }
    }

    /**
     * Opens a block inventory gui for the player.
     *
     * @param position The position of the block to open a gui for.
     */
    public void openBlockGui(String guiName, ScriptablePosition position)
    {
        openBlockGui(guiName, (int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Opens a block inventory gui for the player.
     *
     * @param guiName The name of the gui used for the name-attribute in the gui-file
     * @param x       The x-position of the block
     * @param y       The y-position of the block
     * @param z       The z-position of the block
     */
    public void openBlockGui(String guiName, int x, int y, int z)
    {
        BaseContentPack pack = JavaScriptHelper.executingPack;
        WrappedGui gui = ((GuiRegistry) pack.getContentRegistry(WrappedGui.class)).getGui(guiName);
        while (GuiBase.INSTANCE.window instanceof WindowNormal)
            GuiBase.openPrevWindow();

        if (gui.getType() == EnumGuiType.NORMAL && player.worldObj.isRemote)
        {
            FMLClientHandler.instance().showGuiScreen(GuiBase.INSTANCE);
            GuiBase.openWindow(gui.getType().createWindow(gui));
        } else if (gui.getType() == EnumGuiType.CONTAINER && !player.worldObj.isRemote)
        {
            TileEntity te = player.worldObj.getTileEntity(x, y, z);
            if (te != null && te instanceof TileEntityInventory)
            {
                PacketOpenCustomGuiServer.openGuiOnServer((EntityPlayerMP) player, new PacketOpenUserContainerGuiClient(gui, x, y, z),
                                                          new ContainerBasic(gui, player, (IInventory) te));
            } else
            {
                player.addChatMessage(new ChatComponentText("ERROR: Block has no tile entity of type 'inventory'"));
            }
        }
    }

    /**
     * Sets the player's hunger
     *
     * @param hunger The hunger to set
     */
    public void setHunger(int hunger)
    {
        ReflectionHelper.setPrivateValue(FoodStats.class, player.getFoodStats(), hunger, 0);
    }

    /**
     * Gets the player's hunger
     *
     * @return The player's hunger
     */
    public int getHunger()
    {
        return player.getFoodStats().getFoodLevel();
    }

    /**
     * Sets the player's saturation
     *
     * @param saturation The saturation to set
     */
    public void setSaturation(double saturation)
    {
        player.getFoodStats().setFoodSaturationLevel((float) saturation);
    }

    /**
     * Gets the player's saturation
     *
     * @return The player's saturation
     */
    public double getSaturation()
    {
        return player.getFoodStats().getSaturationLevel();
    }

    /**
     * Sets how high this entity can step up when running into a block to try to get over it
     *
     * @param f The step height
     */
    public void setStepHeight(double f)
    {
        player.stepHeight = (float) f;
    }

    /**
     * Gets the step height
     *
     * @return The player's step height
     */
    public double getStepHeight()
    {
        return player.stepHeight;
    }

    /**
     * Displays a message for the player. Doesn't work at the moment.
     *
     * @param message The message to display
     */
    public void sendMessage(String message)
    {
        if (!player.worldObj.isRemote)
        {
            player.addChatComponentMessage(new ChatComponentText(message));
        }
    }

    /**
     * Makes the player send a chat message as if the message was sent with the chat console. Commands will work this way.
     *
     * @param chat The chat message
     */
    public void sendChat(String chat)
    {
        if (player.worldObj.isRemote && player instanceof EntityClientPlayerMP)
        {
            if (ClientCommandHandler.instance.executeCommand(player, chat) != 0) return;
            ((EntityClientPlayerMP) player).sendChatMessage(chat);
        }
    }

    /**
     * Sets the current item in use, so onUsing is called.
     */
    public void setItemInUse()
    {
        player.setItemInUse(player.getCurrentEquippedItem(), player.getCurrentEquippedItem().getItem().getMaxItemUseDuration(player.getCurrentEquippedItem()));
    }

    /**
     * Gets the id of the slot the player is currently using
     *
     * @return The id of the slot
     */
    public int getCurrentSlot()
    {
        return player.inventory.currentItem;
    }

    /**
     * Places a block
     *
     * @param position         The position
     * @param side             On what side at position the block should be added
     * @param blockName        The block's name
     * @param metadata         The block's metadata
     * @param useFromInventory If true the function will uses blocks from the inventory and won't place any block if the required block isn't in the
     *                         player's inventory
     * @return True if the block has been placed, false otherwise
     */
    public boolean placeBlock(ScriptablePosition position, int side, String blockName, int metadata, boolean useFromInventory)
    {
        return placeBlock((int) position.x, (int) position.y, (int) position.z, side, blockName, metadata, useFromInventory);
    }

    /**
     * Places a block
     *
     * @param x                The x coordinate
     * @param y                The y coordinate
     * @param z                The z coordinate
     * @param side             On what side at position the block should be added
     * @param blockName        The block's name
     * @param metadata         The block's metadata
     * @param useFromInventory If true the function will uses blocks from the inventory and won't place any block if the required block isn't in the
     *                         player's inventory
     * @return True if the block has been placed, false otherwise
     */
    public boolean placeBlock(int x, int y, int z, int side, String blockName, int metadata, boolean useFromInventory)
    {
        Block block = GeneralHelper.getBlock(blockName);

        World world = player.worldObj;
        ItemStack stack;
        if (useFromInventory)
        {
            stack = inventory.findItem(Item.getItemFromBlock(block), metadata);
            if (stack == null)
                return false;
        } else
        {
            stack = new ItemStack(block, 1, metadata);
        }

        if (block == Blocks.snow)
        {
            side = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && (block == Blocks.air || !block.isReplaceable(world, x, y, z)))
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }

        if (stack.stackSize == 0)
            return false;
        else if (!player.canPlayerEdit(x, y, z, side, stack))
            return false;
        else if (y == 255 && block.getMaterial().isSolid())
            return false;
        else if (world.canPlaceEntityOnSide(block, x, y, z, false, side, player, stack))
        {
            int meta = block.onBlockPlaced(world, x, y, z, side, 0, 0, 0, stack.getItemDamage());

            if (placeBlockAt(block, stack, player, world, x, y, z, side, meta))
            {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        } else
            return false;
    }

    private boolean placeBlockAt(Block block, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, int metadata)
    {
        if (!world.setBlock(x, y, z, block, metadata, 3))
            return false;

        if (world.getBlock(x, y, z) == block)
        {
            block.onBlockPlacedBy(world, x, y, z, player, stack);
            block.onPostBlockPlaced(world, x, y, z, metadata);
            world.notifyBlockChange(x, y, z, block);
        }

        return true;
    }

    private MovingObjectPosition getMop()
    {
        Vec3 vec1 = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 vec2 = player.getLookVec();
        Vec3 vec3 = vec1.addVector(vec2.xCoord * 1000, vec2.yCoord * 1000, vec2.zCoord * 1000);
        MovingObjectPosition mop = player.worldObj.rayTraceBlocks(vec1, vec3);
        if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            return mop;
        return null;
    }

    /**
     * Gets the x-coordinate of the block that the player is looking on.
     *
     * @return The x-coordinate or -1 if the player isn't looking on any block
     */
    public int getLookX()
    {
        MovingObjectPosition mop = getMop();
        return mop != null ? mop.blockX : -1;
    }

    /**
     * Gets the y-coordinate of the block that the player is looking on.
     *
     * @return The y-coordinate or -1 if the player isn't looking on any block
     */
    public int getLookY()
    {
        MovingObjectPosition mop = getMop();
        return mop != null ? mop.blockY : -1;
    }

    /**
     * Gets the z-coordinate of the block that the player is looking on.
     *
     * @return The z-coordinate or -1 if the player isn't looking on any block
     */
    public int getLookZ()
    {
        MovingObjectPosition mop = getMop();
        return mop != null ? mop.blockZ : -1;
    }

    /**
     * Adds experience to the player
     *
     * @param amount The amount of xp
     */
    public void addExperience(int amount)
    {
        player.addExperience(amount);
    }

    /**
     * Removes experience from the player
     *
     * @param amount The amount of xp to remove
     */
    public void removeExperience(int amount)
    {
        player.experienceTotal -= amount;
        if (player.experienceTotal < 0)
        {
            player.experienceTotal = 0;
        }
    }

    /**
     * Sets the player's experience
     *
     * @param amount The new xp of the player
     */
    public void setExperience(int amount)
    {
        player.experienceTotal = amount;
        if (player.experienceTotal < 0)
        {
            player.experienceTotal = 0;
        }
    }

    /**
     * Adds level to the player
     *
     * @param amount The amount of levels
     */
    public void addExperienceLevel(int amount)
    {
        player.addExperienceLevel(amount);
    }

    /**
     * Removes level from the player
     *
     * @param amount The amount of levels
     */
    public void removeExperienceLevel(int amount)
    {
        player.addExperienceLevel(-amount);
    }

    /**
     * Sets the player's level
     *
     * @param level The level
     */
    public void setExperienceLevel(int level)
    {
        player.experienceLevel = level;
    }

    /**
     * Gets the player's experience
     *
     * @return
     */
    public int getExperience()
    {
        return player.experienceTotal;
    }

    /**
     * Gets the player's level
     *
     * @return
     */
    public int getExperienceLevel()
    {
        return player.experienceLevel;
    }

    /**
     * Gets the player's horizontal look angle
     *
     * @return
     */
    public float getHorizontalAngle()
    {
        return MathHelper.wrapAngleTo180_float(player.rotationYaw);
    }

    /**
     * Gets the player's vertical look angle
     *
     * @return
     */
    public float getVerticalAngle()
    {
        return MathHelper.wrapAngleTo180_float(player.rotationPitch);
    }

    /**
     * Gets the player's username
     *
     * @return
     */
    public String getUsername()
    {
        return player.getCommandSenderName();
    }

    /**
     * Check if the player is sneaking
     *
     * @return
     */
    public boolean isSneaking()
    {
        return player.isSneaking();
    }

    /**
     * Checks if the player is sprinting
     *
     * @return
     */
    public boolean isSprinting()
    {
        return player.isSprinting();
    }

    /**
     * Sets the player's max health
     *
     * @param value The new max health
     */
    public void setMaxHealth(int value)
    {
        value = value < 1 ? 1 : value;
        player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(value);
    }


    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        player.swingItem();
    }

    /**
     * Get if the player is in creative mode
     *
     * @return true if the player is in creative mode false otherwise
     */
    public boolean isInCreative()
    {
        return player.capabilities.isCreativeMode;
    }

    /**
     * Damages the item currently hold by the player.
     *
     * @param amount The amount of damage to add to the item
     */
    public void damageCurrentItem(int amount)
    {
        getInventory().damageItem(getCurrentSlot(), amount);
    }

    /**
     * Gets the players inventory.
     *
     * @return
     */
    public ScriptableInventory getInventory()
    {
        return new ScriptableInventory(player.inventory);
    }

    public void openEnderChest(String customName)
    {
        if (!player.worldObj.isRemote)
        {
            WrappedInventory inv = new WrappedInventory(player.getInventoryEnderChest());
            inv.customName = customName;
            player.displayGUIChest(inv);
        }
    }

    public void openEnderChest()
    {
        openEnderChest(null);
    }
}
