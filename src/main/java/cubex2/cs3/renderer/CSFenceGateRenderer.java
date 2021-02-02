package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.BlockCSFenceGate;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CSFenceGateRenderer implements ISimpleBlockRenderingHandler
{

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (modelID == getRenderId())
        {
            float f;
            Tessellator tessellator = Tessellator.instance;
            for (int i = 0; i < 3; ++i)
            {
                f = 0.0625F;

                if (i == 0)
                {
                    renderer.setRenderBounds(0.5F - f, 0.3D, 0.0D, 0.5F + f, 1.0D, f * 2.0F);
                }

                if (i == 1)
                {
                    renderer.setRenderBounds(0.5F - f, 0.3D, 1.0F - f * 2.0F, 0.5F + f, 1.0D, 1.0D);
                }

                f = 0.0625F;

                if (i == 2)
                {
                    renderer.setRenderBounds(0.5F - f, 0.5D, 0.0D, 0.5F + f, 1.0F - f, 1.0D);
                }

                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 2));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 3));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 4));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 5));
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == getRenderId())
        {
            boolean var5 = true;
            int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
            boolean isOpen = BlockCSFenceGate.isFenceGateOpen(meta);
            int direction = BlockDirectional.getDirection(meta);
            float var9 = 0.375F;
            float var10 = 0.5625F;
            float var11 = 0.75F;
            float var12 = 0.9375F;
            float var13 = 0.3125F;
            float var14 = 1.0F;

            if ((direction == 2 || direction == 0) && renderer.blockAccess.getBlock(x - 1, y, z) == Blocks.cobblestone_wall && renderer.blockAccess.getBlock(x + 1, y, z) == Blocks.cobblestone_wall || (direction == 3 || direction == 1) && renderer.blockAccess.getBlock(x, y, z - 1) == Blocks.cobblestone_wall && renderer.blockAccess.getBlock(x, y, z + 1) == Blocks.cobblestone_wall)
            {
                var9 -= 0.1875F;
                var10 -= 0.1875F;
                var11 -= 0.1875F;
                var12 -= 0.1875F;
                var13 -= 0.1875F;
                var14 -= 0.1875F;
            }

            float var15;
            float var17;
            float var16;
            float var18;

            if (direction != 3 && direction != 1)
            {
                var15 = 0.0F;
                var16 = 0.125F;
                var17 = 0.4375F;
                var18 = 0.5625F;
                renderer.setRenderBounds(var15, var13, var17, var16, var14, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var15 = 0.875F;
                var16 = 1.0F;
                renderer.setRenderBounds(var15, var13, var17, var16, var14, var18);
                renderer.renderStandardBlock(block, x, y, z);
            } else
            {
                var15 = 0.4375F;
                var16 = 0.5625F;
                var17 = 0.0F;
                var18 = 0.125F;
                renderer.setRenderBounds(var15, var13, var17, var16, var14, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var17 = 0.875F;
                var18 = 1.0F;
                renderer.setRenderBounds(var15, var13, var17, var16, var14, var18);
                renderer.renderStandardBlock(block, x, y, z);
            }

            if (isOpen)
            {
                if (direction == 3)
                {
                    renderer.setRenderBounds(0.8125D, var9, 0.0D, 0.9375D, var12, 0.125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.8125D, var9, 0.875D, 0.9375D, var12, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.5625D, var9, 0.0D, 0.8125D, var10, 0.125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.5625D, var9, 0.875D, 0.8125D, var10, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.5625D, var11, 0.0D, 0.8125D, var12, 0.125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.5625D, var11, 0.875D, 0.8125D, var12, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                } else if (direction == 1)
                {
                    renderer.setRenderBounds(0.0625D, var9, 0.0D, 0.1875D, var12, 0.125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.0625D, var9, 0.875D, 0.1875D, var12, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.1875D, var9, 0.0D, 0.4375D, var10, 0.125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.1875D, var9, 0.875D, 0.4375D, var10, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.1875D, var11, 0.0D, 0.4375D, var12, 0.125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.1875D, var11, 0.875D, 0.4375D, var12, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                } else if (direction == 0)
                {
                    renderer.setRenderBounds(0.0D, var9, 0.8125D, 0.125D, var12, 0.9375D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.875D, var9, 0.8125D, 1.0D, var12, 0.9375D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.0D, var9, 0.5625D, 0.125D, var10, 0.8125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.875D, var9, 0.5625D, 1.0D, var10, 0.8125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.0D, var11, 0.5625D, 0.125D, var12, 0.8125D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.875D, var11, 0.5625D, 1.0D, var12, 0.8125D);
                    renderer.renderStandardBlock(block, x, y, z);
                } else if (direction == 2)
                {
                    renderer.setRenderBounds(0.0D, var9, 0.0625D, 0.125D, var12, 0.1875D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.875D, var9, 0.0625D, 1.0D, var12, 0.1875D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.0D, var9, 0.1875D, 0.125D, var10, 0.4375D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.875D, var9, 0.1875D, 1.0D, var10, 0.4375D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.0D, var11, 0.1875D, 0.125D, var12, 0.4375D);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setRenderBounds(0.875D, var11, 0.1875D, 1.0D, var12, 0.4375D);
                    renderer.renderStandardBlock(block, x, y, z);
                }
            } else if (direction != 3 && direction != 1)
            {
                var15 = 0.375F;
                var16 = 0.5F;
                var17 = 0.4375F;
                var18 = 0.5625F;
                renderer.setRenderBounds(var15, var9, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var15 = 0.5F;
                var16 = 0.625F;
                renderer.setRenderBounds(var15, var9, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var15 = 0.625F;
                var16 = 0.875F;
                renderer.setRenderBounds(var15, var9, var17, var16, var10, var18);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(var15, var11, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var15 = 0.125F;
                var16 = 0.375F;
                renderer.setRenderBounds(var15, var9, var17, var16, var10, var18);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(var15, var11, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
            } else
            {
                var15 = 0.4375F;
                var16 = 0.5625F;
                var17 = 0.375F;
                var18 = 0.5F;
                renderer.setRenderBounds(var15, var9, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var17 = 0.5F;
                var18 = 0.625F;
                renderer.setRenderBounds(var15, var9, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var17 = 0.625F;
                var18 = 0.875F;
                renderer.setRenderBounds(var15, var9, var17, var16, var10, var18);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(var15, var11, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
                var17 = 0.125F;
                var18 = 0.375F;
                renderer.setRenderBounds(var15, var9, var17, var16, var10, var18);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(var15, var11, var17, var16, var12, var18);
                renderer.renderStandardBlock(block, x, y, z);
            }

            renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
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
        return RenderIds.fenceGateRenderId;
    }

}
