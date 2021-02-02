package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.Control;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiContainerBase extends GuiContainer
{
    public WindowContainer window;
    public boolean closeOnInventoryKey = true;
    public boolean isInventoryEffects = false;

    private boolean field_147045_u;

    public GuiContainerBase(WindowContainer window, Container container)
    {
        super(container);
        this.window = window;
        xSize = window.width;
        ySize = window.height;
        window.setGui(this);
        window.onParentResized();
        //window.removeControls();
    }

    public int getLeft()
    {
        return guiLeft;
    }

    public int getTop()
    {
        return guiTop;
    }

    @Override
    public void onGuiClosed()
    {
        if (window != null)
        {
            window.onGuiClosed();
        }
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();

        GuiBase.isContainerGuiOpen = false;
        GuiBase.containerGui = null;
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        Control.ROOT_CONTROL_DUMMY.onParentResized();

        window.onParentResized();
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        GuiBase.dWheel = Mouse.getDWheel();
        window.onUpdate();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);
        boolean intoWindow = window.isMouseOverControl(mouseX, mouseY);

        window.mouseClicked(mouseX, mouseY, button, intoWindow);
        if (intoWindow)
        {
            window.mouseDown(mouseX, mouseY, button);
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button)
    {
        super.mouseMovedOrUp(mouseX, mouseY, button);

        window.mouseUp(mouseX, mouseY, button);
    }

    @Override
    protected void keyTyped(char c, int i)
    {
        if (i != mc.gameSettings.keyBindInventory.getKeyCode() || closeOnInventoryKey)
        {
            super.keyTyped(c, i);
        }

        window.keyTyped(c, i);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderTick)
    {
        super.drawScreen(mouseX, mouseY, renderTick);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        window.drawForeground(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        window.draw(mouseX, mouseY, f);
    }
}
