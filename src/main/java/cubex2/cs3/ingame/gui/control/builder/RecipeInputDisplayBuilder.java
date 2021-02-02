package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.RecipeInputDisplay;
import cubex2.cs3.util.RecipeInput;

public class RecipeInputDisplayBuilder extends ControlBuilder<RecipeInputDisplay>
{
    private RecipeInput input;

    public RecipeInputDisplayBuilder(RecipeInput input, ControlContainer c)
    {
        super(c);
        this.input = input;
    }

    @Override
    protected RecipeInputDisplay newInstance()
    {
        RecipeInputDisplay display = new RecipeInputDisplay(anchor, offsetX, offsetY, container);
        if (input != null)
            display.setRecipeInput(input);
        return display;
    }
}
