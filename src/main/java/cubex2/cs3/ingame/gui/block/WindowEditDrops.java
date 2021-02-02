package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.BlockDrop;
import net.minecraft.item.ItemStack;

public class WindowEditDrops extends WindowEditBlockAttribute implements IWindowClosedListener, IListBoxItemClickListener<BlockDrop.DropData>
{
    private ListBox<BlockDrop.DropData> listBox;

    public WindowEditDrops(WrappedBlock block)
    {
        super(block, "drops", NEW | EDIT | DELETE | BACK, 263, 160);

        ListBoxDescription<BlockDrop.DropData> desc = new ListBoxDescription<BlockDrop.DropData>(7, 7);
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = container.drop.getDrops();
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
            GuiBase.openWindow(new WindowSelectItem(false));
        } else if (c == btnDelete)
        {
            BlockDrop.DropData drop = listBox.getSelectedItem();
            container.drop.getDrops().remove(drop);
            listBox.updateElements(container.drop.getDrops());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);

            wrappedBlock.getPack().save();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    protected void handleEditButtonClicked()
    {
        applyChanges();
    }

    @Override
    protected void applyChanges()
    {
        GuiBase.openWindow(new WindowEditDropData(wrappedBlock, listBox.getSelectedItem()));
    }

    @Override
    public void itemClicked(BlockDrop.DropData item, ListBox<BlockDrop.DropData> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    public void windowClosed(Window window)
    {
        if (window instanceof WindowSelectItem)
        {
            WindowSelectItem wdw = (WindowSelectItem) window;
            ItemStack stack = wdw.getSelectedStack();
            if (stack != null)
            {
                BlockDrop.DropData drop = new BlockDrop.DropData(stack.getItem(), stack.getItemDamage(), 1, 1);
                container.drop.getDrops().add(drop);
                listBox.updateElements(container.drop.getDrops());
                wrappedBlock.getPack().save();
            }
        }
        else if (window instanceof WindowEditDropData)
        {
            listBox.updateElements(container.drop.getDrops());
        }

        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
