package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.TabControl;

public class TabControlBuilder extends ControlBuilder<TabControl>
{
    private int tabWidth;
    private int tabHeight;

    public TabControlBuilder(int tabWidth, int tabHeight, ControlContainer c)
    {
        super(c);
        this.tabWidth = tabWidth;
        this.tabHeight = tabHeight;
    }

    @Override
    protected TabControl newInstance()
    {
        return new TabControl(tabWidth, tabHeight, width, height, anchor, offsetX, offsetY, container);
    }
}
