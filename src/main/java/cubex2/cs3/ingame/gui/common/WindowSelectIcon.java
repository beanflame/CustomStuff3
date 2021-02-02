package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.ISelectElementCallback;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Tab;
import cubex2.cs3.ingame.gui.control.TabChangedListener;
import cubex2.cs3.ingame.gui.control.TabControl;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.Filter;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class WindowSelectIcon extends Window implements IListBoxItemClickListener<ResourceLocation>, TabChangedListener
{
    private ListBox<ResourceLocation> lbPack;
    private ListBox<ResourceLocation> lbAll;
    private TabControl tabControl;
    private String selectedTexture;

    private ISelectElementCallback<String> callback;

    public WindowSelectIcon(List<ResourceLocation> packIcons, List<ResourceLocation> allIcons, ISelectElementCallback<String> callback)
    {
        super("Select Texture", SELECT | CANCEL, 197, 211);
        this.callback = callback;

        tabControl = tabControl(70, 20).fill().add();
        tabControl.listener = this;
        Tab packTab = tabControl.addTab("Pack");
        Tab allTab = tabControl.addTab("All");

        ListBoxDescription<ResourceLocation> desc = new ListBoxDescription<ResourceLocation>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = packIcons;
        desc.hasSearchBar = true;
        desc.filter = Filter.RESOURCE_LOCATION;
        desc.listBoxItemMeta = 1;
        lbPack = packTab.listBox(desc).left(7).top(7).right(7).add();
        claimFocus(lbPack.getSearchBox());

        ListBoxDescription<ResourceLocation> desc1 = new ListBoxDescription<ResourceLocation>(7, 7);
        desc1.elementWidth = 22;
        desc1.elementHeight = 22;
        desc1.columns = 7;
        desc1.rows = 7;
        desc1.elements = allIcons;
        desc1.hasSearchBar = true;
        desc1.filter = Filter.RESOURCE_LOCATION;
        desc1.listBoxItemMeta = 1;
        lbAll = allTab.listBox(desc1).left(7).top(7).right(7).add();

        btnSelect.setEnabled(false);
    }

    public String getSelectedTexture()
    {
        return selectedTexture;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCancel)
        {
            selectedTexture = null;
            GuiBase.openPrevWindow();
        } else if (c == btnSelect)
        {
            if (callback != null)
            {
                callback.itemSelected(selectedTexture);
            }
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(ResourceLocation item, ListBox<ResourceLocation> listBox, int button)
    {
        ListBox otherLB = listBox == lbPack ? lbAll : lbPack;
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        otherLB.removeSelection();

        selectedTexture = ClientHelper.resourceToIconString(listBox.getSelectedItem());
    }

    @Override
    public void tabChanged(TabControl tabControl, Tab tab)
    {
        if (tab.title.equals("Pack"))
            claimFocus(lbPack.getSearchBox());
        else
            claimFocus(lbAll.getSearchBox());
    }
}
