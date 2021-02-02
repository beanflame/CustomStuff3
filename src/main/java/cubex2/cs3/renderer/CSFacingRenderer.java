package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cubex2.cs3.block.BlockCSFacing;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class CSFacingRenderer implements ISimpleBlockRenderingHandler
{

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (modelID == getRenderId())
        {
            metadata = metadata == 0 ? 3 : 11;
            Tessellator tessellator = Tessellator.instance;

            block.setBlockBoundsForItemRender();
            renderer.setRenderBoundsFromBlock(block);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
            tessellator.draw();

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
            tessellator.draw();

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == getRenderId())
        {
            BlockCSFacing csBlock = (BlockCSFacing)block;
            if (csBlock.rotateSideTextures())
            {
                int meta = world.getBlockMetadata(x, y, z) & 7;

                if (meta == 4 || meta == 5)
                {
                    renderer.uvRotateEast = 1;
                    renderer.uvRotateWest = 1;
                    renderer.uvRotateTop = 1;
                    renderer.uvRotateBottom = 1;
                } else if (meta == 2 || meta == 3)
                {
                    renderer.uvRotateSouth = 1;
                    renderer.uvRotateNorth = 1;
                }
            }

            boolean flag = renderer.renderStandardBlock(block, x, y, z);
            if (csBlock.rotateSideTextures())
            {
                renderer.uvRotateSouth = 0;
                renderer.uvRotateEast = 0;
                renderer.uvRotateWest = 0;
                renderer.uvRotateNorth = 0;
                renderer.uvRotateTop = 0;
                renderer.uvRotateBottom = 0;
            }
            return flag;
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
        return RenderIds.facingRenderId;
    }

}
