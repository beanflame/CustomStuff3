package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.CookProgressBar;
import cubex2.cs3.ingame.gui.control.FuelProgressBar;
import cubex2.cs3.tileentity.TileEntityInventory;
import net.minecraft.util.ResourceLocation;

public class FuelProgressBarBuilder extends ImageProgressBarBuilder<FuelProgressBar>
{
    private final TileEntityInventory tile;
    private final String name;

    public FuelProgressBarBuilder(String name, TileEntityInventory tile, ResourceLocation texture, int u, int v, int direction, ControlContainer c)
    {
        super(texture, u, v, direction, c);
        this.name = name;
        this.tile = tile;
    }

    @Override
    protected FuelProgressBar newInstance()
    {
        return new FuelProgressBar(name, tile, texture, u, v, direction, width, height, anchor, offsetX, offsetY, container);
    }
}
