package cubex2.cs3.gui.data;


import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.tileentity.TileEntityInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class FuelProgressData extends ProgressData
{
    public FuelProgressData()
    {
    }

    public FuelProgressData(int x, int y, int width, int height, String name, ResourceLocation texture, int u, int v, int direction)
    {
        super(x, y, width, height, name, texture, u, v, direction);
    }

    @Override
    public Control addToWindow(Window window, IInventory slotInv)
    {
        TileEntityInventory tile = null;
        if (slotInv != null && slotInv instanceof TileEntityInventory)
        {
            tile = ((TileEntityInventory) slotInv);
        }
        return window.fuelProgressBar(name, tile, texture, u, v, direction).at(0, 0).offset(x, y).size(width, height).add();
    }

    @Override
    protected String getControlType()
    {
        return "fuelProgress";
    }
}
