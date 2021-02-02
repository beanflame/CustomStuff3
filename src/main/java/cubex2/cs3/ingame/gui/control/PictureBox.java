package cubex2.cs3.ingame.gui.control;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PictureBox extends Control
{
    private ResourceLocation texture;
    private int u;
    private int v;

    public PictureBox(ResourceLocation texture, int u, int v, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    public void setTexture(ResourceLocation texture)
    {
        this.texture = texture;
    }

    public void setUV(int u, int v)
    {
        this.u = u;
        this.v = v;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {

        mc.renderEngine.bindTexture(texture);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(bounds.getX(), bounds.getY(), u, v, bounds.getWidth(), bounds.getHeight());
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }
}
