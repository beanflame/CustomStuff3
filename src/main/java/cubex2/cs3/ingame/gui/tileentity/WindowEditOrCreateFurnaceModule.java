package cubex2.cs3.ingame.gui.tileentity;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.registry.FuelRegistry;
import cubex2.cs3.registry.SmeltingRecipeRegistry;
import cubex2.cs3.tileentity.attributes.TileEntityInventoryAttributes;
import cubex2.cs3.tileentity.data.FurnaceModule;

public class WindowEditOrCreateFurnaceModule extends Window
{
    private FurnaceModule editingModule;
    private WrappedTileEntity tile;

    private TextBox tbName;
    private DropBox<String> dbRecipes;
    private DropBox<String> dbFuels;
    private NumericUpDown nupInput;
    private NumericUpDown nupOutput;
    private NumericUpDown nupFuel;
    private NumericUpDown nupCookTime;

    public WindowEditOrCreateFurnaceModule(WrappedTileEntity tile, FurnaceModule module)
    {
        super("Edit Furnace Module", EDIT | CANCEL, 150, 220);
        this.tile = tile;
        editingModule = module;
        initControls();
    }

    public WindowEditOrCreateFurnaceModule(WrappedTileEntity tile)
    {
        super("New Furnace Module", CREATE | CANCEL, 150, 220);
        this.tile = tile;
        initControls();
    }

    private void initControls()
    {
        String[] recipeLists = ((SmeltingRecipeRegistry) tile.getPack().getContentRegistry(SmeltingRecipe.class)).getRecipeLists();
        String[] fuelLists = ((FuelRegistry) tile.getPack().getContentRegistry(Fuel.class)).getFuelLists();

        row("Name:");
        tbName = row(textBox());
        row("Recipe List:");
        dbRecipes = row(dropBox(recipeLists));
        dbRecipes.setSelectedValue("vanilla");
        row("Fuel List:");
        dbFuels = row(dropBox(fuelLists));
        dbFuels.setSelectedValue("vanilla");

        row("Input Slot:");
        nupInput = numericUpDown().below(lastControl).width(150 / 2 - 7 - 3).add();
        nupOutput = numericUpDown().rightTo(nupInput, 3).right(7).add();
        label("Output Slot:").left(nupOutput, 0, true).bottom(nupOutput, 3).add();

        label("Fuel Slot:").below(nupInput).add();
        nupFuel = numericUpDown().below(lastControl).width(150 / 2 - 7 - 3).add();
        nupCookTime = numericUpDown().rightTo(nupFuel, 3).right(7).add();
        label("Cook Time:").left(nupCookTime, 0, true).bottom(nupCookTime, 3).add();


        tbName.setValidityProvider(new NameValidator(tile, editingModule != null ? editingModule.name : null));
        nupCookTime.setValue(200);

        if (editingModule != null)
        {
            tbName.setText(editingModule.name);
            dbRecipes.setSelectedValue(editingModule.recipeList);
            dbFuels.setSelectedValue(editingModule.fuelList);
            nupInput.setValue(editingModule.inputSlot);
            nupOutput.setValue(editingModule.outputSlot);
            nupFuel.setValue(editingModule.fuelSlot);
            nupCookTime.setValue(editingModule.cookTime);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnEdit)
        {
            editingModule.name = tbName.getText();
            editingModule.recipeList = dbRecipes.getSelectedValue();
            editingModule.fuelList = dbFuels.getSelectedValue();
            editingModule.inputSlot = nupInput.getValue();
            editingModule.outputSlot = nupOutput.getValue();
            editingModule.fuelSlot = nupFuel.getValue();
            editingModule.cookTime = nupCookTime.getValue();

            tile.getPack().save();
            GuiBase.openPrevWindow();
        } else if (c == btnCreate)
        {
            FurnaceModule module = new FurnaceModule(tbName.getText(),  dbRecipes.getSelectedValue(), dbFuels.getSelectedValue(), nupInput.getValue(), nupOutput.getValue(), nupFuel.getValue(), nupCookTime.getValue());
            ((TileEntityInventoryAttributes) tile.container).furnaceModules.list.add(module);

            tile.getPack().save();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    public static class NameValidator implements IValidityProvider
    {
        private TileEntityInventoryAttributes container;
        private String current;

        public NameValidator(WrappedTileEntity tile, String current)
        {
            container = (TileEntityInventoryAttributes) tile.container;
            this.current = current;
        }

        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            String text = tb.getText().trim();
            if (text.length() == 0)
                message = "Enter something";
            else if (current == null || !text.equals(current))
            {
                for (FurnaceModule module : container.furnaceModules.list)
                {
                    if (module.name.equals(text))
                    {
                        message = "That name is already used.";
                        break;
                    }
                }
            }

            return message;
        }
    }
}
