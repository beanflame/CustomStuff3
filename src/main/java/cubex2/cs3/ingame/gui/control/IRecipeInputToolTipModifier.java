package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.util.RecipeInput;

import java.util.List;

public interface IRecipeInputToolTipModifier
{
    void modifyToolTip(List<String> toolTipList, RecipeInput input);
}
