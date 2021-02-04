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

import java.io.BufferedReader;

public class TESRMagicCircle extends TileEntitySpecialRenderer {

    public IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(Test.MODID,
            "obj/magic_circle.obj"));

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
        GL11.glRotated(((int)System.currentTimeMillis()%360000)/100D, 0, 1, 0);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        model.renderAll();
        GL11.glPopMatrix();
    }
}
