package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowContentPacks extends Window implements IListBoxItemClickListener<BaseContentPack>, IWindowClosedListener
{
    private ListBox<BaseContentPack> lbPacks;

    public WindowContentPacks()
    {
        super("Content Packs", NEW | BACK, 180, 201);

        ListBoxDescription<BaseContentPack> desc = new ListBoxDescription<BaseContentPack>(7, 7);
        desc.rows = 12;
        desc.elements = BaseContentPackLoader.instance().getContentPacks();
        desc.sorted = true;
        desc.canSelect = false;
        lbPacks = listBox(desc).left(7).right(7).top(7).height(12 * 14 - 1).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowNewPack());
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(BaseContentPack item, ListBox<BaseContentPack> listBox, int button)
    {
        if (button == 0 && !item.isZipped())
        {
            GuiBase.openWindow(new WindowEditPack(item));
        }
    }

    @Override
    public void windowClosed(Window window)
    {
        lbPacks.updateElements(BaseContentPackLoader.instance().getContentPacks());
    }
}
