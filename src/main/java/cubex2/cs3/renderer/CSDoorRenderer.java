package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.BlockCSDoor;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class CSDoorRenderer implements ISimpleBlockRenderingHandler
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
            int l = renderer.blockAccess.getBlockMetadata(x, y, z);

            if ((l & 8) != 0)
            {
                if (renderer.blockAccess.getBlock(x, y - 1, z) != block)
                    return false;
            } else if (renderer.blockAccess.getBlock(x, y + 1, z) != block)
                return false;

            if (((BlockCSDoor) block).normalBlockShading())
            {
                renderer.renderStandardBlock(block, x, y, z);
                return true;
            }
            boolean top = (l & 8) != 0;

            boolean flag = false;
            float f = 0.5F;
            float f1 = 1.0F;
            float f2 = 0.8F;
            float f3 = 0.6F;
            int i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
            if (!top)
            {
                tessellator.setBrightness(renderer.renderMinY > 0.0D ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z));
                tessellator.setColorOpaque_F(f, f, f);
                renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));
            }
            flag = true;
            if (top)
            {
                tessellator.setBrightness(renderer.renderMaxY < 1.0D ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z));
                tessellator.setColorOpaque_F(f1, f1, f1);
                renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 1));
            }
            flag = true;
            tessellator.setBrightness(renderer.renderMinZ > 0.0D ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
            tessellator.setColorOpaque_F(f2, f2, f2);
            IIcon icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 2);
            renderer.renderFaceZNeg(block, x, y, z, icon);
            flag = true;
            renderer.flipTexture = false;
            tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
            tessellator.setColorOpaque_F(f2, f2, f2);
            icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3);
            renderer.renderFaceZPos(block, x, y, z, icon);
            flag = true;
            renderer.flipTexture = false;
            tessellator.setBrightness(renderer.renderMinX > 0.0D ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
            tessellator.setColorOpaque_F(f3, f3, f3);
            icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 4);
            renderer.renderFaceXNeg(block, x, y, z, icon);
            flag = true;
            renderer.flipTexture = false;
            tessellator.setBrightness(renderer.renderMaxX < 1.0D ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
            tessellator.setColorOpaque_F(f3, f3, f3);
            icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5);
            renderer.renderFaceXPos(block, x, y, z, icon);
            flag = true;
            renderer.flipTexture = false;
            return flag;
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
        return RenderIds.doorRenderId;
    }

}
