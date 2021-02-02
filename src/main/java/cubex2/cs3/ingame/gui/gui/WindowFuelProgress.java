package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.FuelProgressData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.FuelProgressBar;

public class WindowFuelProgress extends WindowProgress<FuelProgressBar, FuelProgressData>
{
    public WindowFuelProgress(WrappedGui gui, Window window, int x, int y, int width, int height)
    {
        super("Fuel", gui, window, x, y, width, height);
    }

    public WindowFuelProgress(WrappedGui gui, Window window, FuelProgressBar control, FuelProgressData data)
    {
        super("Fuel", gui, window, control, data);
    }

    @Override
    protected FuelProgressData createData()
    {
        return new FuelProgressData(nupX.getValue(), nupY.getValue(), nupWidth.getValue(), nupHeight.getValue(),
                tbName.getText(), tbTexture.getLocation(), nupU.getValue(), nupV.getValue(), dbDirection.getSelectedValue());
    }
}
