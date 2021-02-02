package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;
import cubex2.cs3.lib.Textures;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CheckBox extends Control
{
    protected int color = Color.BLACK;
    protected String text;
    protected boolean isChecked = false;

    public CheckBox(Control parent)
    {
        super(9, 9, parent);
    }

    public CheckBox(Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(9, 9, anchor, offsetX, offsetY, parent);
    }

    public CheckBox(String text, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(9, 9, anchor, offsetX, offsetY, parent);
        setText(text);
    }

    public boolean getIsChecked()
    {
        return isChecked;
    }

    public void setIsChecked(boolean value)
    {
        isChecked = value;
    }

    public void setText(String value)
    {
        text = value;
    }
    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
            isChecked = !isChecked;
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        mc.renderEngine.bindTexture(Textures.CONTROLS);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean hover = bounds.contains(mouseX, mouseY);

        int u = isChecked ? 200 : 209;
        int v = !isEnabled() ? 45 : hover ? 63 : 54;
        this.drawTexturedModalRect(bounds.getX(), bounds.getY(), u, v, 9, 9);

        if (text != null)
        {
            mc.fontRenderer.drawString(text, bounds.getX() + bounds.getWidth() + 3, bounds.getY() + 1, color);
        }
    }
}
