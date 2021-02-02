package cubex2.cs3.ingame.gui.control;

import cpw.mods.fml.client.FMLClientHandler;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.util.Rectangle;

public abstract class Control
{
    // This is the parent control of Windows, so they must not be handled differently
    // Only used for getting bounds. This is NOT an actual control
    public static final Control ROOT_CONTROL_DUMMY = new Control(0, 0, null)
    {
        @Override
        protected boolean isRootDummy()
        {
            return true;
        }

        @Override
        public void onParentResized()
        {
            bounds = GuiBase.INSTANCE.getBounds();
        }

        @Override
        protected void setBounds()
        {
            bounds = GuiBase.INSTANCE.getBounds();
        }

        @Override
        public boolean isMouseOverControl(int mouseX, int mouseY)
        {
            return true;
        }
    };

    public Anchor anchor;
    /**
     * If this is not NONE, anchor will be ignored.
     */
    public Dock dock = Dock.NONE;
    public Padding padding = new Padding();
    /**
     * The offset is added to the final position.
     */
    public int offsetX = 0;
    public int offsetY = 0;

    public final Minecraft mc;

    // TODO give this a better name
    protected Window rootControl;
    protected Control parent;

    public boolean parentMouseOverCheck = true;

    protected Rectangle bounds;
    public int width;
    public int height;

    protected float zLevel = 0F;

    private boolean isEnabled = true;
    private boolean isVisible = true;

    public ToolTipProvider toolTipProvider;

    public Object controlTag;

    public boolean tabStop = false;

    public Control(int width, int height, Control parent)
    {
        this(width, height, null, parent);
    }

    public Control(int width, int height, Anchor anchor, Control parent)
    {
        this(width, height, anchor, 0, 0, parent);
    }

    /**
     * @param width  The control's width
     * @param height The control's height
     * @param anchor The control's anchor definition
     * @param parent The parent control.
     */
    public Control(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        mc = FMLClientHandler.instance().getClient();

        if (this instanceof Window)
        {
            // A window has no parent -> itself is the root control
            rootControl = (Window) this;
            if (this.parent == null)
            {
                this.parent = ROOT_CONTROL_DUMMY;
            }
            dock = Dock.CENTER;
        } else if (parent != null)
        {
            rootControl = parent.rootControl;
        } else if (!this.isRootDummy())
        {
            throw new IllegalStateException("Control has no root control.");
        }

        // Set default anchor to top-left of parent control
        if (anchor == null)
        {
            anchor = new Anchor(0, -1, 0, -1);
            anchor.controlLeft = parent;
            anchor.sameSideLeft = true;
            anchor.controlTop = parent;
            anchor.sameSideTop = true;
            anchor.controlRight = parent;
            anchor.controlBottom = parent;
        }
        this.anchor = anchor;

        setBounds();
    }

    public boolean isMouseOverControl(int mouseX, int mouseY)
    {
        return (!parentMouseOverCheck || parent.isMouseOverControl(mouseX, mouseY)) && bounds.contains(mouseX, mouseY);
    }

    protected void setBounds()
    {
        if (parent == ROOT_CONTROL_DUMMY)
            parent.setBounds();
        Rectangle parentRect = parent.bounds;
        Padding padding = parent.padding;

        if (dock == Dock.TOP)
        {
            bounds = new Rectangle(parentRect.getX() + padding.left, parentRect.getY() + padding.top, parentRect.getWidth() - padding.left - padding.right, height);
        } else if (dock == Dock.BOTTOM)
        {
            bounds = new Rectangle(parentRect.getX() + padding.left, parentRect.getY() + parentRect.getHeight() - padding.bottom - height, parentRect.getWidth() - padding.left - padding.right, height);
        } else if (dock == Dock.LEFT)
        {
            bounds = new Rectangle(parentRect.getX() + padding.left, parentRect.getY() + padding.top, width, parentRect.getHeight() - padding.top - padding.bottom);
        } else if (dock == Dock.RIGHT)
        {
            bounds = new Rectangle(parentRect.getX() + parentRect.getWidth() - padding.right - width, parentRect.getY() + padding.top, width, parentRect.getHeight() - padding.top - padding.bottom);
        } else if (dock == Dock.FILL)
        {
            bounds = new Rectangle(parentRect.getX() + padding.left, parentRect.getY() + padding.top, parentRect.getWidth() - padding.left - padding.right, parentRect.getHeight() - padding.top - padding.bottom);
        } else if (dock == Dock.CENTER)
        {
            bounds = new Rectangle(parentRect.getX() + (parentRect.getWidth() - width) / 2, parentRect.getY() + (parentRect.getHeight() - height) / 2, width, height);
        } else // use anchor
        {
            int posX = 0;
            int posY = 0;
            int width = this.width;
            int height = this.height;

            if (anchor.isLeft() && anchor.isRight())
            {
                if (width > 0) // center control
                {
                    posX = anchor.getAnchorLeft() + (anchor.getAnchorRight() - anchor.getAnchorLeft() - width) / 2;
                } else
                {
                    posX = anchor.getAnchorLeft() + anchor.distanceLeft;
                    width = anchor.getAnchorRight() - anchor.distanceRight - posX;
                }
            } else if (anchor.isLeft())
            {
                posX = anchor.getAnchorLeft() + anchor.distanceLeft;
            } else if (anchor.isRight())
            {
                posX = anchor.getAnchorRight() - anchor.distanceRight - width;
            }

            if (anchor.isTop() && anchor.isBottom())
            {
                if (height > 0)
                {
                    posY = anchor.getAnchorTop() + (anchor.getAnchorBottom() - anchor.getAnchorTop() - height) / 2;
                } else
                {
                    posY = anchor.getAnchorTop() + anchor.distanceTop;
                    height = anchor.getAnchorBottom() - anchor.distanceBottom - posY;
                }
            } else if (anchor.isTop())
            {
                posY = anchor.getAnchorTop() + anchor.distanceTop;
            } else if (anchor.isBottom())
            {
                posY = anchor.getAnchorBottom() - anchor.distanceBottom - height;
            }

            bounds = new Rectangle(posX, posY, width, height);
        }

        // apply offset
        bounds.setX(bounds.getX() + offsetX);
        bounds.setY(bounds.getY() + offsetY);
    }

    public boolean hasFocus()
    {
        return rootControl.getFocusedControl() == this;
    }

    public boolean canReleaseFocus()
    {
        return true;
    }

    /**
     * Called when the control receives the focus
     */
    public void onFocus()
    {

    }

    /**
     * Called when the control loses the focus
     */
    public void onUnfocus()
    {

    }

    /**
     * This is called when the parent control or the game window has been resized.
     */
    public void onParentResized()
    {
        setBounds();
    }

    public boolean canHandleInput()
    {
        return GuiBase.inputLockedControl == null || GuiBase.inputLockedControl == this;
    }

    public Control getParent()
    {
        return parent;
    }

    /**
     * Gets the controls rectangle.
     *
     * @return The rectangle in absolute positions.
     */
    public Rectangle getBounds()
    {
        return bounds;
    }

    public int getX()
    {
        return bounds.getX();
    }

    public int getY()
    {
        return bounds.getY();
    }

    public int getWidth()
    {
        return bounds.getWidth();
    }

    public int getHeight()
    {
        return bounds.getHeight();
    }

    public void setEnabled(boolean value)
    {
        isEnabled = value;
    }

    public boolean isEnabled()
    {
        return isEnabled;
    }

    public void setVisible(boolean value)
    {
        isVisible = value;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    /**
     * This is called every tick (0.05 seconds)
     */
    public void onUpdate()
    {
    }

    /**
     * This is called when the user clicks anywhere on the screen but only if the control is enabled and visible.
     *
     * @param mouseX      The absolute mouse x-position
     * @param mouseY      The absolute mouse y-position
     * @param button      The mouse button with that has been clicked.
     * @param intoControl true if the user has clicked into this control.
     */
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {

    }

    /**
     * This is called when the user clicks into this control but only if the control is enabled and visible.
     *
     * @param mouseX The absolute mouse x-position
     * @param mouseY The absolute mouse y-position
     * @param button The mouse button with that has been clicked.
     */
    public void mouseDown(int mouseX, int mouseY, int button)
    {
    }

    /**
     * This is called when the user releases a mouse button.
     *
     * @param mouseX The absolute mouse x-position
     * @param mouseY The absolute mouse y-position
     * @param button 0: left button, 1: right button
     */
    public void mouseUp(int mouseX, int mouseY, int button)
    {
    }

    /**
     * This is called when the user typed a key on the keyboard.
     *
     * @param c   The key as char.
     * @param key The key as int.
     */
    public void keyTyped(char c, int key)
    {
    }

    /**
     * @param mouseX     The absolute mouse x-position.
     * @param mouseY     The absolute mouse y-position.
     * @param renderTick
     */
    public void draw(int mouseX, int mouseY, float renderTick)
    {
    }

    /**
     * Draw the foreground, i.e. hover text
     *
     * @param mouseX The absolute mouse x-position.
     * @param mouseY The absolute mouse y-position.
     */
    public void drawForeground(int mouseX, int mouseY)
    {
        if (toolTipProvider != null && isMouseOverControl(mouseX, mouseY))
        {
            String toolTip = toolTipProvider.getToolTip(this);
            if (toolTip != null && toolTip.length() > 0)
            {
                GuiHelper.drawToolTip(toolTip.split("\n"), mouseX, mouseY, mc.fontRenderer);
            }
        }
    }

    public void drawTexturedModalRect(Rectangle rect, int u, int v)
    {
        drawTexturedModalRect(rect.getX(), rect.getY(), u, v, rect.getWidth(), rect.getHeight());
    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }

    public void drawString(String text, int x, int y, int color)
    {
        mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public void drawCenteredString(String text, int x, int y, int color)
    {
        mc.fontRenderer.drawStringWithShadow(text, x - mc.fontRenderer.getStringWidth(text) / 2, y, color);
    }

    protected boolean isRootDummy()
    {
        return false;
    }

}
