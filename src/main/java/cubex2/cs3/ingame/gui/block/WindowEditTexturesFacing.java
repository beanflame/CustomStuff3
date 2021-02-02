package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.block.attributes.FacingAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowEditTexturesFacing extends WindowEditTexturesBase
{
    private static final String[] textures = new String[]{"front", "top", "back", "bottom", "sides"};

    private Button btnRotate;
    private int rotation = 3;

    private CheckBox cbRotateSideTextures;
    private FacingAttributes attributes;
    private boolean prevRotateSideTextures;

    public WindowEditTexturesFacing(WrappedBlock block, boolean transparent, boolean semiTransparent, boolean tileTransparent)
    {
        super(block, textures, transparent, semiTransparent, tileTransparent);
        world.setMetadata(3, 0, 0, 0);

        attributes = (FacingAttributes) block.container;

        cbRotateSideTextures = checkBox("Rotate side textures").below(lastCheckBox, 7).add();
        cbRotateSideTextures.setIsChecked(attributes.rotateSideTextures);

        btnRotate = button("Rotate").width(40).bottom(worldDisplay, 0, true).right(worldDisplay, 3).add();

        worldDisplay.setCam(1.5f, 1.5f, 1.5f);
        worldDisplay.setLook(0.5f, 0.5f, 0.5f);
    }

    public WindowEditTexturesFacing(WrappedBlock block)
    {
        this(block, true, true, true);
    }

    @Override
    protected void applyChanges()
    {
        attributes.rotateSideTextures = cbRotateSideTextures.getIsChecked();

        super.applyChanges();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnRotate)
        {
            rotation = (rotation + 1) % 6;
            world.setMetadata(rotation, 0, 0, 0);
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    protected void preDraw()
    {
        prevRotateSideTextures = attributes.rotateSideTextures;
        attributes.rotateSideTextures = cbRotateSideTextures.getIsChecked();
    }

    @Override
    protected void postDraw()
    {
        attributes.rotateSideTextures = prevRotateSideTextures;
    }
}
