package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.ShapelessRecipe;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.RecipeInputDisplay;
import cubex2.cs3.lib.Textures;

public class ListBoxItemShapelessRecipe extends ListBoxItem<ShapelessRecipe>
{
    public ListBoxItemShapelessRecipe(ShapelessRecipe value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        for (int i = 0; i < 9; i++)
        {
            int row = i / 3;
            int col = i % 3;

            RecipeInputDisplay display = recipeInputDisplay().left(4 + col * 18).top(4 + row * 18).add().setDrawSlotBackground();
            if (i < value.input.length)
                display.setRecipeInput(value.input[i]);
        }

        itemDisplay(value.result).left(92).top(22).add().setDrawSlotBackground();

        pictureBox(Textures.CONTROLS, 218, 18).left(63).top(22).size(22, 15).add();
    }
}
