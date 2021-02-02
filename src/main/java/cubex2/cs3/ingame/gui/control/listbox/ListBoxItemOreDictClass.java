package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.RecipeInputDisplay;
import cubex2.cs3.util.OreDictionaryClass;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.RecipeInput;

// TODO: OreDictClass display
public class ListBoxItemOreDictClass extends ListBoxItem<OreDictionaryClass>
{
    public ListBoxItemOreDictClass(OreDictionaryClass value, int idx,int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        recipeInputDisplay(new RecipeInput(value.oreClass)).left(3).centerVert().add();
    }
}
