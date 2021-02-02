package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;
import net.minecraft.world.biome.BiomeGenBase;

public class ListBoxItemBiome extends ListBoxItem<BiomeGenBase>
{
    private Label label;

    public ListBoxItemBiome(BiomeGenBase value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
        label = label(value.biomeName).left(3).centerVert(1).add();
    }

    @Override
    public void selectionChanged()
    {
        label.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
