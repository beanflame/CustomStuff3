package cubex2.cs3.block;

import cubex2.cs3.block.attributes.FacingAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCSFacing extends BlockCS
{
    protected FacingAttributes container;

    public BlockCSFacing(WrappedBlock block)
    {
        super(block);
        container = (FacingAttributes) block.container;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.facingRenderId;
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        meta &= 7;
        if (side == meta)
            return container.textureFront.icon;
        else if (side == ForgeDirection.OPPOSITES[meta])
            return container.textureBack.icon;
        else
        {
            if (side == 0 && container.textureBottom.icon != null)
                return container.textureBottom.icon;
            if (side == 1 && container.textureTop.icon != null)
                return container.textureTop.icon;
            return container.textureSides.icon;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        if (!container.faceBySide)
        {
            int orientation = determineOrientation(world, x, y, z, (EntityPlayer) living);
            world.setBlockMetadataWithNotify(x, y, z, orientation, 3);
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, metadata);
        if (container.faceBySide)
        {
            metadata |= facing;
        }
        return metadata;
    }

    public int determineOrientation(World world, int x, int y, int z, EntityPlayer player)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (MathHelper.abs((float) player.posX - x) < 2.0F && MathHelper.abs((float) player.posZ - z) < 2.0F)
        {
            double var5 = player.posY + 1.82D - player.yOffset;

            if (container.canFaceTop && var5 - y > 2.0D)
                return 1 | meta;

            if (container.canFaceBottom && y - var5 > 0.0D)
                return 0 | meta;

            if (!container.canFaceSides)
                return 1 | meta;
        }

        int lookDir = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        return (lookDir == 0 ? 2 : lookDir == 1 ? 5 : lookDir == 2 ? 3 : lookDir == 3 ? 4 : 0) | meta;
    }

    public boolean rotateSideTextures()
    {
        return container.rotateSideTextures;
    }
}
