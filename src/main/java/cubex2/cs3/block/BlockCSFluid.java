package cubex2.cs3.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.attributes.FluidAttributes;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;
import java.util.Random;

public class BlockCSFluid extends BlockFluidClassic implements IBlockCS
{
    protected BaseContentPack pack;
    protected WrappedBlock wrappedBlock;
    protected FluidAttributes container;

    public BlockCSFluid(WrappedBlock block)
    {
        super(createFluid((FluidAttributes) block.container), block.container.material);
        pack = block.getPack();
        wrappedBlock = block;
        container = (FluidAttributes) wrappedBlock.container;
    }

    private static Fluid createFluid(FluidAttributes container)
    {
        Fluid fluid = new Fluid(container.getPack().getName());
        fluid.setLuminosity(container.light);
        fluid.setDensity(container.density);
        fluid.setViscosity(container.viscosity);
        fluid.setGaseous(container.gaseous);
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        wrappedBlock.updateTick(world, x, y, z, rand);
        if (container.infiniteSource && !isSourceBlock(world, x, y, z))
        {
            int adjacentSourceBlocks = 0;
            if (isSourceBlock(world, x + 1, y, z))
            {
                adjacentSourceBlocks++;
            }
            if (isSourceBlock(world, x - 1, y, z))
            {
                adjacentSourceBlocks++;
            }
            if (isSourceBlock(world, x, y, z + 1))
            {
                adjacentSourceBlocks++;
            }
            if (isSourceBlock(world, x, y, z - 1))
            {
                adjacentSourceBlocks++;
            }

            if (adjacentSourceBlocks >= 2)
            {
                world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            }
        }
        super.updateTick(world, x, y, z, rand);
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
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        wrappedBlock.onBlockAdded(world, x, y, z);
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        wrappedBlock.onBlockBreak(world, x, y, z, block, meta);
        super.breakBlock(world, x, y, z, block, meta);
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
        super.onEntityWalking(world, x, y, z, entity);
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
        super.onBlockClicked(world, x, y, z, player);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        wrappedBlock.onEntityCollidedWithBlock(world, x, y, z, entity);
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        wrappedBlock.onBlockPlacedBy(world, x, y, z, living);
        super.onBlockPlacedBy(world, x, y, z, living, stack);
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance)
    {
        wrappedBlock.onFallenUpon(world, x, y, z, entity, fallDistance);
        super.onFallenUpon(world, x, y, z, entity, fallDistance);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        wrappedBlock.randomDisplayTick(world, x, y, z, random);
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return side != 0 && side != 1 ? container.textureFlowing.icon : container.textureStill.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        wrappedBlock.registerBlockIcons(iconRegister);

        stack.getFluid().setStillIcon(container.textureStill.icon);
        stack.getFluid().setFlowingIcon(container.textureFlowing.icon);
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
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        //wrappedBlock.getSubBlocks(item, tab, list);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return wrappedBlock.getCreativeTabToDisplayOn();
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
    {
        return wrappedBlock.isFireSource(world, x, y, z, side);

    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
        return wrappedBlock.getFlammability(world, x, y, z, side);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
        return wrappedBlock.getFireSpreadSpeed(world, x, y, z, side);
    }

    @Override
    public boolean isBurning(IBlockAccess world, int x, int y, int z)
    {
        return wrappedBlock.isBurning(world, x, y, z);
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return wrappedBlock.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public WrappedBlock getWrappedBlock()
    {
        return wrappedBlock;
    }
}
