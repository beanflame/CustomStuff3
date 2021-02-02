package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class CSFlatRenderer implements ISimpleBlockRenderingHandler
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
            Tessellator tessellator = Tessellator.instance;
            IIcon icon = renderer.getBlockIconFromSide(block, 0);

            if (renderer.hasOverrideBlockTexture())
            {
                icon = renderer.overrideBlockTexture;
            }

            float f = 1.0F;
            tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
            int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
            float f1 = (l >> 16 & 255) / 255.0F;
            float f2 = (l >> 8 & 255) / 255.0F;
            float f3 = (l & 255) / 255.0F;
            tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
            icon.getMinU();
            icon.getMinV();
            icon.getMaxU();
            icon.getMaxV();
            double d4 = 0.015625D;
            int meta = renderer.blockAccess.getBlockMetadata(x, y, z) & 7;

            if (meta == 0)
            {
                renderer.renderFaceYNeg(block, x, y + 1 - d4, z, icon);
            }
            else if (meta == 1)
            {
                renderer.renderFaceYPos(block, x, y - 1 + d4, z, icon);
            }
            else if (meta == 2)
            {
                renderer.renderFaceZNeg(block, x, y, z + 1 - d4, icon);
            }
            else if (meta == 3)
            {
                renderer.renderFaceZPos(block, x, y, z - 1 + d4, icon);
            }
            else if (meta == 4)
            {
                renderer.renderFaceXNeg(block, x + 1 - d4, y, z, icon);
            }
            else if (meta == 5)
            {
                renderer.renderFaceXPos(block, x - 1 + d4, y, z, icon);
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
        return RenderIds.flatRenderId;
    }

}
