package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiContainerAttributes;
import cubex2.cs3.gui.data.ShiftClickRule;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.registry.FuelRegistry;
import cubex2.cs3.registry.SmeltingRecipeRegistry;

public class WindowEditOrCreateShiftClickRule extends Window
{
    private ShiftClickRule editingRule;
    private WrappedGui gui;

    private CheckBox cbFromInv;
    private CheckBox cbToInv;
    private NumericUpDown nbFromStart;
    private NumericUpDown nbFromEnd;
    private NumericUpDown nbToStart;
    private NumericUpDown nbToEnd;
    private CheckBox cbFuelOnly;
    private CheckBox cbInputOnly;
    private DropBox<String> dbRecipes;
    private DropBox<String> dbFuels;

    public WindowEditOrCreateShiftClickRule(WrappedGui gui, ShiftClickRule rule)
    {
        super("Edit Shift-Click Rule", EDIT | CANCEL, 150, 230);
        this.gui = gui;
        editingRule = rule;
        initControls();
    }

    public WindowEditOrCreateShiftClickRule(WrappedGui gui)
    {
        super("New Shift-Click Rule", CREATE | CANCEL, 150, 230);
        this.gui = gui;
        initControls();
    }

    private void initControls()
    {
        String[] recipeLists = ((SmeltingRecipeRegistry) gui.getPack().getContentRegistry(SmeltingRecipe.class)).getRecipeLists();
        String[] fuelLists = ((FuelRegistry) gui.getPack().getContentRegistry(Fuel.class)).getFuelLists();

        cbFromInv = row(checkBox("From Player Inventory"));

        row("From Start:");
        nbFromStart = numericUpDown().below(lastControl).width(150 / 2 - 7 - 3).add();
        nbFromEnd = numericUpDown().rightTo(nbFromStart, 3).right(7).add();
        label("From End:").left(nbFromEnd, 0, true).bottom(nbFromEnd, 3).add();


        cbToInv = checkBox("To Player Inventory").below(nbFromStart, 7).add();

        label("To Start:").below(cbToInv).add();
        nbToStart = numericUpDown().below(lastControl).width(150 / 2 - 7 - 3).add();
        nbToEnd = numericUpDown().rightTo(nbToStart, 3).right(7).add();
        label("To End:").left(nbToEnd, 0, true).bottom(nbToEnd, 3).add();


        cbFuelOnly = checkBox("Fuel Only").below(nbToStart, 7).add();
        label("Fuel List:").below(cbFuelOnly).add();
        dbFuels = dropBox(fuelLists).below(lastControl).right(7).add();
        dbFuels.setSelectedValue("vanilla");
        cbInputOnly = checkBox("Furnace Input Only").below(dbFuels, 7).add();
        label("Recipe List:").below(cbInputOnly).add();
        dbRecipes = dropBox(recipeLists).below(lastControl).right(7).add();
        dbRecipes.setSelectedValue("vanilla");

        if (editingRule != null)
        {
            cbFromInv.setIsChecked(editingRule.fromInv);
            nbFromStart.setValue(editingRule.fromStart);
            nbFromEnd.setValue(editingRule.fromEnd);
            cbToInv.setIsChecked(editingRule.toInv);
            nbToStart.setValue(editingRule.toStart);
            nbToEnd.setValue(editingRule.toEnd);
            cbFuelOnly.setIsChecked(editingRule.fuelOnly);
            cbInputOnly.setIsChecked(editingRule.furnaceInputOnly);
            dbFuels.setSelectedValue(editingRule.fuelList);
            dbRecipes.setSelectedValue(editingRule.recipeList);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnEdit)
        {
            editingRule.fromInv = cbFromInv.getIsChecked();
            editingRule.fromStart = nbFromStart.getValue();
            editingRule.fromEnd = nbFromEnd.getValue();
            editingRule.toInv = cbToInv.getIsChecked();
            editingRule.toStart = nbToStart.getValue();
            editingRule.toEnd = nbToEnd.getValue();
            editingRule.fuelOnly = cbFuelOnly.getIsChecked();
            editingRule.furnaceInputOnly = cbInputOnly.getIsChecked();
            editingRule.recipeList = dbRecipes.getSelectedValue();
            editingRule.fuelList = dbFuels.getSelectedValue();

            gui.getPack().save();
            GuiBase.openPrevWindow();
        } else if (c == btnCreate)
        {
            ShiftClickRule rule = new ShiftClickRule(cbFromInv.getIsChecked(), nbFromStart.getValue(), nbFromEnd.getValue(),
                    cbToInv.getIsChecked(), nbToStart.getValue(), nbToEnd.getValue(), cbFuelOnly.getIsChecked(), cbInputOnly.getIsChecked(),
                                                     dbFuels.getSelectedValue(), dbRecipes.getSelectedValue());
            ((GuiContainerAttributes) gui.container).shiftClickRules.list.add(rule);

            gui.getPack().save();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
