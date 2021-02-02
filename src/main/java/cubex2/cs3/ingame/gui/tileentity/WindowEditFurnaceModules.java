package cubex2.cs3.ingame.gui.tileentity;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.tileentity.data.FurnaceModule;

public class WindowEditFurnaceModules extends WindowEditInventoryTEAttribute implements IListBoxItemClickListener<FurnaceModule>, IWindowClosedListener<WindowEditOrCreateFurnaceModule>
{
    private ListBox<FurnaceModule> listBox;

    public WindowEditFurnaceModules(WrappedTileEntity tile)
    {
        super(tile, "Furnace Modules", NEW | EDIT | DELETE | BACK, 263, 160);

        ListBoxDescription<FurnaceModule> desc = new ListBoxDescription<FurnaceModule>(7, 7);
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = Lists.newArrayList(container.furnaceModules.list);
        desc.canSelect = true;
        listBox = listBox(desc).fillWidth(7).top(7).add();

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateFurnaceModule(wrappedTileEntity));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateFurnaceModule(wrappedTileEntity, listBox.getSelectedItem()));
        } else if (c == btnDelete)
        {
            FurnaceModule rule = listBox.getSelectedItem();
            container.furnaceModules.list.remove(rule);
            listBox.updateElements(container.furnaceModules.list);
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);

            wrappedTileEntity.getPack().save();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(FurnaceModule item, ListBox<FurnaceModule> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    public void windowClosed(WindowEditOrCreateFurnaceModule window)
    {
        listBox.updateElements(container.furnaceModules.list);
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
