package cubex2.cs3.common;

import com.google.common.base.Predicate;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.block.EnumBlockType;
import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.common.scripting.TriggerData;
import cubex2.cs3.util.GeneralHelper;
import cubex2.cs3.util.IconWrapper;
import cubex2.cs3.util.JavaScriptHelper;
import cubex2.cs3.util.PostponableTask;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Field;
import java.util.*;

public class WrappedBlock extends AttributeContent implements Comparable<WrappedBlock>
{
    public Block block;
    public Item blockItem;
    public BlockAttributes container;

    private EnumBlockType type;

    private Random random = new Random();

    public WrappedBlock(String name, EnumBlockType type, BaseContentPack pack)
    {
        super(name, pack);
        this.type = type;
    }

    public WrappedBlock(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public AttributeContainer getContainer()
    {
        return container;
    }

    @Override
    public String getTypeString()
    {
        return type.name;
    }

    public EnumBlockType getType()
    {
        return type;
    }

    private void initBlock()
    {
        block.setBlockName(name);
        block.setLightOpacity(container.opacity);
        blockItem.setMaxStackSize(container.maxStack);
        block.setStepSound(container.stepSound);
        block.slipperiness = container.slipperiness;

        if (container.toolClass != null)
        {
            block.setHarvestLevel(container.toolClass, container.harvestLevel);
        }

        Map<String, Properties> modLangData = ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), "modLanguageData");
        Properties p = modLangData.get("en_US");
        p.put("tile." + getName() + ".name", container.displayName);
    }

    @Override
    public boolean canEdit()
    {
        return block != null;
    }

    @Override
    public void apply()
    {
        if (block != null)
        {
            initBlock();
        }

        super.apply();
    }

    @Override
    public boolean readFromNBT(final NBTTagCompound compound)
    {
        name = compound.getString("Name");
        type = EnumBlockType.get(compound.getString("Type"));

        container = type.createAttributeContainer(this);
        container.loadFromNBT(compound.getCompoundTag("Attributes"), false);
        pack.postponeHandler.addTask(new PostponableTask()
        {
            @Override
            protected boolean doWork()
            {
                container.loadFromNBT(compound.getCompoundTag("Attributes"), true);
                return true;
            }
        });

        block = type.createBlock(this);
        blockItem = GameData.getItemRegistry().getObject(pack.id + ":" + getName());

        container.postCreateBlock(block);

        return true;
    }

    /* Block methods */
    public boolean isOpaqueCube()
    {
        return !container.transparent && !container.semiTransparent;
    }

    public int getRenderBlockPass()
    {
        return container.semiTransparent ? 1 : 0;
    }

    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        if (!container.tileTransparent)
        {
            Block block = world.getBlock(x, y, z);
            return block != this.block && shouldSideBeRenderedDefault(world, x, y, z, side);
        }
        return shouldSideBeRenderedDefault(world, x, y, z, side);
    }

    public boolean shouldSideBeRenderedDefault(IBlockAccess world, int x, int y, int z, int side)
    {
        return side == 0 && block.getBlockBoundsMinY() > 0.0D ? true : side == 1 && block.getBlockBoundsMaxY() < 1.0D ? true : side == 2 && block.getBlockBoundsMinZ() > 0.0D ? true : side == 3 && block.getBlockBoundsMaxZ() < 1.0D ? true : side == 4 && block.getBlockBoundsMinX() > 0.0D ? true : side == 5 && block.getBlockBoundsMaxX() < 1.0D ? true : !world.getBlock(x, y, z).isOpaqueCube();
    }

    public boolean hasTileEntity(int metadata)
    {
        return container.tileEntity != null;
    }

    public TileEntity createTileEntity(World world, int metadata)
    {
        return container.tileEntity.createTileEntity();
    }

    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (container.onUpdate != null && container.onUpdate.script != null)
        {
            ITriggerData data = new TriggerData("onUpdate", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onUpdate.script, data, pack);
            world.scheduleBlockUpdate(x, y, z, block, block.tickRate(world));
        }
    }

    public void removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        if (container.onDestroyedByPlayer != null && container.onDestroyedByPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onDestroyedByPlayer", TriggerType.BLOCK, world, x, y, z).setPlayer(player);
            JavaScriptHelper.executeTrigger(container.onDestroyedByPlayer.script, data, pack);
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        if (container.onNeighborChange != null && container.onNeighborChange.script != null)
        {
            ITriggerData data = new TriggerData("onNeighborChange", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onNeighborChange.script, data, pack);
        }
        if (container.onRedstoneSignal != null && container.onRedstoneSignal.script != null)
        {
            TriggerData data = new TriggerData("onRedstoneSignal", TriggerType.BLOCK, world, x, y, z);
            if (world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                data.setRedstoneSignal(true);
            } else
            {
                data.setRedstoneSignal(false);
            }
            JavaScriptHelper.executeTrigger(container.onRedstoneSignal.script, data, pack);
        }
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        /*if (container.hasTileEntity)
        {
            world.setTileEntity(x, y, z, block.createTileEntity(world, meta));
        }*/

        if (container.onAdded != null && container.onAdded.script != null)
        {
            ITriggerData data = new TriggerData("onAdded", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onAdded.script, data, pack);
        }
        if (container.onUpdate != null && container.onUpdate.script != null)
        {
            world.scheduleBlockUpdate(x, y, z, block, block.tickRate(world));
        }
        if (container.onRedstoneSignal != null && container.onRedstoneSignal.script != null)
        {
            TriggerData data = new TriggerData("onRedstoneSignal", TriggerType.BLOCK, world, x, y, z);
            if (world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                data.setRedstoneSignal(true);
            } else
            {
                data.setRedstoneSignal(false);
            }
            JavaScriptHelper.executeTrigger(container.onRedstoneSignal.script, data, pack);
        }
    }

    public void onBlockBreak(World world, int x, int y, int z, Block block, int meta)
    {
        if (container.onBreak != null && container.onBreak.script != null)
        {
            ITriggerData data = new TriggerData("onBreak", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onBreak.script, data, pack);
        }
        if (container.tileEntity != null)
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof IInventory)
            {
                GeneralHelper.dropItems((IInventory) te, world, x, y, z, block);
            }
            world.removeTileEntity(x, y, z);
        }
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ)
    {
        if (container.onActivated != null && container.onActivated.script != null)
        {
            ITriggerData data = new TriggerData("onActivated", TriggerType.BLOCK, world, x, y, z).setPlayer(player).setSideAndHit(facing, hitX, hitY, hitZ);
            return JavaScriptHelper.executeTrigger(container.onActivated.script, data, pack, false);
        }
        return false;
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        if (container.onWalking != null && container.onWalking.script != null)
        {
            ITriggerData data = new TriggerData("onWalking", TriggerType.BLOCK, world, x, y, z).setEntity(entity);
            JavaScriptHelper.executeTrigger(container.onWalking.script, data, pack);
        }
    }

    public void onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ)
    {
        // TODO add facing, hit?
        if (container.onPlaced != null && container.onPlaced.script != null)
        {
            ITriggerData data = new TriggerData("onPlaced", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onPlaced.script, data, pack);
        }
    }

    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        if (container.onClicked != null && container.onClicked.script != null)
        {
            ITriggerData data = new TriggerData("onClicked", TriggerType.BLOCK, world, x, y, z).setPlayer(player);
            JavaScriptHelper.executeTrigger(container.onClicked.script, data, pack);
        }
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (container.onCollided != null && container.onCollided.script != null)
        {
            ITriggerData data = new TriggerData("onCollided", TriggerType.BLOCK, world, x, y, z).setEntity(entity);
            JavaScriptHelper.executeTrigger(container.onCollided.script, data, pack);
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living)
    {
        if (living instanceof EntityPlayer && container.onPlacedByPlayer != null && container.onPlacedByPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onPlacedByPlayer", TriggerType.BLOCK, world, x, y, z).setPlayer((EntityPlayer) living);
            JavaScriptHelper.executeTrigger(container.onPlacedByPlayer.script, data, pack);
        } else if (container.onPlacedBy != null && container.onPlacedBy.script != null)
        {
            ITriggerData data = new TriggerData("onPlacedBy", TriggerType.BLOCK, world, x, y, z).setLiving(living);
            JavaScriptHelper.executeTrigger(container.onPlacedBy.script, data, pack);
        }
    }

    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance)
    {
        if (container.onFallenUpon != null && container.onFallenUpon.script != null)
        {
            ITriggerData data = new TriggerData("onFallenUpon", TriggerType.BLOCK, world, x, y, z).setEntity(entity);
            JavaScriptHelper.executeTrigger(container.onFallenUpon.script, data, pack);
        }
    }

    public boolean onBonemeal(World world, int x, int y, int z, EntityPlayer player)
    {
        if (container.onBonemeal != null && container.onBonemeal.script != null)
        {
            ITriggerData data = new TriggerData("onBonemeal", TriggerType.BLOCK, world, x, y, z).setPlayer(player);
            JavaScriptHelper.executeTrigger(container.onBonemeal.script, data, pack);
        }
        return false;
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if (container.onRandomDisplayTick != null && container.onRandomDisplayTick.script != null)
        {
            ITriggerData data = new TriggerData("onRandomDisplayTick", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onRandomDisplayTick.script, data, pack);
        }
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z)
    {
        return container.isWood;
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return container.isWood;
    }

    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
        return container.canSilkHarvest;
    }

    public int tickRate(World world)
    {
        return container.tickrate;
    }

    public float getHardness(int meta)
    {
        return container.hardness;
    }

    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
        return container.resistance / 5.0f;
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return container.light;
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return container.flammability;
    }

    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return container.fireSpreadSpeed;
    }


    /* Methods from block item */

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return !container.leaveContainerItem;
    }

    public ItemStack getContainerItem(ItemStack stack)
    {
        return container.containerItem.copy();
    }

    public IIcon getIcon(int side, int meta)
    {
        return container.getTexture(side).icon;
    }

    public void registerBlockIcons(IIconRegister iconRegister)
    {
        Field[] fields = container.getAttributeFields(new Predicate<Field>()
        {
            @Override
            public boolean apply(Field input)
            {
                return input.getType() == IconWrapper.class && input.isAnnotationPresent(Attribute.class);
            }
        });

        try
        {
            for (Field f : fields)
            {
                ((IconWrapper) f.get(container)).setIcon(iconRegister);
            }
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return container.creativeTab;
    }

    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        if (side == 0 && !container.canPlaceOnCeiling)
            return false;
        if (side == 1 && !container.canPlaceOnFloor)
            return false;

        return !(side > 1 && !container.canPlaceOnWall);
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        if (container.information == null) return;

        String[] split = container.information.split("\n");
        Collections.addAll(list, split);
    }

    public int getExpDrop(IBlockAccess world, int metadata, int fortune)
    {
        int min = container.expDropMin;
        int max = container.expDropMax;

        return random.nextInt(max + 1 - min) + min;
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        return container.drop.getDrops(container.useFortune ? fortune + container.fortuneModifier : 0, random);
    }

    public int getMobilityFlag()
    {
        return container.blocksPiston ? 2 : -1;
    }

    public boolean isBurning(IBlockAccess world, int x, int y, int z)
    {
        return container.isBurning;
    }

    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return container.isBeaconBase;
    }

    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
    {
        return container.isFireSource;
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        return container.pick;
    }

    @Override
    public int compareTo(WrappedBlock o)
    {
        return name.compareTo(o.name);
    }

    public boolean onBlockEventReceived(World world, int x, int y, int z, int id, int data)
    {
        if (container.tileEntity != null)
        {
            TileEntity tileentity = world.getTileEntity(x, y, z);
            return tileentity != null && tileentity.receiveClientEvent(id, data);
        }

        return false;
    }
}
