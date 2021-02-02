package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.GrassPlant;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.Validators;

public class WindowEditOrCreateGrassPlant extends WindowEditOrCreate<GrassPlant> implements IWindowClosedListener<WindowSelectBlock>
{
    private ItemDisplay blockDisplay;
    private NumericUpDown nupWeight;

    public WindowEditOrCreateGrassPlant(BaseContentPack pack)
    {
        super("New Grass Plant", 180, 103, pack);
    }

    public WindowEditOrCreateGrassPlant(GrassPlant editingPlant, BaseContentPack pack)
    {
        super("Edit Grass Plant", 180, 103, editingPlant, pack);
    }

    @Override
    protected void initControls()
    {
        row("Block:");
        blockDisplay = row(itemDisplay());
        blockDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        if (editingContent == null)
            blockDisplay.useSelectBlockDialog();
        else
            blockDisplay.setItemStack(editingContent.block);
        blockDisplay.setDrawSlotBackground();

        row("Weight:", Strings.INFO_GRASS_PLANT_WEIGHT);
        nupWeight = row(numericUpDown());
        nupWeight.setMinValue(1);
        nupWeight.setValue(editingContent == null ? 1 : editingContent.weight);
    }

    @Override
    protected GrassPlant createContent()
    {
        return new GrassPlant(blockDisplay.getItemStack(), nupWeight.getValue(), pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.weight = nupWeight.getValue();
    }

    @Override
    public void windowClosed(WindowSelectBlock window)
    {
        if (window.getSelectedStack() != null)
        {
            blockDisplay.setItemStack(window.getSelectedStack());
        }
    }
}
