package cubex2.cs3.ingame.gui.item;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.ToolClass;

import java.util.List;

public class WindowEditToolClass extends WindowEditItemAttribute implements IWindowClosedListener<WindowCreateToolClass>, IListBoxItemClickListener<ToolClass>
{
    private List<ToolClass> toolClasses;
    private ListBox<ToolClass> lbToolClasses;

    public WindowEditToolClass(WrappedItem item)
    {
        super(item, "toolClass", NEW | DELETE | BACK, 200, 120);

        ListBoxDescription<ToolClass> desc = new ListBoxDescription<ToolClass>(7, 7);
        desc.rows = 6;
        desc.elements = Lists.newArrayList(item.container.toolClasses);
        desc.canSelect = true;
        lbToolClasses = listBox(desc).fillWidth(7).top(7).add();

        toolClasses = Lists.newArrayList(item.container.toolClasses);

        updateNewButton();
    }

    private void updateNewButton()
    {
        btnNew.setEnabled(toolClasses.size() != 1 || (!toolClasses.get(0).toolClass.equals("noHarvest") && !toolClasses.get(0).toolClass.equals("all")));
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowCreateToolClass(toolClasses.size() == 0));
        } else if (c == btnDelete)
        {
            ToolClass toolClass = lbToolClasses.getSelectedItem();
            toolClasses.remove(toolClass);
            lbToolClasses.updateElements(toolClasses);
            btnDelete.setEnabled(false);
            updateNewButton();
        } else if (c == btnBack)
        {
            applyChanges();
            saveAndClose();
        }
    }


    @Override
    protected void applyChanges()
    {
        for (ToolClass toolClass : wrappedItem.container.toolClasses)
        {
            wrappedItem.item.setHarvestLevel(toolClass.toolClass, -1);
        }

        wrappedItem.container.toolClasses = toolClasses.toArray(new ToolClass[toolClasses.size()]);

        for (ToolClass toolClass : wrappedItem.container.toolClasses)
        {
            wrappedItem.item.setHarvestLevel(toolClass.toolClass, toolClass.harvestLevel);
        }
    }

    @Override
    public void itemClicked(ToolClass item, ListBox<ToolClass> listBox, int button)
    {
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    public void windowClosed(WindowCreateToolClass window)
    {
        if (window.getCreatedClass() != null)
        {
            toolClasses.add(window.getCreatedClass());
            lbToolClasses.updateElements(toolClasses);
            updateNewButton();
        }
    }
}
