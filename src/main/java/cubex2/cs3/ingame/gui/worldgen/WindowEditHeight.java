package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.NumericUpDown;

public class WindowEditHeight extends WindowEditWorldGenAttribute
{
    private NumericUpDown nupMin;
    private NumericUpDown nupMax;

    public WindowEditHeight(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen, "height", 150, 105);

        Label lblMin = label("Min").at(7, 7).add();

        nupMin = numericUpDown().below(lblMin).fillWidth(7).add();
        nupMin.setValue(container.minHeight);

        Label lblMax = label("Max").below(nupMin).add();

        nupMax = numericUpDown().below(lblMax).fillWidth(7).add();
        nupMax.setMaxValue(255);
        nupMax.setValue(container.maxHeight);
    }

    @Override
    protected void applyChanges()
    {
        container.minHeight = nupMin.getValue();
        container.maxHeight = nupMax.getValue();

        if (container.minHeight > container.maxHeight)
        {
            container.minHeight = container.maxHeight;
        }
    }
}
