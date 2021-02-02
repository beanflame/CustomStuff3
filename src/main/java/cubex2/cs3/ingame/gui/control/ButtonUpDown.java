package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Textures;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonUpDown extends Control
{
    protected boolean hover;
    private boolean isUp = false;

    public ButtonUpDown(boolean isUp, Control parent)
    {
        this(isUp, null, 0, 0, parent);
    }

    public ButtonUpDown(boolean isUp, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(9, 9, anchor, offsetX, offsetY, parent);
        this.isUp = isUp;
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
        }
    }

    protected int getHoverState(boolean hover)
    {
        byte b0 = 1;

        if (!isEnabled())
        {
            b0 = 0;
        } else if (hover)
        {
            b0 = 2;
        }

        return b0;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        mc.renderEngine.bindTexture(Textures.CONTROLS);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        hover = isMouseOverControl(mouseX, mouseY);

        int k = getHoverState(hover);
        int u = isUp ? 200 : 209;
        drawTexturedModalRect(bounds.getX(), bounds.getY(), u, 18 + k * 9, 9, 9);
    }
}

