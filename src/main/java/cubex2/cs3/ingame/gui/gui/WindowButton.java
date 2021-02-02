package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.ButtonData;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.util.ScriptWrapper;

public class WindowButton extends WindowEditOrCreateControl<Button, ButtonData>
{
    private TextBox tbText;
    private Button btnOnClicked;

    public WindowButton(WrappedGui gui, Window window, int x, int y, int width, int height)
    {
        super("Create Button", gui, window, x, y, width, height);
    }

    public WindowButton(WrappedGui gui, Window window, Button control, ButtonData data)
    {
        super("Edit Button", gui, window, control, data);

        tbText.setText(data.text);
    }

    @Override
    protected void initControls(boolean hasSize)
    {
        super.initControls(hasSize);

        row("Text");
        tbText = row(textBox());
        row("OnClicked");
        btnOnClicked = row(button("Edit"));
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnOnClicked)
        {
            if (data.onClicked == null)
                data.onClicked = new ScriptWrapper(null);
            GuiBase.openWindow(new WindowEditControlScript(data.onClicked, "gui/button/onClicked.txt"));
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    protected ButtonData createData()
    {
        return new ButtonData(nupX.getValue(), nupY.getValue(), nupWidth.getValue(), nupHeight.getValue(), tbText.getText());
    }

    @Override
    protected void edit()
    {
        data.text = tbText.getText();
        control.setText(data.text);
    }
}
