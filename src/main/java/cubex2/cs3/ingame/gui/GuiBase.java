package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.gui.WindowNormal;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;

import java.util.List;
import java.util.Stack;

public class GuiBase extends GuiScreen
{
    static
    {
        new GuiBase();
    }

    public static GuiBase INSTANCE;

    public static final RenderItem itemRenderer = new RenderItem();
    public static int dWheel = 0;

    public final Stack<Window> windowHistory = new Stack<Window>();
    public Window window;

    public static boolean devMode = false;
    public static Control activeDevControl = null;
    public static Control inputLockedControl = null;

    public static boolean isContainerGuiOpen = false;
    static GuiContainerBase containerGui;

    public static int tickCounter;

    private GuiBase()
    {
        INSTANCE = this;

        Control.ROOT_CONTROL_DUMMY.onParentResized();

        window = new WindowMain();
    }

    public static void closeGui()
    {
        INSTANCE.mc.displayGuiScreen((GuiScreen) null);
        INSTANCE.mc.setIngameFocus();
        Keyboard.enableRepeatEvents(false);
    }

    public static <T extends WindowContainer> GuiContainerBase createContainerGui(T window)
    {
        GuiContainerBase gui = new GuiContainerBase(window, window.getContainer());
        isContainerGuiOpen = true;
        containerGui = gui;

        return gui;
    }

    public static void openWindow(Window window)
    {
        openWindow(window, null);
    }

    public static void openWindow(Window window, String tag)
    {
        window.tag = tag;
        INSTANCE.windowHistory.push(INSTANCE.window);
        INSTANCE.window = window;
        INSTANCE.window.onParentResized();
    }

    public static void openPrevWindow()
    {
        Window currentWindow = INSTANCE.window;
        INSTANCE.window = INSTANCE.windowHistory.pop();
        INSTANCE.window.onParentResized();
        if (INSTANCE.window instanceof IWindowClosedListener && !(currentWindow instanceof WindowNormal))
            ((IWindowClosedListener) INSTANCE.window).windowClosed(currentWindow);
    }

    public static void lockInput(Control c)
    {
        inputLockedControl = c;
    }

    public static void releaseInput()
    {
        inputLockedControl = null;
    }


    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void initGui()
    {
        Control.ROOT_CONTROL_DUMMY.onParentResized();

        window.onParentResized();
    }

    @Override
    public void onGuiClosed()
    {
        if (window != null)
        {
            window.onGuiClosed();
        }
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen()
    {
        tickCounter++;

        dWheel = Mouse.getDWheel() / 120;
        window.onUpdate();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        boolean intoWindow = window.isMouseOverControl(mouseX, mouseY);
        if (devMode)
        {
            if (button == 0)
            {
                activeDevControl = window.getControlAt(mouseX, mouseY);
            }
        } else
        {
            window.mouseClicked(mouseX, mouseY, button, intoWindow);
            if (intoWindow)
            {
                window.mouseDown(mouseX, mouseY, button);
            }
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button)
    {
        if (devMode)
        {

        } else
        {
            window.mouseUp(mouseX, mouseY, button);
        }
    }

    @Override
    protected void keyTyped(char c, int i)
    {
        if (i == Keyboard.KEY_ESCAPE)
        {
            mc.displayGuiScreen((GuiScreen) null);
            mc.setIngameFocus();
        } else if (i == Keyboard.KEY_F7)
        {
            devMode = !devMode;
            if (!devMode)
            {
                activeDevControl = null;
            }
        }

        if (devMode)
        {
            if (activeDevControl != null)
            {
                if (i == Keyboard.KEY_DOWN)
                {
                    activeDevControl.offsetY += 1;
                    activeDevControl.onParentResized();
                } else if (i == Keyboard.KEY_UP)
                {
                    activeDevControl.offsetY -= 1;
                    activeDevControl.onParentResized();
                }

                if (i == Keyboard.KEY_LEFT)
                {
                    activeDevControl.offsetX -= 1;
                    activeDevControl.onParentResized();
                } else if (i == Keyboard.KEY_RIGHT)
                {
                    activeDevControl.offsetX += 1;
                    activeDevControl.onParentResized();
                }

                if (i == Keyboard.KEY_PRIOR)
                {
                    activeDevControl = activeDevControl.getParent();
                }
            }
        } else
        {
            window.keyTyped(c, i);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderTick)
    {
        window.draw(mouseX, mouseY, renderTick);
        window.drawForeground(mouseX, mouseY);

        if (devMode)
        {
            drawDevScreen(mouseX, mouseY);
        }
    }

    private void drawDevScreen(int mouseX, int mouseY)
    {
        mc.fontRenderer.drawString("Dev Mode", 5, 5, Color.RED);

        Control c = window.getControlAt(mouseX, mouseY);
        if (c != null)
        {
            List<String> list = Lists.newArrayList();
            list.add(c.getClass().getSimpleName());
            list.add(String.format("X: %d Y: %d", c.getX(), c.getY()));
            list.add("W: " + c.getWidth() + " H: " + c.getHeight());
            GuiHelper.drawHoveringText(list, mouseX, mouseY, mc.fontRenderer);
        }


        if (activeDevControl != null)
        {
            GuiHelper.drawBorder(activeDevControl.getX() - 1, activeDevControl.getY() - 1,
                    activeDevControl.getX() + activeDevControl.getWidth() + 1, activeDevControl.getY() + activeDevControl.getHeight() + 1,
                    Color.RED);
        }
    }


    public Rectangle getBounds()
    {
        if (Minecraft.getMinecraft().currentScreen == null)
            return new Rectangle(0,0,0,0);
        return new Rectangle(0, 0, Minecraft.getMinecraft().currentScreen.width, Minecraft.getMinecraft().currentScreen.height);
        /*return mc.currentScreen
        if (isContainerGuiOpen)
            return new Rectangle(0, 0, containerGui.width, containerGui.height);
        return new Rectangle(0, 0, width, height);*/
    }

    @Override
    public void drawGradientRect(int x1, int y1, int x2, int y2, int color1, int color2)
    {
        super.drawGradientRect(x1, y1, x2, y2, color1, color2);
    }

    public void setZLevel(float value)
    {
        zLevel = value;
    }


}
