package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.ControlData;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.ScrollContainer;

public abstract class WindowEditOrCreateControl<T extends Control, U extends ControlData> extends Window
{
    protected BaseContentPack pack;
    protected GuiData guiData;
    protected Window window;

    protected NumericUpDown nupX;
    protected NumericUpDown nupY;
    protected NumericUpDown nupWidth;
    protected NumericUpDown nupHeight;
    protected T control;
    protected U data;

    private ScrollContainer scroll;
    protected ControlContainer content;

    public WindowEditOrCreateControl(String title, WrappedGui gui, Window window, int x, int y, int width, int height)
    {
        super(title, CREATE | CANCEL, 200, 200);
        this.pack = gui.getPack();
        this.guiData = gui.container.guiData;
        this.window = window;

        initControls(width != -1);
        scroll.automaticTotalHeight();

        nupX.setValue(x);
        nupY.setValue(y);
        if (width != -1)
        {
            nupWidth.setValue(width);
            nupHeight.setValue(height);
        }

        data = createData();
    }

    public WindowEditOrCreateControl(String title, WrappedGui gui, Window window, T control, U data)
    {
        super(title, EDIT | CANCEL, 200, 200);
        this.pack = gui.getPack();
        this.guiData = gui.container.guiData;
        this.window = window;
        this.control = control;
        this.data = data;

        initControls(data.isSizeable());
        scroll.automaticTotalHeight();

        nupX.setValue(data.x);
        nupY.setValue(data.y);
        if (data.isSizeable())
        {
            nupWidth.setValue(data.width);
            nupHeight.setValue(data.height);
        }
    }

    protected void initControls(boolean hasSize)
    {
        scroll = scrollContainer(1).fillWidth(7).top(7).bottom(btnCancel, 5).add();
        content = scroll.content();

        content.defaultNoOffset = true;
        defaultNoOffset = true;
        defaultBuilderContainer = content;

        row("X");
        nupX = row(numericUpDown());
        nupX.setMinValue(Integer.MIN_VALUE);
        row("Y");
        nupY = row(numericUpDown());
        nupY.setMinValue(Integer.MIN_VALUE);
        if (hasSize)
        {
            row("Width");
            nupWidth = row(numericUpDown());
            nupWidth.setMinValue(1);
            row("Height");
            nupHeight = row(numericUpDown());
            nupHeight.setMinValue(1);
        }
    }

    protected abstract U createData();

    protected abstract void edit();

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            ControlData data = createData();
            guiData.add(data);
            data.addToWindow(window, null).controlTag = data;
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            data.x = nupX.getValue();
            data.y = nupY.getValue();
            if (data.isSizeable())
            {
                data.width = nupWidth.getValue();
                data.height = nupHeight.getValue();
            }

            edit();

            control.offsetX = data.x;
            control.offsetY = data.y;
            if (data.isSizeable())
            {
                control.width = data.width;
                control.height = data.height;
            }

            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
