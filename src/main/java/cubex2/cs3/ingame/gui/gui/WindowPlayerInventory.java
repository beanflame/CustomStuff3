package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.gui.data.PlayerInventoryData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.PlayerInventoryArea;

public class WindowPlayerInventory extends WindowEditOrCreateControl<PlayerInventoryArea, PlayerInventoryData>
{
    public WindowPlayerInventory(WrappedGui gui, Window window, PlayerInventoryArea control, PlayerInventoryData data)
    {
        super("Edit Player Inventory", gui, window, control, data);
    }

    public WindowPlayerInventory(WrappedGui gui, Window window, int x, int y)
    {
        super("Create Player Inventory", gui, window, x, y, -1, -1);
    }

    @Override
    protected PlayerInventoryData createData()
    {
        return new PlayerInventoryData(nupX.getValue(), nupY.getValue());
    }

    @Override
    protected void edit()
    {

    }
}
