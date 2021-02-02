package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.GrassSeed;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.Validators;

public class WindowCreateGrassSeed extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private final BaseContentPack pack;

    private ItemDisplay itemDisplay;
    private NumericUpDown nupWeight;

    public WindowCreateGrassSeed(BaseContentPack pack)
    {
        super("New Grass Seed", CREATE | CANCEL, 180, 103);
        this.pack = pack;

        row("Item:");
        itemDisplay = row(itemDisplay());
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        itemDisplay.useSelectItemDialog(false);
        itemDisplay.setDrawSlotBackground();

        row("Weight:", Strings.INFO_GRASS_SEED_WEIGHT);
        nupWeight = row(numericUpDown());
        nupWeight.setMinValue(1);
        nupWeight.setValue(1);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            GrassSeed seed = new GrassSeed(itemDisplay.getItemStack(), nupWeight.getValue(), pack);
            seed.apply();

            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
        {
            itemDisplay.setItemStack(window.getSelectedStack());
        }
    }
}
