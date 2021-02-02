package cubex2.cs3.ingame.gui;

import com.google.common.base.Predicate;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.Filter;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class WindowSelectBlock extends Window implements IListBoxItemClickListener<ItemStack>
{
    private ListBox<ItemStack> lbItems;
    private ItemStack selectedStack = null;
    private boolean wildCardStacks = true;
    private boolean subBlocks = true;

    private ISelectElementCallback<ItemStack> callback;
    private Predicate<ItemStack> itemFilter;

    public WindowSelectBlock()
    {
        this(false, false, null, null);
    }

    public WindowSelectBlock(boolean wildCardStacks, boolean subBlocks, ISelectElementCallback<ItemStack> callback, Predicate<ItemStack> itemFilter)
    {
        super("Select Block", SELECT | CANCEL, 197, 211);
        this.wildCardStacks = wildCardStacks;
        this.subBlocks = subBlocks;
        this.callback = callback;
        this.itemFilter = itemFilter;

        ListBoxDescription<ItemStack> desc = new ListBoxDescription<ItemStack>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = getStacks();
        desc.hasSearchBar = true;
        desc.filter = Filter.ITEM_STACK;
        lbItems = listBox(desc).left(7).top(7).right(7).add();

        btnSelect.setEnabled(false);
    }

    private List<ItemStack> getStacks()
    {
        List<ItemStack> stacks = subBlocks ? ItemStackHelper.getBlockStacks(wildCardStacks) :
                ItemStackHelper.getBlockStacks();

        if (itemFilter != null)
        {
            for (int i = 0; i < stacks.size(); i++)
            {
                if (!itemFilter.apply(stacks.get(i)))
                {
                    stacks.remove(i--);
                }
            }
        }

        return stacks;
    }

    public void setCallback(ISelectElementCallback<ItemStack> callback)
    {
        this.callback = callback;
    }

    public ItemStack getSelectedStack()
    {
        return selectedStack;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCancel)
        {
            selectedStack = null;
            GuiBase.openPrevWindow();
        } else if (c == btnSelect)
        {
            if (callback != null)
            {
                callback.itemSelected(selectedStack);
            }
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(ItemStack item, ListBox<ItemStack> listBox, int button)
    {
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        selectedStack = listBox.getSelectedItem();
    }
}
