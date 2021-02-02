package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.util.BlockDrop;

public class WindowEditDropData extends WindowEditBlockAttribute
{
    private BlockDrop.DropData drop;

    private NumericUpDown nupMin;
    private NumericUpDown nupMax;

    public WindowEditDropData(WrappedBlock block, BlockDrop.DropData drop)
    {
        super(block, "Edit drop", 150, 105);
        this.drop = drop;

        Label lblMin = label("Min").at(7, 7).add();

        nupMin = numericUpDown().below(lblMin).fillWidth(7).add();
        nupMin.setValue(drop.getMinCount());

        Label lblMax = label("Max").below(nupMin).add();

        nupMax = numericUpDown().below(lblMax).fillWidth(7).add();
        nupMax.setValue(drop.getMaxCount());
    }

    @Override
    protected void applyChanges()
    {
        int min = nupMin.getValue();
        int max = nupMax.getValue();

        if (min > max)
            min = max;

        drop.setAmount(min, max);
    }
}
