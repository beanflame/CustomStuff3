package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowEditHarvesting extends WindowEditBlockAttribute
{
    private TextBox tbToolClass;
    private NumericUpDown nupHarvestLevel;
    private CheckBox cbSilkHarvest;

    public WindowEditHarvesting(WrappedBlock block)
    {
        super(block, "harvesting", 150, 120);

        Label lblToolClass = label("Tool class:").at(7, 7).add();
        infoButton(Strings.INFO_TOOL_CLASS_BLOCK).rightTo(lblToolClass).add();

        tbToolClass = textBox().top(lblToolClass, 2).fillWidth(7).add();
        if (container.toolClass != null)
            tbToolClass.setText(container.toolClass);

        Label lblHarvestLevel = label("Harvest level:").below(tbToolClass).add();
        infoButton(Strings.INFO_HARVEST_LEVEL_BLOCK).rightTo(lblHarvestLevel).add();

        nupHarvestLevel = numericUpDown().top(lblHarvestLevel,2).fillWidth(7).add();
        nupHarvestLevel.setValue(container.harvestLevel);

        cbSilkHarvest = checkBox("Allow silk harvest.", container.canSilkHarvest).top(nupHarvestLevel, 6).left(7).add();
    }

    @Override
    protected void applyChanges()
    {
        String toolClass = tbToolClass.getText().length() != 0 ? tbToolClass.getText() : null;
        container.toolClass = toolClass;
        container.harvestLevel = toolClass != null ? nupHarvestLevel.getValue() : 0;
        container.canSilkHarvest = cbSilkHarvest.getIsChecked();

        wrappedBlock.block.setHarvestLevel(container.toolClass, container.harvestLevel);
    }
}
