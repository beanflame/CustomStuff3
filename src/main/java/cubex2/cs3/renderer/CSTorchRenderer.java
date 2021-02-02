package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class CSTorchRenderer implements ISimpleBlockRenderingHandler
{

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (modelID == getRenderId())
        {
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderTorchAtAngle(block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D, 0);
            tessellator.draw();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == getRenderId())
        {
            int meta = world.getBlockMetadata(x, y, z);
            Tessellator tessellator = Tessellator.instance;
            tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
            tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
            double var7 = 0.4000000059604645D;
            double var9 = 0.5D - var7;
            double var11 = 0.20000000298023224D;

            if (meta == 1)
            {
                renderer.renderTorchAtAngle(block, x - var9, y + var11, z, -var7, 0.0D, 0);
            } else if (meta == 2)
            {
                renderer.renderTorchAtAngle(block, x + var9, y + var11, z, var7, 0.0D, 0);
            } else if (meta == 3)
            {
                renderer.renderTorchAtAngle(block, x, y + var11, z - var9, 0.0D, -var7, 0);
            } else if (meta == 4)
            {
                renderer.renderTorchAtAngle(block, x, y + var11, z + var9, 0.0D, var7, 0);
            } else if (meta == 5)
            {
                renderer.renderTorchAtAngle(block, x, y, z, 0.0D, 0.0D, 0);
            } else
            {
                renderTorchUpsideDown(renderer, block, x, y, z);
            }

            return true;
        }
        return false;
    }

    private void renderTorchUpsideDown(RenderBlocks renderer, Block block, double x, double y, double z)
    {
        Tessellator tessellator = Tessellator.instance;
        IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, 0);

        if (renderer.hasOverrideBlockTexture())
        {
            icon = renderer.overrideBlockTexture;
        }

        double minU = icon.getMinU();
        double minV = icon.getMinV();
        double maxU = icon.getMaxU();
        double maxV = icon.getMaxV();
        double d9 = icon.getInterpolatedU(7.0D);
        double d10 = icon.getInterpolatedV(6.0D);
        double d11 = icon.getInterpolatedU(9.0D);
        double d12 = icon.getInterpolatedV(8.0D);
        icon.getInterpolatedU(7.0D);
        icon.getInterpolatedV(13.0D);
        icon.getInterpolatedU(9.0D);
        icon.getInterpolatedV(15.0D);
        x += 0.5D;
        z += 0.5D;
        double minX = x - 0.5D;
        double maxX = x + 0.5D;
        double minZ = z - 0.5D;
        double maxZ = z + 0.5D;
        double xySize = 0.0625D;
        double topSurfaceYOffset = 1 - 0.625D;

        // top surface
        tessellator.addVertexWithUV(x + xySize, y + topSurfaceYOffset, z - xySize, d9, d10);
        tessellator.addVertexWithUV(x + xySize, y + topSurfaceYOffset, z + xySize, d9, d12);
        tessellator.addVertexWithUV(x - xySize, y + topSurfaceYOffset, z + xySize, d11, d12);
        tessellator.addVertexWithUV(x - xySize, y + topSurfaceYOffset, z - xySize, d11, d10);

        // x- surface
        tessellator.addVertexWithUV(x - xySize, y + 1.0D, minZ, minU, maxV);
        tessellator.addVertexWithUV(x - xySize, y + 0.0D, minZ, minU, minV);
        tessellator.addVertexWithUV(x - xySize, y + 0.0D, maxZ, maxU, minV);
        tessellator.addVertexWithUV(x - xySize, y + 1.0D, maxZ, maxU, maxV);

        // x+ surface
        tessellator.addVertexWithUV(x + xySize, y + 1.0D, maxZ, minU, maxV);
        tessellator.addVertexWithUV(x + xySize, y + 0.0D, maxZ, minU, minV);
        tessellator.addVertexWithUV(x + xySize, y + 0.0D, minZ, maxU, minV);
        tessellator.addVertexWithUV(x + xySize, y + 1.0D, minZ, maxU, maxV);

        // z+ surface
        tessellator.addVertexWithUV(minX, y + 1.0D, z + xySize, minU, maxV);
        tessellator.addVertexWithUV(minX, y + 0.0D, z + xySize, minU, minV);
        tessellator.addVertexWithUV(maxX, y + 0.0D, z + xySize, maxU, minV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, z + xySize, maxU, maxV);

        // z- surface
        tessellator.addVertexWithUV(maxX, y + 1.0D, z - xySize, minU, maxV);
        tessellator.addVertexWithUV(maxX, y + 0.0D, z - xySize, minU, minV);
        tessellator.addVertexWithUV(minX, y + 0.0D, z - xySize, maxU, minV);
        tessellator.addVertexWithUV(minX, y + 1.0D, z - xySize, maxU, maxV);
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return RenderIds.torchRenderId;
    }

}
