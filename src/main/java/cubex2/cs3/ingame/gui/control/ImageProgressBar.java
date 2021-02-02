package cubex2.cs3.ingame.gui.control;

import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ImageProgressBar extends Control
{
    // Directions
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public ResourceLocation texture;
    public int u;
    public int v;
    private int progress;
    public int direction;

    public ImageProgressBar(ResourceLocation texture, int u, int v, int direction, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.direction = direction;
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress(int value)
    {
        progress = MathHelper.clamp_int(value, 0, getMaxProgress());
    }

    protected final int getMaxProgress()
    {
        if (direction == LEFT || direction == RIGHT)
            return getWidth();
        return getHeight();
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        mc.renderEngine.bindTexture(texture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        switch (direction)
        {
            case UP:
                drawTexturedModalRect(getX(), getY() + getHeight() - getProgress(), u, v + getHeight() - getProgress(), getWidth(), getProgress());
                break;
            case DOWN:
                drawTexturedModalRect(getX(), getY(), u, v, width, getProgress());
                break;
            case LEFT:
                drawTexturedModalRect(getX() + getWidth() - getProgress(), getY(), u + getWidth() - getProgress(), v, getProgress(), getHeight());
                break;
            case RIGHT:
                drawTexturedModalRect(getX(), getY(), u, v, getProgress(), getHeight());
                break;
        }
    }
}
