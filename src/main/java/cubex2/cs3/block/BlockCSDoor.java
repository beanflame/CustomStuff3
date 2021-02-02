package cubex2.cs3.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.attributes.DoorAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockCSDoor extends BlockCS
{
    private DoorAttributes container;

    private IIcon flippedBottomFront;
    private IIcon flippedTopFront;

    public BlockCSDoor(WrappedBlock block)
    {
        super(block);
        container = (DoorAttributes) block.container;
        disableStats();

        float f = 0.5F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public boolean normalBlockShading()
    {
        return container.normalBlockShading;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        if (side == 0)
            return container.textureBottom.icon;
        if (side == 1)
            return container.textureTop.icon;

        int i1 = getFullMetadata(world, x, y, z);
        int j1 = i1 & 3;
        boolean isOpen = (i1 & 4) != 0;
        boolean flipped = false;
        boolean isTopPart = (i1 & 8) != 0;

        if (isOpen)
        {
            if ((j1 == 0 || j1 == 2) && (side == 4 || side == 5))
            {
                return isTopPart ? container.textureSouth.icon : container.textureWest.icon;
            }
            if ((j1 == 1 || j1 == 3) && (side == 2 || side == 3))
            {
                return isTopPart ? container.textureSouth.icon : container.textureWest.icon;
            }
            if (j1 == 0 && side == 2)
            {
                flipped = true;
            } else if (j1 == 1 && side == 5)
            {
                flipped = true;
            } else if (j1 == 2 && side == 3)
            {
                flipped = true;
            } else if (j1 == 3 && side == 4)
            {
                flipped = true;
            }
        } else
        {
            if ((j1 == 0 || j1 == 2) && (side == 2 || side == 3))
            {
                return isTopPart ? container.textureSouth.icon : container.textureWest.icon;
            }
            if ((j1 == 1 || j1 == 3) && (side == 4 || side == 5))
            {
                return isTopPart ? container.textureSouth.icon : container.textureWest.icon;
            }
            if (j1 == 0 && side == 5)
            {
                flipped = true;
            } else if (j1 == 1 && side == 3)
            {
                flipped = true;
            } else if (j1 == 2 && side == 4)
            {
                flipped = true;
            } else if (j1 == 3 && side == 2)
            {
                flipped = true;
            }

            if ((i1 & 16) != 0)
            {
                flipped = !flipped;
            }
        }

        return isTopPart ? (flipped ? flippedTopFront : container.textureNorth.icon) : (flipped ? flippedBottomFront : container.textureEast.icon);


    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
    {
        int l = this.getFullMetadata(world, x, y, z);
        return (l & 4) != 0;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.doorRenderId;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        this.setDoorRotation(this.getFullMetadata(world, x, y, z));
    }

    public int getDoorOrientation(IBlockAccess world, int x, int y, int z)
    {
        return this.getFullMetadata(world, x, y, z) & 3;
    }

    public boolean isDoorOpen(IBlockAccess world, int x, int y, int z)
    {
        return (this.getFullMetadata(world, x, y, z) & 4) != 0;
    }

    private void setDoorRotation(int par1)
    {
        float f = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int j = par1 & 3;
        boolean flag = (par1 & 4) != 0;
        boolean flag1 = (par1 & 16) != 0;

        if (j == 0)
        {
            if (!flag)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            } else if (!flag1)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            } else
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
        } else if (j == 1)
        {
            if (!flag)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            } else if (!flag1)
            {
                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        } else if (j == 2)
        {
            if (!flag)
            {
                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else if (!flag1)
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            } else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
        } else if (j == 3)
        {
            if (!flag)
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            } else if (!flag1)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            } else
            {
                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ)
    {
        super.onBlockActivated(world, x, y, z, player, facing, hitX, hitY, hitZ);
        if (container.redstoneOnly)
            return false;
        else
        {
            int i = this.getFullMetadata(world, x, y, z);
            int j = i & 7;
            j ^= 4;

            if ((i & 8) != 0)
            {
                world.setBlockMetadataWithNotify(x, y - 1, z, j, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
            } else
            {
                world.setBlockMetadataWithNotify(x, y, z, j, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            }

            world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
            return true;
        }
    }

    public void onPoweredBlockChange(World world, int x, int y, int z, boolean par5)
    {
        int l = this.getFullMetadata(world, x, y, z);
        boolean flag1 = (l & 4) != 0;

        if (flag1 != par5)
        {
            int i = l & 7;
            i ^= 4;

            if ((l & 8) == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, i, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            } else
            {
                world.setBlockMetadataWithNotify(x, y - 1, z, i, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
            }

            world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        int i1 = world.getBlockMetadata(x, y, z);

        if ((i1 & 8) == 0)
        {
            boolean flag = false;

            if (world.getBlock(x, y + 1, z) != this)
            {
                world.setBlockToAir(x, y, z);
                flag = true;
            }

            if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z))
            {
                world.setBlockToAir(x, y, z);
                flag = true;

                if (world.getBlock(x, y + 1, z) == this)
                {
                    world.setBlockToAir(x, y + 1, z);
                }
            }

            if (flag)
            {
                if (!world.isRemote)
                {
                    this.dropBlockAsItem(world, x, y, z, i1, 0);
                }
            } else
            {
                boolean flag1 = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);

                if ((flag1 || neighborBlock != Blocks.air && neighborBlock.canProvidePower()) && neighborBlock != this)
                {
                    this.onPoweredBlockChange(world, x, y, z, flag1);
                }
            }
        } else
        {
            if (world.getBlock(x, y - 1, z) != this)
            {
                world.setBlockToAir(x, y, z);
            }

            if (neighborBlock != Blocks.air && neighborBlock != this)
            {
                this.onNeighborBlockChange(world, x, y - 1, z, neighborBlock);
            }
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        return (metadata & 8) != 0 ? new ArrayList<ItemStack>() : super.getDrops(world, x, y, z, metadata, fortune);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 par5Vec3D, Vec3 par6Vec3D)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, par5Vec3D, par6Vec3D);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return y >= 255 ? false : World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public int getMobilityFlag()
    {
        return 1;
    }

    public int getFullMetadata(IBlockAccess world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        boolean flag = (l & 8) != 0;
        int i;
        int j;

        if (flag)
        {
            i = world.getBlockMetadata(x, y - 1, z);
            j = l;
        } else
        {
            i = l;
            j = world.getBlockMetadata(x, y + 1, z);
        }

        boolean flag1 = (j & 1) != 0;
        return i & 7 | (flag ? 8 : 0) | (flag1 ? 16 : 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        super.registerBlockIcons(iconRegister);

        flippedTopFront = new IconFlipped(container.getTexture("top front").icon, true, false);
        flippedBottomFront = new IconFlipped(container.getTexture("bottom front").icon, true, false);
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && (meta & 8) != 0 && world.getBlock(x, y - 1, z) == this)
        {
            world.setBlockToAir(x, y - 1, z);
        }
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return GameRegistry.findItem(container.getPack().id, wrappedBlock.getName());
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        if (container.addToCreative)
        {
            list.add(new ItemStack(GameRegistry.findItem(container.getPack().id, wrappedBlock.getName())));
        }
    }
}
