package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.TradeRecipe;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.lib.Textures;

public class ListBoxItemTradeRecipe extends ListBoxItem<TradeRecipe>
{
    public ListBoxItemTradeRecipe(TradeRecipe value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        itemDisplay(value.input1).left(3).centerVert().add();
        itemDisplay(value.input2).left(29).centerVert().add();
        itemDisplay(value.result).right(3).centerVert().add();
        pictureBox(Textures.CONTROLS, 218, 18).left(60).centerVert().size(22, 15).add();
    }
}
