package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.PictureBox;
import net.minecraft.util.ResourceLocation;

public class PictureBoxBuilder extends ControlBuilder<PictureBox>
{
    private final ResourceLocation texture;
    private final int u;
    private final int v;

    public PictureBoxBuilder(ResourceLocation texture, int u, int v, ControlContainer c)
    {
        super(c);
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    @Override
    protected PictureBox newInstance()
    {
        return new PictureBox(texture, u, v, width, height, anchor, offsetX, offsetY, container);
    }
}
