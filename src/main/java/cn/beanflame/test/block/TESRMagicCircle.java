package cn.beanflame.test.block;

import cn.beanflame.test.Test;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TESRMagicCircle extends TileEntitySpecialRenderer {


    /*
    private static final ResourceLocation field_147523_b = new ResourceLocation(
            "textures/entity/beacon_beam.png");


    public void AC_renderTileEntityAt(TEMagicCircle tileentity, double x, double y, double z, float partialTick)
    {
        float f1 = 0.0625F;//tileentity.func_146002_i();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        if (f1 > 0.0F)
        {
            Tessellator tessellator = Tessellator.instance;
            this.bindTexture(field_147523_b);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            float f2 = (float)tileentity.getWorldObj().getTotalWorldTime() + partialTick;
            float f3 = -f2 * 0.2F - (float) MathHelper.floor_float(-f2 * 0.1F);
            byte b0 = 1;
            double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(255, 255, 255, 32);
            double d5 = (double)b0 * 0.2D;
            double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
            double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
            double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
            double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
            double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
            double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
            double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
            double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
            double d23 = (double)(256.0F * f1);
            double d25 = 0.0D;
            double d27 = 1.0D;
            double d28 = (double)(-1.0F + f3);
            double d29 = (double)(256.0F * f1) * (0.5D / d5) + d28;
            tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
            tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
            tessellator.addVertexWithUV(x + d11, y, z + d13, d25, d28);
            tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d25, d29);
            tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d27, d29);
            tessellator.addVertexWithUV(x + d19, y, z + d21, d27, d28);
            tessellator.addVertexWithUV(x + d15, y, z + d17, d25, d28);
            tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d25, d29);
            tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d27, d29);
            tessellator.addVertexWithUV(x + d11, y, z + d13, d27, d28);
            tessellator.addVertexWithUV(x + d19, y, z + d21, d25, d28);
            tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d25, d29);
            tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d27, d29);
            tessellator.addVertexWithUV(x + d15, y, z + d17, d27, d28);
            tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
            tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);
            tessellator.draw();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask(false);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(255, 255, 255, 32);
            double d30 = 0.2D;
            double d4 = 0.2D;
            double d6 = 0.8D;
            double d8 = 0.2D;
            double d10 = 0.2D;
            double d12 = 0.8D;
            double d14 = 0.8D;
            double d16 = 0.8D;
            double d18 = (double)(256.0F * f1);
            double d20 = 0.0D;
            double d22 = 1.0D;
            double d24 = (double)(-1.0F + f3);
            double d26 = (double)(256.0F * f1) + d24;
            tessellator.addVertexWithUV(x + d30, y + d18, z + d4, d22, d26);
            tessellator.addVertexWithUV(x + d30, y, z + d4, d22, d24);
            tessellator.addVertexWithUV(x + d6, y, z + d8, d20, d24);
            tessellator.addVertexWithUV(x + d6, y + d18, z + d8, d20, d26);
            tessellator.addVertexWithUV(x + d14, y + d18, z + d16, d22, d26);
            tessellator.addVertexWithUV(x + d14, y, z + d16, d22, d24);
            tessellator.addVertexWithUV(x + d10, y, z + d12, d20, d24);
            tessellator.addVertexWithUV(x + d10, y + d18, z + d12, d20, d26);
            tessellator.addVertexWithUV(x + d6, y + d18, z + d8, d22, d26);
            tessellator.addVertexWithUV(x + d6, y, z + d8, d22, d24);
            tessellator.addVertexWithUV(x + d14, y, z + d16, d20, d24);
            tessellator.addVertexWithUV(x + d14, y + d18, z + d16, d20, d26);
            tessellator.addVertexWithUV(x + d10, y + d18, z + d12, d22, d26);
            tessellator.addVertexWithUV(x + d10, y, z + d12, d22, d24);
            tessellator.addVertexWithUV(x + d30, y, z + d4, d20, d24);
            tessellator.addVertexWithUV(x + d30, y + d18, z + d4, d20, d26);
            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
        }
    }


    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTick)
    {
        this.AC_renderTileEntityAt((TEMagicCircle)tileentity, x, y, z, partialTick);
    }
    */





    public IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(Test.MODID,
            "obj/magic_circle.obj"));


    //public MCMB model = new MCMB();


    public ResourceLocation texture = new ResourceLocation(Test.MODID,
            "textures/blocks/magic_circle.png");

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTick)
    {
        bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.52F, (float) z + 0.5F);
        //GL11.glTranslatef((float) x, (float) y , (float) z );
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        model.renderAll();

        Tessellator tessellator = Tessellator.instance;


        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 255);

        float f1 = 0.0625F;


        float f2 = (float)tileentity.getWorldObj().getTotalWorldTime() + partialTick;
        float f3 = -f2 * 0.2F - (float) MathHelper.floor_float(-f2 * 0.1F);
        byte b0 = 1;
        double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
        double d5 = (double)b0 * 0.2D;
        double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
        double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
        double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
        double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
        double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
        double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
        double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
        double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
        double d23 = (double)(256.0F * f1);
        double d25 = 0.0D;
        double d27 = 1.0D;
        double d28 = (double)(-1.0F + f3);
        double d29 = (double)(256.0F * f1) * (0.5D / d5) + d28;


        tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
        tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
        tessellator.addVertexWithUV(x + d11, y, z + d13, d25, d28);
        tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d25, d29);
        tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d27, d29);
        tessellator.addVertexWithUV(x + d19, y, z + d21, d27, d28);
        tessellator.addVertexWithUV(x + d15, y, z + d17, d25, d28);
        tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d25, d29);
        tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d27, d29);
        tessellator.addVertexWithUV(x + d11, y, z + d13, d27, d28);
        tessellator.addVertexWithUV(x + d19, y, z + d21, d25, d28);
        tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d25, d29);
        tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d27, d29);
        tessellator.addVertexWithUV(x + d15, y, z + d17, d27, d28);
        tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
        tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);
        tessellator.draw();

        GL11.glPopMatrix();
    }

}