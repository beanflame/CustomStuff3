package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.BlockCSFence;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CSFenceRenderer implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer)
    {
        if (modelId == getRenderId())
        {
            Tessellator tessellator = Tessellator.instance;
            float var8;
            for (int i = 0; i < 4; ++i)
            {
                var8 = 0.125F;

                if (i == 0)
                {
                    renderer.setRenderBounds(0.5F - var8, 0.0D, 0.0D, 0.5F + var8, 1.0D, var8 * 2.0F);
                }

                if (i == 1)
                {
                    renderer.setRenderBounds(0.5F - var8, 0.0D, 1.0F - var8 * 2.0F, 0.5F + var8, 1.0D, 1.0D);
                }

                var8 = 0.0625F;

                if (i == 2)
                {
                    renderer.setRenderBounds(0.5F - var8, 1.0F - var8 * 3.0F, -var8 * 2.0F, 0.5F + var8, 1.0F - var8, 1.0F + var8 * 2.0F);
                }

                if (i == 3)
                {
                    renderer.setRenderBounds(0.5F - var8, 0.5F - var8 * 3.0F, -var8 * 2.0F, 0.5F + var8, 0.5F - var8, 1.0F + var8 * 2.0F);
                }

                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
                tessellator.draw();

                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }

            renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == getRenderId())
        {
            BlockCSFence fence = (BlockCSFence) block;
            boolean var5 = true;
            float f = 0.375F;
            float f1 = 0.625F;
            renderer.setRenderBounds(f, 0.0D, f, f1, 1.0D, f1);
            renderer.renderStandardBlock(fence, x, y, z);
            boolean flag1 = false;
            boolean flag2 = false;

            if (fence.canConnectFenceTo(renderer.blockAccess, x - 1, y, z) || fence.canConnectFenceTo(renderer.blockAccess, x + 1, y, z))
            {
                flag1 = true;
            }

            if (fence.canConnectFenceTo(renderer.blockAccess, x, y, z - 1) || fence.canConnectFenceTo(renderer.blockAccess, x, y, z + 1))
            {
                flag2 = true;
            }

            boolean connectXN = fence.canConnectFenceTo(renderer.blockAccess, x - 1, y, z);
            boolean connectXP = fence.canConnectFenceTo(renderer.blockAccess, x + 1, y, z);
            boolean connectZN = fence.canConnectFenceTo(renderer.blockAccess, x, y, z - 1);
            boolean connectZP = fence.canConnectFenceTo(renderer.blockAccess, x, y, z + 1);

            if (!flag1 && !flag2)
            {
                flag1 = true;
            }

            f = 0.4375F;
            f1 = 0.5625F;
            float var14 = 0.75F;
            float var15 = 0.9375F;
            float var16 = connectXN ? 0.0F : f;
            float var17 = connectXP ? 1.0F : f1;
            float var18 = connectZN ? 0.0F : f;
            float var19 = connectZP ? 1.0F : f1;

            if (flag1)
            {
                renderer.setRenderBounds(var16, var14, f, var17, var15, f1);
                renderer.renderStandardBlock(fence, x, y, z);
                var5 = true;
            }

            if (flag2)
            {
                renderer.setRenderBounds(f, var14, var18, f1, var15, var19);
                renderer.renderStandardBlock(fence, x, y, z);
                var5 = true;
            }

            var14 = 0.375F;
            var15 = 0.5625F;

            if (flag1)
            {
                renderer.setRenderBounds(var16, var14, f, var17, var15, f1);
                renderer.renderStandardBlock(fence, x, y, z);
                var5 = true;
            }

            if (flag2)
            {
                renderer.setRenderBounds(f, var14, var18, f1, var15, var19);
                renderer.renderStandardBlock(fence, x, y, z);
                var5 = true;
            }

            fence.setBlockBoundsBasedOnState(renderer.blockAccess, x, y, z);
            return var5;
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return RenderIds.fenceRenderId;
    }
}
