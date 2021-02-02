package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

import java.util.List;

public class WindowSelectString extends Window implements IListBoxItemClickListener<String>
{
    private String selectedElement;

    private ISelectElementCallback<String> callback;

    public WindowSelectString(String title, List<String> elements)
    {
        super(title, SELECT | CANCEL, 200, 150);

        ListBoxDescription<String> desc = new ListBoxDescription<String>(7, 7);
        desc.elementHeight = 18;
        desc.columns = 1;
        desc.rows = 6;
        desc.elements = elements;
        desc.sorted = true;
        listBox(desc).fillWidth(7).top(7).add();

        btnSelect.setEnabled(false);
    }

    public ISelectElementCallback<String> getCallback()
    {
        return callback;
    }

    public void setCallback(ISelectElementCallback<String> callback)
    {
        this.callback = callback;
    }

    public String getSelectedElement()
    {
        return selectedElement;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCancel)
        {
            selectedElement = null;
            GuiBase.openPrevWindow();
        } else if (c == btnSelect)
        {
            if (callback != null)
            {
                callback.itemSelected(selectedElement);
            }
            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    public void itemClicked(String item, ListBox<String> listBox, int button)
    {
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        selectedElement = listBox.getSelectedItem();
    }
}
