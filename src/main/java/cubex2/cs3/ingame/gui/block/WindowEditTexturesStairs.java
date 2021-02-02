package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowEditTexturesStairs extends WindowEditTexturesBase
{
    private Button btnRotate;
    private int rotation = 3;

    public WindowEditTexturesStairs(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);

        btnRotate = button("Rotate").width(40).bottom(worldDisplay, 0, true).right(worldDisplay, 3).add();

        world.setMetadata(rotation, 0, 0, 0);

        worldDisplay.setCam(1.5f, 1.5f, 1.5f);
        worldDisplay.setLook(0.5f, 0.5f, 0.5f);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnRotate)
        {
            rotation = (rotation + 1) % 8;
            world.setMetadata(rotation, 0, 0, 0);
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

}
