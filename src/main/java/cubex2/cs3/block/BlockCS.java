package cubex2.cs3.block;

import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

public class BlockCS extends Block implements IBlockCS
{
    protected BaseContentPack pack;
    protected WrappedBlock wrappedBlock;
    protected BlockAttributes container;

    public BlockCS(WrappedBlock block)
    {
        super(block.container.material);
        pack = block.getPack();
        wrappedBlock = block;
        container = wrappedBlock.container;
    }

    public int getMetaForFlowerGen(int itemMeta)
    {
        return itemMeta;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return wrappedBlock != null && wrappedBlock.isOpaqueCube();
    }

    @Override
    public int getRenderBlockPass()
    {
        return wrappedBlock.getRenderBlockPass();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return wrappedBlock.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return wrappedBlock.hasTileEntity(metadata);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return wrappedBlock.createTileEntity(world, metadata);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        wrappedBlock.updateTick(world, x, y, z, rand);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willDestroy)
    {
        boolean removed = world.setBlockToAir(x, y, z);
        wrappedBlock.removedByPlayer(world, player, x, y, z);
        return removed;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        wrappedBlock.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        wrappedBlock.onBlockAdded(world, x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        wrappedBlock.onBlockBreak(world, x, y, z, block, meta);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int id, int data)
    {
        super.onBlockEventReceived(world, x, y, z, id, data);
        return wrappedBlock.onBlockEventReceived(world, x, y, z, id, data);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ)
    {
        return wrappedBlock.blockActivated(world, x, y, z, player, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        wrappedBlock.onEntityWalking(world, x, y, z, entity);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        wrappedBlock.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ);
        return metadata;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        wrappedBlock.onBlockClicked(world, x, y, z, player);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        wrappedBlock.onEntityCollidedWithBlock(world, x, y, z, entity);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        wrappedBlock.onBlockPlacedBy(world, x, y, z, living);
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance)
    {
        wrappedBlock.onFallenUpon(world, x, y, z, entity, fallDistance);
    }

    public boolean onBonemeal(World world, int x, int y, int z, EntityPlayer player)
    {
        return wrappedBlock.onBonemeal(world, x, y, z, player);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        wrappedBlock.randomDisplayTick(world, x, y, z, random);
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z)
    {
        return wrappedBlock.isWood(world, x, y, z);
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return wrappedBlock.canSustainLeaves(world, x, y, z);
    }

    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
        return wrappedBlock.canSilkHarvest(world, player, x, y, z, metadata);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return wrappedBlock.container.hasCollision ? super.getCollisionBoundingBoxFromPool(world, x, y, z) : null;
    }

    @Override
    public int tickRate(World world)
    {
        return wrappedBlock.tickRate(world);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return wrappedBlock.getLightValue(world, x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        return wrappedBlock.getDrops(world, x, y, z, metadata, fortune);
    }

    @Override
    public int getExpDrop(IBlockAccess world, int metadata, int fortune)
    {
        return wrappedBlock.getExpDrop(world, metadata, fortune);
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return wrappedBlock.getFlammability(world, x, y, z, face);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return wrappedBlock.getFireSpreadSpeed(world, x, y, z, face);
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return canPlaceBlockAt(world, x, y, z) && wrappedBlock.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
        return wrappedBlock.getHardness(world.getBlockMetadata(x, y, z));
    }

    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
        return wrappedBlock.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public boolean isBurning(IBlockAccess world, int x, int y, int z)
    {
        return wrappedBlock.isBurning(world, x, y, z);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return wrappedBlock.isBeaconBase(worldObj, x, y, z, beaconX, beaconY, beaconZ);
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
    {
        return wrappedBlock.isFireSource(world, x, y, z, side);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        return wrappedBlock.getPickBlock(target, world, x, y, z);
    }

    @Override
    public int getMobilityFlag()
    {
        int mobility = wrappedBlock.getMobilityFlag();
        return mobility >= 0 ? mobility : super.getMobilityFlag();
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return wrappedBlock.getCreativeTabToDisplayOn();
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return wrappedBlock.getIcon(side, meta);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        wrappedBlock.registerBlockIcons(iconRegister);
    }

    @Override
    public WrappedBlock getWrappedBlock()
    {
        return wrappedBlock;
    }
}
