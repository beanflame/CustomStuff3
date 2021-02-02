package cubex2.cs3.ingame.gui.control;

import cpw.mods.fml.client.FMLClientHandler;
import cubex2.cs3.lib.Color;

public class Label extends Control
{
    protected String text;
    protected int color;
    protected boolean centered;
    private String[] lines;

    public Label(String text, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        this(text, Color.BLACK, anchor, offsetX, offsetY, parent);
    }

    public Label(String text, int color, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(FMLClientHandler.instance().getClient().fontRenderer.getStringWidth(text), 9, anchor, offsetX, offsetY, parent);
        setText(text);
        this.color = color;
    }

    public Label setCentered()
    {
        centered = true;
        return this;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setText(String value)
    {
        text = value;
        if (text != null)
            lines = text.split("\n");
        else
            lines = new String[0];

        int width = 0;
        for (int i = 0; i < lines.length; i++)
        {
            int w = mc.fontRenderer.getStringWidth(lines[i]);
            if (w > width)
                width = w;
        }

        bounds.setWidth(width);
        bounds.setHeight(lines.length * 13 - 4);

        this.width = width;
        this.height = lines.length * 13 - 4;
        this.height = lines.length * 13 - 4;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        for (int i = 0; i < lines.length; i++)
        {
            int y = bounds.getY() + i * 13;
            if (centered)
            {
                mc.fontRenderer.drawString(lines[i], bounds.getX() + (bounds.getWidth() - mc.fontRenderer.getStringWidth(text)) / 2, y, color);
            } else
            {
                mc.fontRenderer.drawString(lines[i], bounds.getX(), y, color);
            }
        }
    }

    public static int calcTextHeight(String text)
    {
        return text.split("\n").length * 13 - 4;
    }
}
