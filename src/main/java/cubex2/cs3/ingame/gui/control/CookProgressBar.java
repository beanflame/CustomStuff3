package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.tileentity.TileEntityInventory;
import net.minecraft.util.ResourceLocation;

public class CookProgressBar extends ImageProgressBar
{
    private final String name;
    private final TileEntityInventory tile;

    public CookProgressBar(String name, TileEntityInventory tile, ResourceLocation texture, int u, int v, int direction, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(texture, u, v, direction, width, height, anchor, offsetX, offsetY, parent);
        this.name = name;
        this.tile = tile;
    }

    @Override
    public int getProgress()
    {
        if (tile == null)
            return GuiBase.tickCounter % (getMaxProgress() +1);
        return tile.getCookProgressScaled(name, getMaxProgress());
    }
}
