package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.SlotData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.InventorySlot;
import cubex2.cs3.registry.SmeltingRecipeRegistry;

public class WindowInventorySlot extends WindowEditOrCreateControl<InventorySlot, SlotData>
{
    private CheckBox cbFurnaceOutput;
    private DropBox<String> dbRecipes;

    public WindowInventorySlot(WrappedGui gui, Window window, InventorySlot control, SlotData data)
    {
        super("Edit Slot", gui, window, control, data);
    }

    public WindowInventorySlot(WrappedGui gui, Window window, int x, int y)
    {
        super("Create Slot", gui, window, x, y, -1, -1);
    }

    @Override
    protected void initControls(boolean hasSize)
    {
        super.initControls(hasSize);

        String[] recipeLists = ((SmeltingRecipeRegistry) pack.getContentRegistry(SmeltingRecipe.class)).getRecipeLists();

        cbFurnaceOutput = row(checkBox("Furnace Output", data != null && data.furnaceOutput));
        row("Recipe List:");

        dbRecipes = row(dropBox(recipeLists));
        dbRecipes.setSelectedValue(data != null ? data.recipeList : "vanilla");
    }

    @Override
    protected SlotData createData()
    {
        return new SlotData(nupX.getValue(), nupY.getValue(), cbFurnaceOutput.getIsChecked(), dbRecipes.getSelectedValue());
    }

    @Override
    protected void edit()
    {
        data.furnaceOutput = cbFurnaceOutput.getIsChecked();
        data.recipeList = dbRecipes.getSelectedValue();
    }
}
