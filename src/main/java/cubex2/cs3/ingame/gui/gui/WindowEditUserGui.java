package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.EnumGuiType;
import cubex2.cs3.gui.WindowContainerNormal;
import cubex2.cs3.gui.WindowNormal;
import cubex2.cs3.gui.data.*;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import org.lwjgl.input.Keyboard;

public class WindowEditUserGui extends Window
{
    private final WrappedGui gui;
    private final GuiData guiData;

    private final Window window;

    private Button btnAddButton;
    private Button btnAddLabel;
    private Button btnAddPlayerInventory;
    private Button btnAddSlot;
    private Button btnAddCookProgress;
    private Button btnAddFuelProgress;

    private Button btnEditControl;
    private Button btnMode;

    private boolean moveMode = true;

    private Control selected = null;

    private Button clickedButton = null;
    private boolean holdOn = false;
    private boolean isCreatingControl = false;
    private int mouseDownX;
    private int mouseDownY;

    public WindowEditUserGui(WrappedGui gui)
    {
        super(BACK, GuiBase.INSTANCE.width, GuiBase.INSTANCE.height);
        this.gui = gui;
        guiData = gui.container.guiData;
        drawBackground = false;

        btnAddButton = button("Button").at(7, 7).size(60, 16).add();
        btnAddLabel = button("Label").below(btnAddButton).size(60, 16).add();
        btnEditControl = button("Edit Control").top(7).right(7).size(80, 16).add();
        btnEditControl.setEnabled(false);
        btnMode = button("Mode: Move").below(btnEditControl).size(80, 16).add();

        if (gui.getType() == EnumGuiType.NORMAL)
            window = new WindowNormal(gui);
        else
        {
            btnAddPlayerInventory = button("Player Inv").below(btnAddLabel).size(60, 16).add();
            btnAddSlot = button("Inv Slot").below(btnAddPlayerInventory).size(60, 16).add();
            btnAddCookProgress = button("Cook Prog").below(btnAddSlot).size(60, 16).add();
            btnAddFuelProgress = button("Fuel Prog").below(btnAddCookProgress).size(60, 16).add();

            window = new WindowContainerNormal(gui, null);
            window.drawSlots = true;
        }
        window.onParentResized();
    }

    private void switchMode()
    {
        moveMode = !moveMode;
        btnMode.setText("Mode: " + (moveMode ? "Move" : "Size"));
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnAddButton || c == btnAddLabel || c == btnAddPlayerInventory || c == btnAddSlot || c == btnAddCookProgress || c == btnAddFuelProgress)
        {
            controlButtonClicked(c);
        } else if (c == btnEditControl)
        {
            if (selected instanceof Button)
                GuiBase.openWindow(new WindowButton(gui, window, (Button) selected, (ButtonData) selected.controlTag));
            else if (selected instanceof Label)
                GuiBase.openWindow(new WindowLabel(gui, window, (Label) selected, (LabelData) selected.controlTag));
            else if (selected instanceof PlayerInventoryArea)
                GuiBase.openWindow(new WindowPlayerInventory(gui, window, (PlayerInventoryArea) selected, (PlayerInventoryData) selected.controlTag));
            else if (selected instanceof InventorySlot)
                GuiBase.openWindow(new WindowInventorySlot(gui, window, (InventorySlot) selected, (SlotData) selected.controlTag));
            else if (selected instanceof CookProgressBar)
                GuiBase.openWindow(new WindowCookProgress(gui, window, (CookProgressBar) selected, (CookProgressData) selected.controlTag));
            else if (selected instanceof FuelProgressBar)
                GuiBase.openWindow(new WindowFuelProgress(gui, window, (FuelProgressBar) selected, (FuelProgressData) selected.controlTag));
        } else if (c == btnMode)
        {
            switchMode();
        } else if (c == btnBack)
        {
            gui.getPack().save();
            GuiBase.openPrevWindow();
        }
    }

    private void controlButtonClicked(Control button)
    {
        clickedButton = (Button) button;
        holdOn = true;
        setAllControlsEnableState(false);
    }

    @Override
    public void onParentResized()
    {
        width = Math.min(GuiBase.INSTANCE.width, 504);
        height = Math.min(GuiBase.INSTANCE.height, 504);

        window.onParentResized();

        super.onParentResized();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        if (holdOn)
        {
            holdOn = false;
            isCreatingControl = true;
        }
        if (isCreatingControl)
        {
            mouseDownX = mouseX;
            mouseDownY = mouseY;
            return;
        }

        Control c = window.getControlAt(mouseX, mouseY);
        if (c != null && c != window)
        {
            if (c == selected && button == 1)
                setSelected(null);
            else if (button == 0)
                setSelected(c);

            btnEditControl.setEnabled(selected != null);
        } else if (selected != null && c == window)
        {
            setSelected(null);
        } else
        {
            super.mouseClicked(mouseX, mouseY, button, intoControl);
        }
    }

    private void setAllControlsEnableState(boolean value)
    {
        btnAddButton.setEnabled(value);
        btnAddLabel.setEnabled(value);
        setEnabled(btnAddPlayerInventory, value);
        setEnabled(btnAddSlot, value);
        setEnabled(btnAddCookProgress, value);
        setEnabled(btnAddFuelProgress, value);
        btnMode.setEnabled(value);
        btnBack.setEnabled(value);
    }

    private void setEnabled(Control c, boolean value)
    {
        if (c != null)
            c.setEnabled(value);
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        if (!holdOn && isCreatingControl)
        {
            int x1 = Math.min(mouseDownX, mouseX);
            int x2 = Math.max(mouseDownX, mouseX);
            int y1 = Math.min(mouseDownY, mouseY);
            int y2 = Math.max(mouseDownY, mouseY);

            int width = x2 - x1;
            int height = y2 - y1;
            x1 -= window.getX();
            y1 -= window.getY();

            if (clickedButton == btnAddButton)
                GuiBase.openWindow(new WindowButton(gui, window, x1, y1, width, height));
            else if (clickedButton == btnAddLabel)
                GuiBase.openWindow(new WindowLabel(gui, window, x1, y1));
            else if (clickedButton == btnAddPlayerInventory)
                GuiBase.openWindow(new WindowPlayerInventory(gui, window, x1, y1));
            else if (clickedButton == btnAddSlot)
                GuiBase.openWindow(new WindowInventorySlot(gui, window, x1, y1));
            else if (clickedButton == btnAddCookProgress)
                GuiBase.openWindow(new WindowCookProgress(gui, window, x1, y1, width, height));
            else if (clickedButton == btnAddFuelProgress)
                GuiBase.openWindow(new WindowFuelProgress(gui, window, x1, y1, width, height));

            setAllControlsEnableState(true);
            isCreatingControl = false;
            return;
        }

        super.mouseUp(mouseX, mouseY, button);
    }

    private void setSelected(Control c)
    {
        selected = c;
        btnEditControl.setEnabled(selected != null);
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (selected != null)
        {
            ControlData tag = (ControlData) selected.controlTag;
            if (key == Keyboard.KEY_DELETE)
            {
                window.getControls().remove(selected);
                gui.container.guiData.controls.remove(tag);
                setSelected(null);
            } else if (key == Keyboard.KEY_RIGHT || key == Keyboard.KEY_LEFT)
            {
                int dir = key == Keyboard.KEY_RIGHT ? 1 : -1;
                if (moveMode)
                {
                    selected.offsetX += dir;
                    tag.x += dir;
                } else if (tag.isSizeable())
                {
                    selected.width += dir;
                    tag.width += dir;
                }

                selected.onParentResized();
            } else if (key == Keyboard.KEY_DOWN || key == Keyboard.KEY_UP)
            {
                int dir = key == Keyboard.KEY_DOWN ? 1 : -1;
                if (moveMode)
                {
                    selected.offsetY += dir;
                    tag.y += dir;
                } else if (tag.isSizeable())
                {
                    selected.height += dir;
                    tag.height += dir;
                }
                selected.onParentResized();
            }
        }
        super.keyTyped(c, key);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        window.draw(mouseX, mouseY, renderTick);

        if (holdOn || isCreatingControl)
        {
            Control c = clickedButton;
            GuiHelper.drawBorder(c.getX() - 1, c.getY() - 1, c.getX() + c.getWidth() + 1, c.getY() + c.getHeight() + 1, Color.RED);
        }

        if (isCreatingControl)
        {
            if (clickedButton == btnAddLabel)
            {
                GuiHelper.drawRect(mouseX - 5, mouseY, mouseX + 6, mouseY + 1, Color.BLUE);
                GuiHelper.drawRect(mouseX, mouseY - 5, mouseX + 1, mouseY + 6, Color.BLUE);
            } else if (clickedButton == btnAddPlayerInventory)
            {
                GuiHelper.drawRect(mouseX, mouseY, mouseX + 9 * 18, mouseY + 76, Color.BLUE);
            } else if (clickedButton == btnAddSlot)
            {
                GuiHelper.drawRect(mouseX, mouseY, mouseX + 18, mouseY + 18, Color.BLUE);
            } else
            {
                int x1 = Math.min(mouseDownX, mouseX);
                int x2 = Math.max(mouseDownX, mouseX);
                int y1 = Math.min(mouseDownY, mouseY);
                int y2 = Math.max(mouseDownY, mouseY);
                GuiHelper.drawBorder(x1, y1, x2, y2, Color.BLUE);
            }
        } else
        {
            Control c = selected;
            if (c != null)
            {
                GuiHelper.drawBorder(c.getX() - 1, c.getY() - 1, c.getX() + c.getWidth() + 1, c.getY() + c.getHeight() + 1, Color.RED);
            }
            c = window.getControlAt(mouseX, mouseY);
            if (c != null && c != window)
            {
                GuiHelper.drawBorder(c.getX() - 1, c.getY() - 1, c.getX() + c.getWidth() + 1, c.getY() + c.getHeight() + 1, Color.WHITE);
            }
        }

        super.draw(mouseX, mouseY, renderTick);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        window.drawForeground(mouseX, mouseY);

        super.drawForeground(mouseX, mouseY);
    }
}
