package cubex2.cs3.renderer;

import cubex2.cs3.block.attributes.FacingAttributes;
import cubex2.cs3.tileentity.TileEntityCS;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

// 豆焰写的

public class TileEntitySpecialRendererCS extends TileEntitySpecialRenderer {

    public void render(TileEntityCS tile, double x, double y, double z, float partialTick) {
        bindTexture(tile.BTexture);
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //------------------------------------------------------------------------
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        //------------------------------------------------------------------------
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.52F, (float) z + 0.5F);
        //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC
        GL11.glRotated(tile.BRotate, 0, 1, 0);
        //GL11.glRotated(((int)System.currentTimeMillis()%360000)/100D, 0, 1, 0);
        GL11.glScaled(tile.BSize, 1.0, tile.BSize);
        // GL11.glScaled(1.0, 1.0, 1.0);
        //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC
        tile.BModel.renderAll();
        GL11.glPopMatrix();
    }


    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTick)
    {
        render((TileEntityCS) tileentity, x, y, z, partialTick);
    }





}
