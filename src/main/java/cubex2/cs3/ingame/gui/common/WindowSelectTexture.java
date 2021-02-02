package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.ISelectElementCallback;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.Filter;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class WindowSelectTexture extends Window implements IListBoxItemClickListener<ResourceLocation>
{
    private final ISelectElementCallback<String> callback;
    private ListBox<ResourceLocation> listBox;

    private String selectedTexture;

    public WindowSelectTexture(List<ResourceLocation> textures, ISelectElementCallback<String> callback)
    {
        super("Select Texture", SELECT | CANCEL, 171, 211);
        this.callback = callback;

        ListBoxDescription<ResourceLocation> desc = new ListBoxDescription<ResourceLocation>(7, 7);
        desc.elementWidth = 128 + 6;
        desc.elementHeight = 64 + 6;
        desc.columns = 1;
        desc.rows = 2;
        desc.elements = textures;
        desc.hasSearchBar = true;
        desc.filter = Filter.RESOURCE_LOCATION;
        listBox = listBox(desc).left(7).top(7).right(7).add();
        claimFocus(listBox.getSearchBox());

        btnSelect.setEnabled(false);
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
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        selectedTexture = listBox.getSelectedItem().toString();
    }
}
