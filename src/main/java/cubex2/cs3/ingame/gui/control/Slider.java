package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;

public class Slider extends Control
{
    public enum Direction
    {
        VERTICAL, HORIZONTAL
    }

    private final Direction direction;

    protected Rectangle scrollThumbRect;
    protected int mousePos = -1;
    protected boolean mouseDown = false;
    protected int scrollOffset;
    protected int prevScrollOffset = -1;

    private int wheelScrollStep = 1;
    private boolean wheelScrollEverywhere = false;
    private boolean wheelScrollParent = false;

    private int currentValue;
    protected int maxValue;

    private int thumbSize;

    private IValueListener<Slider> listener;

    private int mouseX = -1;
    private int mouseY = -1;

    public Slider(Direction direction, int maxValue, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.direction = direction;
        this.maxValue = maxValue;
        updateThumbSize();
        scrollThumbRect = new Rectangle(getX(), getY(), thumbWidth(), thumbHeight());

        if (rootControl instanceof IValueListener)
        {
            listener = (IValueListener<Slider>) rootControl;
        }
    }

    private int scrollAreaSize()
    {
        return direction == Direction.HORIZONTAL ? getWidth() : getHeight();
    }

    private int thumbWidth()
    {
        return direction == Direction.HORIZONTAL ? thumbSize : getWidth();
    }

    private int thumbHeight()
    {
        return direction == Direction.VERTICAL ? thumbSize : getHeight();
    }

    private int maxScrollSize()
    {
        return (direction == Direction.HORIZONTAL ? getWidth() : getHeight()) - thumbSize;
    }

    private void updateThumbRect()
    {
        if (direction == Direction.HORIZONTAL)
            scrollThumbRect.setX(getX() + scrollOffset);
        else
            scrollThumbRect.setY(getY() + scrollOffset);
    }

    public void setValueListener(IValueListener<Slider> listener)
    {
        this.listener = listener;
    }

    public int getValue()
    {
        return currentValue;
    }

    public float getValueFloat()
    {
        return scrollOffset / (float) maxScrollSize() * maxValue;
    }

    public void setWheelScrollStep(int value)
    {
        wheelScrollStep = value;
    }

    public void setWheelScrollEverywhere(boolean value)
    {
        wheelScrollEverywhere = value;
    }

    public void setWheelScrollParent(boolean value)
    {
        wheelScrollParent = value;
    }

    /**
     * Sets the maxValue in pixels.
     */
    public void setMaxValue(int value)
    {
        maxValue = value;
        updateThumbSize();

        scrollThumbRect.setHeight(thumbSize);

        currentValue = Math.min(currentValue, maxValue);
        scrollOffset = (int) (maxScrollSize() / (float) maxValue * currentValue);
        updateThumbRect();
    }

    private void updateThumbSize()
    {
        int total = scrollAreaSize() + maxValue;
        float perc = scrollAreaSize() / (float) total;
        thumbSize = maxValue <= 0 ? 0 : Math.max((int) (perc * scrollAreaSize()), 10);
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        scrollThumbRect = new Rectangle(getX(), getY(), thumbWidth(), thumbHeight());
        updateThumbRect();
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0 && maxValue > 0)
        {
            mouseDown = true;

            if (!scrollThumbRect.contains(mouseX, mouseY))
                scrollOffset = MathHelper.clamp_int(mousePos(mouseX, mouseY) - zeroThumbPos() - thumbSize / 2, 0, maxScrollSize());

            prevScrollOffset = scrollOffset;
            mousePos = mousePos(mouseX, mouseY);

            if (!scrollThumbRect.contains(mouseX, mouseY))
                updateScroll();
        }
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        if (button == 0 && mouseDown)
        {
            mouseDown = false;
        }
    }

    private int zeroThumbPos()
    {
        return direction == Direction.HORIZONTAL ? getX() : getY();
    }


    private int mousePos(int mouseX, int mouseY)
    {
        return direction == Direction.HORIZONTAL ? mouseX : mouseY;
    }

    public void setScroll(int value)
    {
        currentValue = MathHelper.clamp_int(value, 0, maxValue);
        scrollChanged();
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (canChangeScroll())
        {
            if (key == Keyboard.KEY_END)
            {
                setScroll(Integer.MAX_VALUE);
            } else if (key == Keyboard.KEY_HOME)
            {
                setScroll(0);
            } else if (key == Keyboard.KEY_PRIOR)
            {
                setScroll(getValue() - getHeight() - 1);
            } else if (key == Keyboard.KEY_NEXT)
            {
                setScroll(getValue() + getHeight() + 1);
            }
        }
    }

    @Override
    public void onUpdate()
    {
        int wheel = GuiBase.dWheel;
        if (wheel != 0 && canChangeScroll() && maxValue > 0)
        {
            currentValue = MathHelper.clamp_int(currentValue - wheel * wheelScrollStep, 0, maxValue);
            scrollChanged();
        }
    }

    /**
     * Checks if the wheel scrolling or using end, pgDown keys can be used
     *
     * @return true if can be used, false otherwise.
     */
    private boolean canChangeScroll()
    {
        return wheelScrollEverywhere
                || (wheelScrollParent && parent.isMouseOverControl(mouseX, mouseY))
                || isMouseOverControl(mouseX, mouseY);
    }

    private void scrollChanged()
    {
        scrollOffset = (int) (maxScrollSize() / (float) maxValue * currentValue);
        updateThumbRect();

        if (listener != null)
            listener.onValueChanged(this);
    }

    public void updateScroll()
    {
        updateThumbRect();
        float widthPerScroll = maxScrollSize() / (float) ((maxValue + 1));
        currentValue = (int) (scrollOffset / widthPerScroll);
        currentValue = MathHelper.clamp_int(currentValue, 0, maxValue);

        if (listener != null)
            listener.onValueChanged(this);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        if (mouseDown)
        {
            scrollOffset = MathHelper.clamp_int(prevScrollOffset + mousePos(mouseX, mouseY) - mousePos, 0, maxScrollSize());
            updateScroll();
        }

        GuiHelper.drawOutlinedRect(getBounds(), Color.DARK_GREY, Color.LIGHT_GREY);

        drawThumb(mouseX, mouseY);
    }

    private void drawThumb(int mouseX, int mouseY)
    {
        if (maxValue > 0)
        {
            if (scrollThumbRect.contains(mouseX, mouseY) || mouseDown)
            {
                GuiHelper.drawOutlinedRect(scrollThumbRect, Color.WHITE, Color.DARK_GREY);
            } else
            {
                GuiHelper.drawRect(scrollThumbRect, Color.DARK_GREY);
            }
        }
    }
}
