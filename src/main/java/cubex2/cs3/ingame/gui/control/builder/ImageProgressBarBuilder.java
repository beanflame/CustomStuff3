package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.ImageProgressBar;
import net.minecraft.util.ResourceLocation;

public class ImageProgressBarBuilder<T extends ImageProgressBar> extends ControlBuilder<T>
{
    protected ResourceLocation texture;
    protected int u;
    protected int v;
    protected int direction;

    public ImageProgressBarBuilder(ResourceLocation texture, int u, int v, int direction, ControlContainer c)
    {
        super(c);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.direction = direction;
    }

    @Override
    protected T newInstance()
    {
        return (T) new ImageProgressBar(texture, u, v, direction, width, height, anchor, offsetX, offsetY, container);
    }
}
