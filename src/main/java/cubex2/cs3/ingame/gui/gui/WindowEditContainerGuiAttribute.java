package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiContainerAttributes;
import cubex2.cs3.ingame.gui.common.WindowEditContainerAttribute;

public class WindowEditContainerGuiAttribute extends WindowEditContainerAttribute<GuiContainerAttributes>
{
    protected final WrappedGui wrappedGui;

    public WindowEditContainerGuiAttribute(WrappedGui gui, String title, int usedControls, int width, int height)
    {
        super((GuiContainerAttributes) gui.container, title, usedControls, width, height);
        wrappedGui = gui;
    }

    public WindowEditContainerGuiAttribute(WrappedGui gui, String title, int width, int height)
    {
        super((GuiContainerAttributes) gui.container, title, width, height);
        wrappedGui = gui;
    }
}
