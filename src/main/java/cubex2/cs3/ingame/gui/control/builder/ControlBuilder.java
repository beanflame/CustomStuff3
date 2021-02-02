package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;

public abstract class ControlBuilder<T extends Control>
{
    public ControlContainer container;

    protected Anchor anchor = new Anchor();

    public int width = 0;
    public int height = 0;

    protected int offsetX = 0;
    protected int offsetY = 0;

    public ControlBuilder(ControlContainer c)
    {
        container = c;
    }

    public ControlBuilder<T> left(int distance)
    {
        return left(container, distance, true);
    }

    public ControlBuilder<T> left(Control c, int distance)
    {
        return left(c, distance, false);
    }

    public ControlBuilder<T> left(Control c, int distance, boolean anchorLeftSide)
    {
        anchor.distanceLeft = distance;
        anchor.controlLeft = c;
        anchor.sameSideLeft = anchorLeftSide;
        return this;
    }

    public ControlBuilder<T> right(int distance)
    {
        return right(container, distance, true);
    }

    public ControlBuilder<T> right(Control c, int distance)
    {
        return right(c, distance, false);
    }

    public ControlBuilder<T> right(Control c, int distance, boolean anchorRightSide)
    {
        anchor.distanceRight = distance;
        anchor.controlRight = c;
        anchor.sameSideRight = anchorRightSide;
        return this;
    }

    public ControlBuilder<T> top(int distance)
    {
        return top(container, distance, true);
    }

    public ControlBuilder<T> top(Control c, int distance)
    {
        return top(c, distance, false);
    }

    public ControlBuilder<T> top(Control c, int distance, boolean anchorTopSide)
    {
        anchor.distanceTop = distance;
        anchor.controlTop = c;
        anchor.sameSideTop = anchorTopSide;
        return this;
    }

    public ControlBuilder<T> bottom(int distance)
    {
        return bottom(container, distance, true);
    }

    public ControlBuilder<T> bottom(Control c, int distance)
    {
        return bottom(c, distance, false);
    }

    public ControlBuilder<T> bottom(Control c, int distance, boolean anchorBottomSide)
    {
        anchor.distanceBottom = distance;
        anchor.controlBottom = c;
        anchor.sameSideBottom = anchorBottomSide;
        return this;
    }

    public ControlBuilder<T> below(Control c)
    {
        return below(c, 3);
    }

    public ControlBuilder<T> below(Control c, int gap)
    {
        return top(c, gap).left(c, 0, true);
    }

    public ControlBuilder<T> rightTo(Control c)
    {
        return rightTo(c, 3);
    }

    public ControlBuilder<T> rightTo(Control c, int gap)
    {
        return left(c, gap).top(c, 0, true);
    }

    public ControlBuilder<T> at(int x, int y)
    {
        return left(x).top(y);
    }

    public ControlBuilder<T> offset(int x, int y)
    {
        offsetX = x;
        offsetY = y;
        return this;
    }

    public ControlBuilder<T> centerHor()
    {
        return centerHor(0);
    }

    public ControlBuilder<T> centerHor(int offset)
    {
        anchor.controlLeft = container;
        anchor.controlRight = container;
        anchor.distanceLeft = 1;
        anchor.distanceRight = 1;
        offsetX = offset;
        return this;
    }

    public ControlBuilder<T> centerVert()
    {
        return centerVert(0);
    }

    public ControlBuilder<T> centerVert(int offset)
    {
        anchor.controlTop = container;
        anchor.controlBottom = container;
        anchor.sameSideTop = true;
        anchor.sameSideBottom = true;
        anchor.distanceTop = 1;
        anchor.distanceBottom = 1;
        offsetY = offset;
        return this;
    }

    public ControlBuilder<T> size(int w, int h)
    {
        width = w;
        height = h;
        return this;
    }

    public ControlBuilder<T> width(int w)
    {
        width = w;
        return this;
    }

    public ControlBuilder<T> fillWidth(int gap)
    {
        return left(gap).right(gap).width(-1);
    }

    public ControlBuilder<T> fill()
    {
        return left(0).right(0).top(0).bottom(0).size(-1, -1);
    }

    public ControlBuilder<T> height(int h)
    {
        height = h;
        return this;
    }

    public ControlBuilder<T> fillHeight(int gap)
    {
        return top(gap).bottom(gap).height(-1);
    }

    public T add()
    {
        T t = newInstance();
        container.addControl(t);
        return t;
    }

    protected abstract T newInstance();
}
