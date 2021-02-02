package cubex2.cs3.ingame.gui.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.ShiftClickRule;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowEditShiftClickRules extends WindowEditContainerGuiAttribute implements IListBoxItemClickListener<ShiftClickRule>, IWindowClosedListener<WindowEditOrCreateShiftClickRule>
{
    private ListBox<ShiftClickRule> listBox;

    public WindowEditShiftClickRules(WrappedGui gui)
    {
        super(gui, "Shift-Click Rules", NEW | EDIT | DELETE | BACK, 263, 160);

        ListBoxDescription<ShiftClickRule> desc = new ListBoxDescription<ShiftClickRule>(7, 7);
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = Lists.newArrayList(container.shiftClickRules.list);
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
            GuiBase.openWindow(new WindowEditOrCreateShiftClickRule(wrappedGui));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateShiftClickRule(wrappedGui, listBox.getSelectedItem()));
        } else if (c == btnDelete)
        {
            ShiftClickRule rule = listBox.getSelectedItem();
            container.shiftClickRules.list.remove(rule);
            listBox.updateElements(container.shiftClickRules.list);
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);

            wrappedGui.getPack().save();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(ShiftClickRule item, ListBox<ShiftClickRule> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    public void windowClosed(WindowEditOrCreateShiftClickRule window)
    {
        listBox.updateElements(container.shiftClickRules.list);
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
