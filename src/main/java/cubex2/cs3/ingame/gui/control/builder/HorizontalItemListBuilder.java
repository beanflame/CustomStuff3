package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.HorizontalItemList;

public class HorizontalItemListBuilder extends ControlBuilder<HorizontalItemList>
{
    private int numItems;

    public HorizontalItemListBuilder(int numItems, ControlContainer c)
    {
        super(c);
        this.numItems = numItems;
    }

    @Override
    protected HorizontalItemList newInstance()
    {
        return new HorizontalItemList(numItems, anchor, offsetX, offsetY, container);
    }
}
