package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.TextureTextBox;

public class WindowEditBackground extends WindowEditGuiAttribute
{
    private TextureTextBox tbBackground;
    private NumericUpDown nupU;
    private NumericUpDown nupV;

    public WindowEditBackground(WrappedGui gui)
    {
        super(gui, "Background", 150, 140);

        row("Background");
        tbBackground = row(textureTextBox(gui.getPack(), "gui"));
        row("Texture X");
        nupU = row(numericUpDown());
        row("Texture Y");
        nupV = row(numericUpDown());

        nupU.setMaxValue(255);
        nupV.setMaxValue(255);
    }

    @Override
    protected void applyChanges()
    {
        container.background = tbBackground.getText().length() > 0 ? tbBackground.getLocation() : null;
        container.bgU = nupU.getValue();
        container.bgV = nupV.getValue();
    }
}
