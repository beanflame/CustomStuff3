package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.TextBoxValidators;
import cubex2.cs3.util.ToolClass;

public class WindowCreateToolClass extends Window implements IValidityProvider
{
    private TextBox tbToolClass;
    private NumericUpDown nupHarvestLevel;
    private final boolean harvestAndAll;

    private ToolClass createdClass = null;

    public WindowCreateToolClass(boolean harvestAndAll)
    {
        super("Add tool class", CREATE | CANCEL, 150, 120);
        this.harvestAndAll = harvestAndAll;

        Label lblToolClass = label("Tool class:").at(7, 7).add();
        infoButton(Strings.INFO_TOOL_CLASS_ITEM).rightTo(lblToolClass).add();

        tbToolClass = textBox().top(lblToolClass, 2).fillWidth(7).add();
        tbToolClass.setValidityProvider(this);

        Label lblHarvestLevel = label("Harvest level:").below(tbToolClass).add();
        infoButton(Strings.INFO_HARVEST_LEVEL_ITEM).rightTo(lblHarvestLevel).add();

        nupHarvestLevel = numericUpDown().top(lblHarvestLevel, 2).fillWidth(7).add();
        nupHarvestLevel.setValue(0);
    }

    public ToolClass getCreatedClass()
    {
        return createdClass;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCancel)
        {
            GuiBase.openPrevWindow();
        } else if (c == btnCreate)
        {
            createdClass = new ToolClass(tbToolClass.getText(), nupHarvestLevel.getValue());
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        if (!harvestAndAll && (tb.getText().equals("all") || tb.getText().equals("noHarvest")))
        {
            message = "all / noHarvest have to be the only tool class.";
        }

        return message;
    }
}
