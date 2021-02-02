package cubex2.cs3.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.BlockCSPane;
import cubex2.cs3.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class CSPaneRenderer implements ISimpleBlockRenderingHandler
{

    @Override
    public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
    {
        if (modelID == getRenderId())
        {

        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == getRenderId())
        {
            BlockCSPane blockPane = (BlockCSPane) block;
            int l = world.getHeight();
            Tessellator tessellator = Tessellator.instance;
            tessellator.setBrightness(blockPane.getMixedBrightnessForBlock(world, x, y, z));
            float f = 1.0F;
            int i1 = blockPane.colorMultiplier(world, x, y, z);
            float f1 = (i1 >> 16 & 255) / 255.0F;
            float f2 = (i1 >> 8 & 255) / 255.0F;
            float f3 = (i1 & 255) / 255.0F;

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
            IIcon icon;
            IIcon icon1;
            int j1;

            if (renderer.hasOverrideBlockTexture())
            {
                icon = renderer.overrideBlockTexture;
                icon1 = renderer.overrideBlockTexture;
            }
            else
            {
                j1 = world.getBlockMetadata(x, y, z);
                icon = renderer.getBlockIconFromSideAndMetadata(blockPane, 0, j1);
                icon1 = icon;
            }

            double d0 = icon.getMinU();
            double d1 = icon.getInterpolatedU(8.0D);
            double d2 = icon.getMaxU();
            double d3 = icon.getMinV();
            double d4 = icon.getMaxV();
            double d5 = icon1.getInterpolatedU(7.0D);
            double d6 = icon1.getInterpolatedU(9.0D);
            double d7 = icon1.getMinV();
            double d8 = icon1.getInterpolatedV(8.0D);
            double d9 = icon1.getMaxV();
            double d10 = x;
            double d11 = x + 0.5D;
            double d12 = x + 1;
            double d13 = z;
            double d14 = z + 0.5D;
            double d15 = z + 1;
            double d16 = x + 0.5D - 0.0625D;
            double d17 = x + 0.5D + 0.0625D;
            double d18 = z + 0.5D - 0.0625D;
            double d19 = z + 0.5D + 0.0625D;
            boolean flag = blockPane.canThisPaneConnectToThisBlockID(world.getBlock(x, y, z - 1));
            boolean flag1 = blockPane.canThisPaneConnectToThisBlockID(world.getBlock(x, y, z + 1));
            boolean flag2 = blockPane.canThisPaneConnectToThisBlockID(world.getBlock(x - 1, y, z));
            boolean flag3 = blockPane.canThisPaneConnectToThisBlockID(world.getBlock(x + 1, y, z));
            boolean flag4 = blockPane.shouldSideBeRendered(world, x, y + 1, z, 1);
            boolean flag5 = blockPane.shouldSideBeRendered(world, x, y - 1, z, 0);

            if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1))
            {
                if (flag2 && !flag3)
                {
                    tessellator.addVertexWithUV(d10, y + 1, d14, d0, d3);
                    tessellator.addVertexWithUV(d10, y + 0, d14, d0, d4);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
                    tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
                    tessellator.addVertexWithUV(d11, y + 1, d14, d0, d3);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d0, d4);
                    tessellator.addVertexWithUV(d10, y + 0, d14, d1, d4);
                    tessellator.addVertexWithUV(d10, y + 1, d14, d1, d3);

                    if (!flag1 && !flag)
                    {
                        tessellator.addVertexWithUV(d11, y + 1, d19, d5, d7);
                        tessellator.addVertexWithUV(d11, y + 0, d19, d5, d9);
                        tessellator.addVertexWithUV(d11, y + 0, d18, d6, d9);
                        tessellator.addVertexWithUV(d11, y + 1, d18, d6, d7);
                        tessellator.addVertexWithUV(d11, y + 1, d18, d5, d7);
                        tessellator.addVertexWithUV(d11, y + 0, d18, d5, d9);
                        tessellator.addVertexWithUV(d11, y + 0, d19, d6, d9);
                        tessellator.addVertexWithUV(d11, y + 1, d19, d6, d7);
                    }

                    if (flag4 || y < l - 1 && world.isAirBlock(x - 1, y + 1, z))
                    {
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d8);
                    }

                    if (flag5 || y > 1 && world.isAirBlock(x - 1, y - 1, z))
                    {
                        tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d8);
                    }
                }
                else if (!flag2 && flag3)
                {
                    tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
                    tessellator.addVertexWithUV(d12, y + 0, d14, d2, d4);
                    tessellator.addVertexWithUV(d12, y + 1, d14, d2, d3);
                    tessellator.addVertexWithUV(d12, y + 1, d14, d1, d3);
                    tessellator.addVertexWithUV(d12, y + 0, d14, d1, d4);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d2, d4);
                    tessellator.addVertexWithUV(d11, y + 1, d14, d2, d3);

                    if (!flag1 && !flag)
                    {
                        tessellator.addVertexWithUV(d11, y + 1, d18, d5, d7);
                        tessellator.addVertexWithUV(d11, y + 0, d18, d5, d9);
                        tessellator.addVertexWithUV(d11, y + 0, d19, d6, d9);
                        tessellator.addVertexWithUV(d11, y + 1, d19, d6, d7);
                        tessellator.addVertexWithUV(d11, y + 1, d19, d5, d7);
                        tessellator.addVertexWithUV(d11, y + 0, d19, d5, d9);
                        tessellator.addVertexWithUV(d11, y + 0, d18, d6, d9);
                        tessellator.addVertexWithUV(d11, y + 1, d18, d6, d7);
                    }

                    if (flag4 || y < l - 1 && renderer.blockAccess.isAirBlock(x + 1, y + 1, z))
                    {
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d7);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d7);
                    }

                    if (flag5 || y > 1 && renderer.blockAccess.isAirBlock(x + 1, y - 1, z))
                    {
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d7);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d7);
                    }
                }
            }
            else
            {
                tessellator.addVertexWithUV(d10, y + 1, d14, d0, d3);
                tessellator.addVertexWithUV(d10, y + 0, d14, d0, d4);
                tessellator.addVertexWithUV(d12, y + 0, d14, d2, d4);
                tessellator.addVertexWithUV(d12, y + 1, d14, d2, d3);
                tessellator.addVertexWithUV(d12, y + 1, d14, d0, d3);
                tessellator.addVertexWithUV(d12, y + 0, d14, d0, d4);
                tessellator.addVertexWithUV(d10, y + 0, d14, d2, d4);
                tessellator.addVertexWithUV(d10, y + 1, d14, d2, d3);

                if (flag4)
                {
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d9);
                    tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d9);
                }
                else
                {
                    if (y < l - 1 && renderer.blockAccess.isAirBlock(x - 1, y + 1, z))
                    {
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d8);
                    }

                    if (y < l - 1 && renderer.blockAccess.isAirBlock(x + 1, y + 1, z))
                    {
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d7);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d7);
                    }
                }

                if (flag5)
                {
                    tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d9);
                    tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d9);
                }
                else
                {
                    if (y > 1 && renderer.blockAccess.isAirBlock(x - 1, y - 1, z))
                    {
                        tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d9);
                        tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d9);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d8);
                    }

                    if (y > 1 && renderer.blockAccess.isAirBlock(x + 1, y - 1, z))
                    {
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d7);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d7);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d8);
                        tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d8);
                        tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d7);
                    }
                }
            }

            if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))
            {
                if (flag && !flag1)
                {
                    tessellator.addVertexWithUV(d11, y + 1, d13, d0, d3);
                    tessellator.addVertexWithUV(d11, y + 0, d13, d0, d4);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
                    tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
                    tessellator.addVertexWithUV(d11, y + 1, d14, d0, d3);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d0, d4);
                    tessellator.addVertexWithUV(d11, y + 0, d13, d1, d4);
                    tessellator.addVertexWithUV(d11, y + 1, d13, d1, d3);

                    if (!flag3 && !flag2)
                    {
                        tessellator.addVertexWithUV(d16, y + 1, d14, d5, d7);
                        tessellator.addVertexWithUV(d16, y + 0, d14, d5, d9);
                        tessellator.addVertexWithUV(d17, y + 0, d14, d6, d9);
                        tessellator.addVertexWithUV(d17, y + 1, d14, d6, d7);
                        tessellator.addVertexWithUV(d17, y + 1, d14, d5, d7);
                        tessellator.addVertexWithUV(d17, y + 0, d14, d5, d9);
                        tessellator.addVertexWithUV(d16, y + 0, d14, d6, d9);
                        tessellator.addVertexWithUV(d16, y + 1, d14, d6, d7);
                    }

                    if (flag4 || y < l - 1 && renderer.blockAccess.isAirBlock(x, y + 1, z - 1))
                    {
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d6, d7);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d5, d7);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d6, d7);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d6, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d5, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d5, d7);
                    }

                    if (flag5 || y > 1 && renderer.blockAccess.isAirBlock(x, y - 1, z - 1))
                    {
                        tessellator.addVertexWithUV(d16, y - 0.005D, d13, d6, d7);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d13, d5, d7);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d6, d7);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d13, d6, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d13, d5, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d5, d7);
                    }
                }
                else if (!flag && flag1)
                {
                    tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
                    tessellator.addVertexWithUV(d11, y + 0, d15, d2, d4);
                    tessellator.addVertexWithUV(d11, y + 1, d15, d2, d3);
                    tessellator.addVertexWithUV(d11, y + 1, d15, d1, d3);
                    tessellator.addVertexWithUV(d11, y + 0, d15, d1, d4);
                    tessellator.addVertexWithUV(d11, y + 0, d14, d2, d4);
                    tessellator.addVertexWithUV(d11, y + 1, d14, d2, d3);

                    if (!flag3 && !flag2)
                    {
                        tessellator.addVertexWithUV(d17, y + 1, d14, d5, d7);
                        tessellator.addVertexWithUV(d17, y + 0, d14, d5, d9);
                        tessellator.addVertexWithUV(d16, y + 0, d14, d6, d9);
                        tessellator.addVertexWithUV(d16, y + 1, d14, d6, d7);
                        tessellator.addVertexWithUV(d16, y + 1, d14, d5, d7);
                        tessellator.addVertexWithUV(d16, y + 0, d14, d5, d9);
                        tessellator.addVertexWithUV(d17, y + 0, d14, d6, d9);
                        tessellator.addVertexWithUV(d17, y + 1, d14, d6, d7);
                    }

                    if (flag4 || y < l - 1 && renderer.blockAccess.isAirBlock(x, y + 1, z + 1))
                    {
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d8);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d6, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d8);
                    }

                    if (flag5 || y > 1 && renderer.blockAccess.isAirBlock(x, y - 1, z + 1))
                    {
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d8);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d6, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d8);
                    }
                }
            }
            else
            {
                tessellator.addVertexWithUV(d11, y + 1, d15, d0, d3);
                tessellator.addVertexWithUV(d11, y + 0, d15, d0, d4);
                tessellator.addVertexWithUV(d11, y + 0, d13, d2, d4);
                tessellator.addVertexWithUV(d11, y + 1, d13, d2, d3);
                tessellator.addVertexWithUV(d11, y + 1, d13, d0, d3);
                tessellator.addVertexWithUV(d11, y + 0, d13, d0, d4);
                tessellator.addVertexWithUV(d11, y + 0, d15, d2, d4);
                tessellator.addVertexWithUV(d11, y + 1, d15, d2, d3);

                if (flag4)
                {
                    tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d9);
                    tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d6, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d9);
                    tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d6, d9);
                    tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d5, d9);
                }
                else
                {
                    if (y < l - 1 && renderer.blockAccess.isAirBlock(x, y + 1, z - 1))
                    {
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d6, d7);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d5, d7);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d6, d7);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d6, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d5, d8);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d5, d7);
                    }

                    if (y < l - 1 && renderer.blockAccess.isAirBlock(x, y + 1, z + 1))
                    {
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d8);
                        tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d6, d9);
                        tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d8);
                    }
                }

                if (flag5)
                {
                    tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d9);
                    tessellator.addVertexWithUV(d17, y - 0.005D, d13, d6, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d9);
                    tessellator.addVertexWithUV(d17, y - 0.005D, d13, d6, d9);
                    tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d5, d9);
                }
                else
                {
                    if (y > 1 && renderer.blockAccess.isAirBlock(x, y - 1, z - 1))
                    {
                        tessellator.addVertexWithUV(d16, y - 0.005D, d13, d6, d7);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d13, d5, d7);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d6, d7);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d13, d6, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d13, d5, d8);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d5, d7);
                    }

                    if (y > 1 && renderer.blockAccess.isAirBlock(x, y - 1, z + 1))
                    {
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d8);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d6, d8);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d8);
                        tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d14, d6, d9);
                        tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d8);
                    }
                }
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
        return RenderIds.paneRenderId;
    }

}
