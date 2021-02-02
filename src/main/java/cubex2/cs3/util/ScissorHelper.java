package cubex2.cs3.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class ScissorHelper
{
    public static void startScissor(Minecraft mc, int x, int y, int w, int h)
    {
        ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int scale = scaledRes.getScaleFactor();

        int scissorWidth = w * scale;
        int scissorHeight = h * scale;
        int scissorX = x * scale;
        int scissorY = mc.displayHeight - scissorHeight - (y * scale);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
    }

    public static void startScissor(Minecraft mc, Rectangle rect)
    {
        startScissor(mc, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public static void endScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
