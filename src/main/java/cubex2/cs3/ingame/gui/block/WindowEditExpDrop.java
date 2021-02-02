package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.NumericUpDown;

public class WindowEditExpDrop extends WindowEditBlockAttribute
{
    private NumericUpDown nupMin;
    private NumericUpDown nupMax;

    public WindowEditExpDrop(WrappedBlock block)
    {
        super(block, "expDrop", 150, 105);

        Label lblMin = label("Min").at(7, 7).add();

        nupMin = numericUpDown().below(lblMin).fillWidth(7).add();
        nupMin.setValue(container.expDropMin);

        Label lblMax = label("Max").below(nupMin).add();

        nupMax = numericUpDown().below(lblMax).fillWidth(7).add();
        nupMax.setValue(container.expDropMax);
    }

    @Override
    protected void applyChanges()
    {
        container.expDropMin = nupMin.getValue();
        container.expDropMax = nupMax.getValue();

        if (container.expDropMin > container.expDropMax)
        {
            container.expDropMin = container.expDropMax;
        }
    }
}
