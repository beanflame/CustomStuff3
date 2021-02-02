package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.lib.Color;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class ListBoxItem<T> extends ControlContainer
{
    protected final T value;
    protected final int index;
    protected boolean drawDefaultBackground = true;
    private boolean isSelected = false;

    public ListBoxItem(T value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.value = value;
        index = idx;
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        super.mouseDown(mouseX, mouseY, button);
    }

    public void setSelected(boolean value)
    {
        isSelected = value;
        selectionChanged();
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void selectionChanged()
    {

    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (drawDefaultBackground)
        {
            boolean isMouseOverControl = isMouseOverControl(mouseX, mouseY);
            int color1 = getDefaultColor1(isSelected, isMouseOverControl);
            int color2 = getDefaultColor2(isSelected, isMouseOverControl);

            GuiHelper.drawOutlinedRect(bounds, color1, color2);
        }

        super.draw(mouseX, mouseY, renderTick);
    }

    protected final int getDefaultColor1(boolean isSelected, boolean isMouseOver)
    {
        int color1 = Color.DARK_GREY;
        if (isMouseOver && isSelected)
        {
            color1 = Color.WHITE;
        } else if (isMouseOver)
        {
            color1 = Color.WHITE;
        } else if (isSelected)
        {
            color1 = Color.BLACK;
        }
        return color1;
    }

    protected final int getDefaultColor2(boolean isSelected, boolean isMouseOver)
    {
        int color2 = Color.LIGHT_GREY;
        if (isMouseOver && isSelected)
        {
            color2 = Color.DARK_GREY;
        } else if (isMouseOver)
        {
            color2 = Color.LIGHT_GREY;
        } else if (isSelected)
        {
            color2 = Color.DARK_GREY;
        }
        return color2;
    }

    protected final int getDefaultTextColor(boolean isSelected, boolean isMouseOver)
    {
        int textColor = Color.BLACK;
        if (isMouseOver && isSelected)
        {
            textColor = Color.YELLOW;
        } else if (isSelected)
        {
            textColor = Color.YELLOW;
        }
        return textColor;
    }
}
