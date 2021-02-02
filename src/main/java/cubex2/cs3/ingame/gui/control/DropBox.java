package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.Rectangle;

public class DropBox<T> extends Control
{
    private final T[] values;
    private T selectedValue = null;
    private boolean isExpanded = false;

    private int offset = 0;
    private int maxShowedValues = 6;

    private IStringProvider<T> stringProvider;

    public boolean drawNullValue = false;

    public DropBox(T[] values, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.values = values;
    }

    public void setStringProvider(IStringProvider<T> provider)
    {
        stringProvider = provider;
    }

    public void setSelectedValue(T value)
    {
        selectedValue = value;
    }

    public T getSelectedValue()
    {
        return selectedValue;
    }

    @Override
    public void onUpdate()
    {
        int wheel = GuiBase.dWheel;
        if (wheel != 0 && isExpanded && values.length > maxShowedValues)
        {
            offset = MathHelper.clamp_int(offset - wheel, 0, values.length - maxShowedValues);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        int borderColor = getBounds().contains(mouseX, mouseY) && !isExpanded ? Color.WHITE : Color.DARK_GREY;
        GuiHelper.drawOutlinedRect(getBounds(), borderColor, Color.LIGHT_GREY);

        if (drawNullValue || selectedValue != null)
        {
            mc.fontRenderer.drawString(getTextToDraw(selectedValue), bounds.getX() + 3, bounds.getY() + 3, Color.BLACK);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (isExpanded)
        {
            for (int i = 0; i < Math.min(values.length, maxShowedValues); i++)
            {
                int index = i + offset;

                int rx = getX() + 10;
                int ry = getY() + (i + 1) * getHeight();
                Rectangle rect = new Rectangle(rx, ry, getWidth(), getHeight());

                int borderColor = rect.contains(mouseX, mouseY) ? Color.WHITE : Color.DARK_GREY;
                GuiHelper.drawOutlinedRect(rect, borderColor, Color.LIGHT_GREY);

                mc.fontRenderer.drawString(getTextToDraw(values[index]), rx + 3, ry + 3, Color.BLACK);
            }
        }
    }

    private String getTextToDraw(T value)
    {
        return stringProvider == null ? value.toString() : stringProvider.getStringFor(value);
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            isExpanded = !isExpanded;
            if (isExpanded)
            {
                GuiBase.lockInput(this);
            } else
            {
                GuiBase.releaseInput();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        if (isExpanded && !intoControl)
        {
            Rectangle expandedRect = new Rectangle(getX() + 10, getY() + getHeight(), getWidth(), getHeight() * values.length);
            if (expandedRect.contains(mouseX, mouseY))
            {
                int index = (mouseY - expandedRect.getY()) / getHeight() + offset;
                if (index < values.length)
                    selectedValue = values[index];
            }
            isExpanded = false;
            GuiBase.releaseInput();
        }
    }

    @Override
    public boolean isMouseOverControl(int mouseX, int mouseY)
    {
        return super.isMouseOverControl(mouseX, mouseY);
    }
}
