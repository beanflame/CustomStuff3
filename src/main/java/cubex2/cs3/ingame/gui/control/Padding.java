package cubex2.cs3.ingame.gui.control;

public class Padding
{
    public final int left;
    public final int right;
    public final int top;
    public final int bottom;

    public Padding(int left, int right, int top, int bottom)
    {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public Padding()
    {
        left = right = top = bottom = 0;
    }
}
