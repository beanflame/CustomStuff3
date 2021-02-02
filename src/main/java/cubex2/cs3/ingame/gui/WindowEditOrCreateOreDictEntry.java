package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class WindowEditOrCreateOreDictEntry extends WindowEditOrCreate<OreDictionaryEntry> implements IValidityProvider, IWindowClosedListener<WindowSelectItem>
{
    private ItemDisplay itemDisplay;
    private TextBox tbOreClass;
    private ItemDisplay[] itemDisplays;

    public WindowEditOrCreateOreDictEntry(BaseContentPack pack)
    {
        super("New Ore Dictionary Entry", 182, 201, pack);
    }

    public WindowEditOrCreateOreDictEntry(OreDictionaryEntry entry, BaseContentPack pack)
    {
        super("Edit Ore Dictionary Entry", 182, 201, entry, pack);
    }

    @Override
    protected void initControls()
    {
        row("Item:");
        itemDisplay = row(itemDisplay());
        row("Ore Class:");
        tbOreClass = row(textBox());
        Label label = row("Items in ore class:", 10);

        itemDisplay.useSelectItemDialog(false);
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        itemDisplay.setDrawSlotBackground();
        if (editingContent != null)
            itemDisplay.setItemStack(editingContent.stack);


        tbOreClass.setValidityProvider(this);
        if (editingContent != null)
        {
            tbOreClass.setText(String.valueOf(editingContent.oreClass));
        }

        itemDisplays = new ItemDisplay[9];
        for (int i = 0; i < itemDisplays.length; i++)
        {
            itemDisplays[i] = itemDisplay().left(7 + i * 19).top(label, 4).add().setDrawSlotBackground();
        }
        updateItemDisplays();
    }

    @Override
    protected OreDictionaryEntry createContent()
    {
        ItemStack stack = itemDisplay.getItemStack();
        String oreClass = tbOreClass.getText().trim();
        return new OreDictionaryEntry(oreClass, stack, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.stack = itemDisplay.getItemStack();
        editingContent.oreClass = tbOreClass.getText().trim();
    }

    private void updateItemDisplays()
    {
        List<ItemStack> ores = OreDictionary.getOres(tbOreClass.getText().trim());
        for (int i = 0; i < itemDisplays.length; i++)
        {
            if (i < ores.size())
            {
                itemDisplays[i].setItemStack(ores.get(i).copy());
            } else
            {
                itemDisplays[i].setItemStack(null);
            }
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tbOreClass.getText().trim();
        if (text.length() == 0)
        {
            message = "Enter a value.";
        }

        updateItemDisplays();

        return message;
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());
    }
}
