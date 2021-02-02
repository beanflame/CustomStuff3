package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiAttributes;
import cubex2.cs3.ingame.gui.common.WindowEditContainerAttribute;

public class WindowEditGuiAttribute extends WindowEditContainerAttribute<GuiAttributes>
{
    protected final WrappedGui wrappedGui;

    public WindowEditGuiAttribute(WrappedGui gui, String title, int usedControls, int width, int height)
    {
        super(gui.container, title, usedControls, width, height);
        wrappedGui = gui;
    }

    public WindowEditGuiAttribute(WrappedGui gui, String title, int width, int height)
    {
        super(gui.container, title, width, height);
        wrappedGui = gui;
    }
}
