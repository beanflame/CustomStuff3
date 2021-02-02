package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.ingame.gui.control.builder.ItemSelectionArrayBuilder;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.Filter;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.OreDictionaryClass;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.item.ItemStack;

public class WindowSelectRecipeInput extends Window implements IListBoxItemClickListener, TabChangedListener
{
    private BaseContentPack pack;
    private ItemSelectionArray itemsArray;
    private ListBox<OreDictionaryClass> lbOreDictClasses;
    private TabControl tabControl;
    private Object selectedInput = null;

    public WindowSelectRecipeInput(BaseContentPack pack)
    {
        super("Select", SELECT | CANCEL, 197, 211);
        this.pack = pack;

        tabControl = tabControl(70, 20).fill().add();
        Tab itemTab = tabControl.addTab("Items");
        Tab oreTab = tabControl.addTab("Ore Classes");

        itemsArray = new ItemSelectionArrayBuilder(ItemStackHelper.getAllItemStacks(), defaultBuilderContainer).left(7).top(7).right(7).height(7 * 23 - 5 + 21).add();
        itemsArray.listener = listener;
        itemsArray.filter = Filter.ITEM_STACK;
        claimFocus(itemsArray.getSearchBox());

        ListBoxDescription<OreDictionaryClass> desc1 = new ListBoxDescription<OreDictionaryClass>(7, 7);
        desc1.elementWidth = 22;
        desc1.elementHeight = 22;
        desc1.columns = 7;
        desc1.rows = 7;
        desc1.elements = OreDictionaryClass.getAllClasses();
        desc1.sorted = true;
        desc1.hasSearchBar = true;
        desc1.filter = Filter.ORE_CLASS;
        lbOreDictClasses = oreTab.listBox(desc1).left(7).top(7).right(7).add();

        btnSelect.setEnabled(false);
    }

    public RecipeInput getSelectedInput()
    {
        if (selectedInput == null) return null;
        return selectedInput instanceof ItemStack ? new RecipeInput((ItemStack) selectedInput) : new RecipeInput(((OreDictionaryClass) selectedInput).oreClass);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCancel)
        {
            selectedInput = null;
            GuiBase.openPrevWindow();
        } else if (c == btnSelect)
        {
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(Object item, ListBox listBox, int button)
    {
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        selectedInput = listBox.getSelectedItem();
    }

    @Override
    public void tabChanged(TabControl tabControl, Tab tab)
    {
        if (tab.title.equals("Items"))
            claimFocus(itemsArray.getSearchBox());
        else
            claimFocus(lbOreDictClasses.getSearchBox());
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
