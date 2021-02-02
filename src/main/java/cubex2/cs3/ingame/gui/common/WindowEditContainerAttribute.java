package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;

public abstract class WindowEditContainerAttribute<T extends AttributeContainer> extends Window
{
    protected final T container;

    public WindowEditContainerAttribute(T container, String title, int usedControls, int width, int height)
    {
        super(title, usedControls, width, height);
        this.container = container;
    }

    public WindowEditContainerAttribute(T container, String title, int width, int height)
    {
        super(title, EDIT | CANCEL, width, height);
        this.container = container;
    }

    @Override
    protected void handleEditButtonClicked()
    {
        applyChanges();
        saveAndClose();
    }

    protected void applyChanges()
    {

    }

    protected void saveAndClose()
    {
        container.getPack().save();
        GuiBase.openPrevWindow();
    }
}
