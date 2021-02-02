package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContent;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.Control;

public abstract class WindowEditOrCreate<T extends BaseContent> extends Window
{
    protected final BaseContentPack pack;
    protected T editingContent;

    public WindowEditOrCreate(String title, int width, int height, BaseContentPack pack)
    {
        super(title, CREATE | CANCEL, width, height);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreate(String title, int width, int height, T content, BaseContentPack pack)
    {
        super(title, EDIT | CANCEL, width, height);
        this.pack = pack;
        editingContent = content;

        initControls();
    }

    protected abstract void initControls();

    protected abstract T createContent();

    protected abstract void editContent();

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            createContent().apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editContent();
            editingContent.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
