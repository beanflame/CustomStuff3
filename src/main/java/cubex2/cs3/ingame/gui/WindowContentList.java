package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContent;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public abstract class WindowContentList<T extends BaseContent> extends Window implements IListBoxItemClickListener<T>, IWindowClosedListener
{
    protected final BaseContentPack pack;
    protected ListBox<T> listBox;
    protected final Class<T> clazz;

    public WindowContentList(Class<T> clazz, String title, int width, int height, BaseContentPack pack)
    {
        this(clazz, title, NEW | EDIT | DELETE | BACK, width, height, pack);
    }

    public WindowContentList(Class<T> clazz, String title, int buttons, int width, int height, BaseContentPack pack)
    {
        super(title, buttons, width, height);
        this.clazz = clazz;
        this.pack = pack;

        ListBoxDescription<T> desc = new ListBoxDescription<T>(7, 7);
        desc.elements = pack.getContentRegistry(clazz).getContentList();
        desc.canSelect = true;
        modifyListBoxDesc(desc);
        listBox = listBox(desc).fillWidth(7).top(7).add();

        if (btnEdit != null)
            btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    protected abstract void modifyListBoxDesc(ListBoxDescription<T> desc);

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(createNewContentWindow());
        } else if (c == btnEdit && btnEdit != null)
        {
            GuiBase.openWindow(createEditContentWindow(listBox.getSelectedItem()));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(clazz).getContentList());
            btnDelete.setEnabled(false);
            if (btnEdit != null)
                btnEdit.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    protected abstract Window createNewContentWindow();

    protected abstract Window createEditContentWindow(T content);

    @Override
    public void windowClosed(Window window)
    {
        listBox.updateElements(pack.getContentRegistry(clazz).getContentList());
        btnDelete.setEnabled(false);
        if (btnEdit != null)
            btnEdit.setEnabled(false);
    }

    @Override
    public void itemClicked(T item, ListBox<T> listBox, int button)
    {
        if (btnEdit != null)
            btnEdit.setEnabled(listBox.getSelectedIndex() != -1 && listBox.getSelectedItem().canEdit());
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
