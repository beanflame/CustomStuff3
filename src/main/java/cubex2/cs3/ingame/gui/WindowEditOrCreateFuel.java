package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.TextBoxValidators;
import cubex2.cs3.lib.Validators;
import cubex2.cs3.registry.FuelRegistry;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateFuel extends WindowEditOrCreate<Fuel> implements IWindowClosedListener<Window>
{
    private ItemDisplay itemDisplay;
    private NumericUpDown nupDuration;
    private TextBox tbFuelList;
    private Button btnSelectList;

    public WindowEditOrCreateFuel(BaseContentPack pack)
    {
        super("New Fuel", 180, 135, pack);
    }

    public WindowEditOrCreateFuel(Fuel fuel, BaseContentPack pack)
    {
        super("Edit Fuel", 180, 135, fuel, pack);
    }

    @Override
    protected void initControls()
    {
        row("Item:");
        itemDisplay = row(itemDisplay());
        row("Duration:", Strings.INFO_FUEL_DURATION);
        nupDuration = row(numericUpDown());
        nupDuration.setValue(300);

        itemDisplay.setDrawSlotBackground();
        itemDisplay.useSelectItemDialog(false);
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);

        label("Fuel List:").left(7).top(nupDuration, 8).add();

        if (editingContent != null)
        {
            itemDisplay.setItemStack(editingContent.stack);
            nupDuration.setValue(editingContent.duration);

            tbFuelList = textBox().below(lastControl).fillWidth(7).add();
            tbFuelList.setText(editingContent.fuelList);
        } else
        {
            tbFuelList = textBox().below(lastControl).left(7).right(70).add();
            tbFuelList.setText("vanilla");

            btnSelectList = button("Select").rightTo(tbFuelList).add();
        }

        tbFuelList.setValidityProvider(TextBoxValidators.NOT_EMPTY);
        tbFuelList.setEnabled(editingContent == null);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnSelectList)
        {
            String[] fuelLists = ((FuelRegistry) pack.getContentRegistry(Fuel.class)).getFuelLists();
            GuiBase.openWindow(new WindowSelectString("Select Fuel List", Lists.newArrayList(fuelLists)));
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    protected Fuel createContent()
    {
        ItemStack stack = itemDisplay.getItemStack();
        int duration = nupDuration.getValue();
        return new Fuel(tbFuelList.getText(), stack, duration, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.stack = itemDisplay.getItemStack();
        editingContent.duration = nupDuration.getValue();
    }

    @Override
    public void windowClosed(Window window)
    {
        if (window instanceof WindowSelectString)
        {
            WindowSelectString wdw = (WindowSelectString) window;

            if (wdw.getSelectedElement() != null)
                tbFuelList.setText(wdw.getSelectedElement());
        }
    }
}
