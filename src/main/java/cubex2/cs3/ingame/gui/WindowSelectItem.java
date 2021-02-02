package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IValueListener;
import cubex2.cs3.ingame.gui.control.ItemSelectionArray;
import cubex2.cs3.ingame.gui.control.builder.ItemSelectionArrayBuilder;
import cubex2.cs3.util.Filter;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;

public class WindowSelectItem extends Window
{
    private ItemSelectionArray itemsArray;
    private ItemStack selectedStack = null;
    private boolean wildCardStacks = true;

    private ISelectElementCallback<ItemStack> callback;

    public WindowSelectItem()
    {
        this(false, null);
    }

    public WindowSelectItem(boolean wildCardStacks)
    {
        this(wildCardStacks, null);
    }

    public WindowSelectItem(boolean wildCardStacks, ISelectElementCallback<ItemStack> callback)
    {
        super("Select Item", SELECT | CANCEL, 197, 211);
        this.wildCardStacks = wildCardStacks;
        this.callback = callback;

        itemsArray = new ItemSelectionArrayBuilder(ItemStackHelper.getAllItemStacks(wildCardStacks), defaultBuilderContainer).left(7).top(7).right(7).height(7 * 23 - 5 + 21).add();
        itemsArray.listener = listener;
        itemsArray.filter = Filter.ITEM_STACK;
        claimFocus(itemsArray.getSearchBox());


        btnSelect.setEnabled(false);
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
            selectedStack = itemsArray.getSelectedStack();
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

    private final IValueListener<ItemSelectionArray> listener = new IValueListener<ItemSelectionArray>()
    {
        @Override
        public void onValueChanged(ItemSelectionArray control)
        {
            btnSelect.setEnabled(control.getSelectedStack() != null);
        }
    };
}
