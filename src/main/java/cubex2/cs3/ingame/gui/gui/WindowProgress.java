package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.ProgressData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.TextBoxValidators;

public abstract class WindowProgress<T extends ImageProgressBar, U extends ProgressData> extends WindowEditOrCreateControl<T, U> implements IStringProvider<Integer>
{
    protected TextBox tbName;
    protected TextureTextBox tbTexture;
    protected NumericUpDown nupU;
    protected NumericUpDown nupV;
    protected DropBox<Integer> dbDirection;


    public WindowProgress(String name, WrappedGui gui, Window window, int x, int y, int width, int height)
    {
        super("Create " + name + " Progress", gui, window, x, y, width, height);
    }

    public WindowProgress(String name, WrappedGui gui, Window window, T control, U data)
    {
        super("Edit " + name + " Progress", gui, window, control, data);

        tbName.setText(data.name);
        tbTexture.setText(data.texture != null ? data.texture.toString() : "");
        nupU.setValue(data.u);
        nupV.setValue(data.v);
        dbDirection.setSelectedValue(data.direction);
    }

    @Override
    protected void initControls(boolean hasSize)
    {
        super.initControls(hasSize);

        row("Module Name");
        tbName = row(textBox());
        row("Texture");
        tbTexture = row(textureTextBox(pack, "gui"));
        row("Texture X");
        nupU = row(numericUpDown());
        row("Texture Y");
        nupV = row(numericUpDown());
        row("Progress Direction", Strings.INFO_PROGRESS_DIRECTION);
        dbDirection = row(dropBox(new Integer[]{0, 1, 2, 3}));
        dbDirection.setStringProvider(this);
        dbDirection.parentMouseOverCheck = false;

        tbName.setValidityProvider(TextBoxValidators.NOT_EMPTY);
        dbDirection.setSelectedValue(0);
    }

    @Override
    protected void edit()
    {
        data.name = tbName.getText();
        data.texture = control.texture = tbTexture.getLocation();
        data.u = control.u = nupU.getValue();
        data.v = control.v = nupV.getValue();
        data.direction = control.direction = dbDirection.getSelectedValue();
    }

    @Override
    public String getStringFor(Integer value)
    {
        switch (value)
        {
            case ImageProgressBar.UP:
                return "Up";
            case ImageProgressBar.DOWN:
                return "Down";
            case ImageProgressBar.LEFT:
                return "Left";
            case ImageProgressBar.RIGHT:
                return "Right";
        }
        return "UNDEFINED";
    }
}
