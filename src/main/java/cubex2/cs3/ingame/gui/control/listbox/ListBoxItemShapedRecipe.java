package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Textures;

public class ListBoxItemShapedRecipe extends ListBoxItem<ShapedRecipe>
{

    public ListBoxItemShapedRecipe(ShapedRecipe value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        RecipeInputDisplay[] inputDisplays = new RecipeInputDisplay[9];
        for (int i = 0; i < 9; i++)
        {
            int row = i / 3;
            int col = i % 3;

            inputDisplays[i] = recipeInputDisplay().left(4 + col * 18).top(4 + row * 18).add().setDrawSlotBackground();
        }

        for (int i = 0; i < value.input.length; i++)
        {
            int row = i / value.width;
            int col = i % value.width;

            inputDisplays[col + row * 3].setRecipeInput(value.input[i]);
        }

        itemDisplay(value.result).left(92).top(22).add().setDrawSlotBackground();

        pictureBox(Textures.CONTROLS, 218, 18).left(63).top(22).size(22, 15).add();
    }
}


