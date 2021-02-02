package cubex2.cs3.ingame.gui.control;

public class Anchor
{
    public int distanceLeft = -1;
    public int distanceRight = -1;
    public int distanceTop = -1;
    public int distanceBottom = -1;

    /**
     * If true, left side is anchored at left side of other control instead of the right side.
     */
    public boolean sameSideLeft = false;
    /**
     * If true, left side is anchored at right side of other control instead of the left side.
     */
    public boolean sameSideRight = false;
    /**
     * If true, left side is anchored at top side of other control instead of the bottom side.
     */
    public boolean sameSideTop = false;
    /**
     * If true, left side is anchored at bottom side of other control instead of the top side.
     */
    public boolean sameSideBottom = false;

    public Control controlLeft = null;
    public Control controlRight = null;
    public Control controlTop = null;
    public Control controlBottom = null;

    public Anchor(int left, int right, int top, int bottom)
    {
        distanceLeft = left;
        distanceRight = right;
        distanceTop = top;
        distanceBottom = bottom;
    }

    public Anchor()
    {
    }

    public boolean isLeft()
    {
        return distanceLeft != -1 && controlLeft != null;
    }

    public boolean isRight()
    {
        return distanceRight != -1 && controlRight != null;
    }

    public boolean isTop()
    {
        return distanceTop != -1 && controlTop != null;
    }

    public boolean isBottom()
    {
        return distanceBottom != -1 && controlBottom != null;
    }

    public int getAnchorLeft()
    {
        return controlLeft.bounds.getX() + (sameSideLeft ? 0 : controlLeft.bounds.getWidth());
    }

    public int getAnchorRight()
    {
        return controlRight.bounds.getX() + (sameSideRight ? controlRight.bounds.getWidth() : 0);
    }

    public int getAnchorTop()
    {
        return controlTop.bounds.getY() + (sameSideTop ? 0 : controlTop.bounds.getHeight());
    }

    public int getAnchorBottom()
    {
        return controlBottom.bounds.getY() + (sameSideBottom ? controlBottom.bounds.getHeight() : 0);
    }
}
