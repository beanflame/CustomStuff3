package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.CreativeTab;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateCreativeTab extends WindowEditOrCreate<CreativeTab>
{
    private TextBox tbName;
    private TextBox tbLabel;
    private ItemDisplay display;

    public WindowEditOrCreateCreativeTab(BaseContentPack pack)
    {
        super("New Creative Tab", 180, 128, pack);
    }

    public WindowEditOrCreateCreativeTab(CreativeTab editingTab, BaseContentPack pack)
    {
        super("Edit Creative Tab", 180, 128, editingTab, pack);
    }

    @Override
    protected void initControls()
    {
        row("Name:", "The internal name. Has to be unique.");
        tbName = row(textBox());

        row("Label:", "This is the actual text displayed.");
        tbLabel = row(textBox());

        row("Icon:");
        display = row(itemDisplay());
        display.setDrawSlotBackground();
        display.useSelectItemDialog(false);
        display.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);

        if (editingContent != null)
        {
            tbName.setText(editingContent.name);
            tbName.setEnabled(false);
            tbLabel.setText(editingContent.label);
            display.setItemStack(editingContent.icon);
        }
    }

    @Override
    protected CreativeTab createContent()
    {
        String name = tbName.getText();
        String label = tbLabel.getText();
        ItemStack icon = display.getItemStack();
        return new CreativeTab(name, label, icon, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.label = tbLabel.getText();
        editingContent.icon = display.getItemStack();
    }
}
