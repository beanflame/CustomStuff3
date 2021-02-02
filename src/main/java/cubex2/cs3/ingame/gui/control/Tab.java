package cubex2.cs3.ingame.gui.control;

public class Tab extends ControlContainer
{
    public final String title;

    public Tab(String title, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.title = title;
    }
}
