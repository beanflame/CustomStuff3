package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cubex2.cs3.block.BlockCSPost;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class CSCrossTexturePostRenderer implements ISimpleBlockRenderingHandler
{

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == getRenderId())
        {
            int blockMeta = world.getBlockMetadata(x, y, z);
            int meta = blockMeta & 7;

            BlockCSPost post = (BlockCSPost) block;

            float thickness = post.getThickness();

            IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, meta);

            if (renderer.hasOverrideBlockTexture())
            {
                icon = renderer.overrideBlockTexture;
            }

            double minU = icon.getMinU();
            double minV = icon.getMinV();
            double maxU = icon.getMaxU();
            double maxV = icon.getMaxV();
            double width = 0.45D * thickness;

            Tessellator tessellator = Tessellator.instance;

            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
            float f = 1.0F;
            int l = block.colorMultiplier(world, x, y, z);
            float f1 = (l >> 16 & 255) / 255.0F;
            float f2 = (l >> 8 & 255) / 255.0F;
            float f3 = (l & 255) / 255.0F;

            if (EntityRenderer.anaglyphEnable)
            {
                float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
                float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
                float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
                f1 = f4;
                f2 = f5;
                f3 = f6;
            }

            tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);

            if (meta == 0 || meta == 1)
            {
                double minX = x + 0.5D - width;
                double maxX = x + 0.5D + width;
                double minZ = z + 0.5D - width;
                double maxZ = z + 0.5D + width;

                tessellator.addVertexWithUV(minX, y + 1, minZ, minU, minV);
                tessellator.addVertexWithUV(minX, y + 0.0D, minZ, minU, maxV);
                tessellator.addVertexWithUV(maxX, y + 0.0D, maxZ, maxU, maxV);
                tessellator.addVertexWithUV(maxX, y + 1, maxZ, maxU, minV);

                tessellator.addVertexWithUV(maxX, y + 1, maxZ, minU, minV);
                tessellator.addVertexWithUV(maxX, y + 0.0D, maxZ, minU, maxV);
                tessellator.addVertexWithUV(minX, y + 0.0D, minZ, maxU, maxV);
                tessellator.addVertexWithUV(minX, y + 1, minZ, maxU, minV);

                tessellator.addVertexWithUV(minX, y + 1, maxZ, minU, minV);
                tessellator.addVertexWithUV(minX, y + 0.0D, maxZ, minU, maxV);
                tessellator.addVertexWithUV(maxX, y + 0.0D, minZ, maxU, maxV);
                tessellator.addVertexWithUV(maxX, y + 1, minZ, maxU, minV);

                tessellator.addVertexWithUV(maxX, y + 1, minZ, minU, minV);
                tessellator.addVertexWithUV(maxX, y + 0.0D, minZ, minU, maxV);
                tessellator.addVertexWithUV(minX, y + 0.0D, maxZ, maxU, maxV);
                tessellator.addVertexWithUV(minX, y + 1, maxZ, maxU, minV);
            } else if (meta == 2 || meta == 3)
            {
                double minX = x + 0.5D - width;
                double maxX = x + 0.5D + width;
                double minY = y + 0.5D - width;
                double maxY = y + 0.5D + width;

                tessellator.addVertexWithUV(minX, minY, z + 1, minU, minV);
                tessellator.addVertexWithUV(minX, minY, z + 0.0D, minU, maxV);
                tessellator.addVertexWithUV(maxX, maxY, z + 0.0D, maxU, maxV);
                tessellator.addVertexWithUV(maxX, maxY, z + 1, maxU, minV);

                tessellator.addVertexWithUV(maxX, maxY, z + 1, minU, minV);
                tessellator.addVertexWithUV(maxX, maxY, z + 0.0D, minU, maxV);
                tessellator.addVertexWithUV(minX, minY, z + 0.0D, maxU, maxV);
                tessellator.addVertexWithUV(minX, minY, z + 1, maxU, minV);

                tessellator.addVertexWithUV(minX, maxY, z + 1, minU, minV);
                tessellator.addVertexWithUV(minX, maxY, z + 0.0D, minU, maxV);
                tessellator.addVertexWithUV(maxX, minY, z + 0.0D, maxU, maxV);
                tessellator.addVertexWithUV(maxX, minY, z + 1, maxU, minV);

                tessellator.addVertexWithUV(maxX, minY, z + 1, minU, minV);
                tessellator.addVertexWithUV(maxX, minY, z + 0.0D, minU, maxV);
                tessellator.addVertexWithUV(minX, maxY, z + 0.0D, maxU, maxV);
                tessellator.addVertexWithUV(minX, maxY, z + 1, maxU, minV);
            } else if (meta == 4 || meta == 5)
            {
                double minX = x;
                double maxX = x + 1.0d;
                double minZ = z + 0.5D - width;
                double maxZ = z + 0.5D + width;
                double minY = y + 0.5D - width;
                double maxY = y + 0.5D + width;

                tessellator.addVertexWithUV(minX, minY, maxZ, minU, minV);
                tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
                tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, maxV);
                tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);

                tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
                tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, maxV);
                tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
                tessellator.addVertexWithUV(minX, minY, maxZ, minU, minV);

                tessellator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
                tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, maxV);
                tessellator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
                tessellator.addVertexWithUV(minX, minY, minZ, maxU, minV);

                tessellator.addVertexWithUV(minX, minY, minZ, maxU, minV);
                tessellator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
                tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, maxV);
                tessellator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return RenderIds.crossTexturePostRenderId;
    }

}
