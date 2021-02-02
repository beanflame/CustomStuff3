package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.ChestItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.Validators;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class WindowEditOrCreateChestItem extends WindowEditOrCreate<ChestItem> implements IWindowClosedListener<WindowSelectItem>
{
    private DropBox<String> dbChest;
    private ItemDisplay itemDisplay;
    private NumericUpDown nupMinCount;
    private NumericUpDown nupMaxCount;
    private NumericUpDown nupRarity;

    public WindowEditOrCreateChestItem(BaseContentPack pack)
    {
        super("New Chest Item", 180, 205, pack);
    }

    public WindowEditOrCreateChestItem(ChestItem item, BaseContentPack pack)
    {
        super("Edit Chest Item", 180, 205, item, pack);
    }

    @Override
    protected void initControls()
    {
        row("Chest:");
        dbChest = row(dropBox(GeneralHelper.getChestGenNames()));
        dbChest.setSelectedValue(ChestGenHooks.BONUS_CHEST);

        row("Item:");
        itemDisplay = row(itemDisplay());
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);

        row("Min Count:");
        nupMinCount = row(numericUpDown());
        nupMinCount.setMinValue(1);

        row("Max Count:");
        nupMaxCount = row(numericUpDown());
        nupMaxCount.setMinValue(1);

        row("Rarity:", Strings.INFO_CHEST_ITEM_RARITY);
        nupRarity = row(numericUpDown());
        nupRarity.setValue(1);

        if (editingContent != null)
        {
            dbChest.setSelectedValue(editingContent.chest);
            itemDisplay.setItemStack(editingContent.stack);
            nupMinCount.setValue(editingContent.minCount);
            nupMaxCount.setValue(editingContent.maxCount);
            nupRarity.setValue(editingContent.rarity);
        }
    }

    @Override
    protected ChestItem createContent()
    {
        String chest = dbChest.getSelectedValue();
        ItemStack stack = itemDisplay.getItemStack();
        int minCount = nupMinCount.getValue();
        int maxCount = nupMaxCount.getValue();
        if (maxCount < minCount)
            maxCount = minCount;
        int rarity = nupRarity.getValue();
        return new ChestItem(stack, chest, minCount, maxCount, rarity, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.chest = dbChest.getSelectedValue();
        editingContent.stack = itemDisplay.getItemStack();
        editingContent.minCount = nupMinCount.getValue();
        editingContent.maxCount = nupMaxCount.getValue();
        if (editingContent.maxCount < editingContent.minCount)
            editingContent.maxCount = editingContent.minCount;
        editingContent.rarity = nupRarity.getValue();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == itemDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem());
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());

        btnCreate.setEnabled(itemDisplay.getItemStack() != null);
    }
}
