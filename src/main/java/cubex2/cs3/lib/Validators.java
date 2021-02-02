package cubex2.cs3.lib;

import com.google.common.base.Predicate;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.crafting.FurnaceRecipes;

public class Validators
{
    public static final Predicate<ItemDisplay> ITEM_DISPLAY_NOT_NULL = new Predicate<ItemDisplay>()
    {
        @Override
        public boolean apply(ItemDisplay input)
        {
            return input.getItemStack() != null;
        }
    };

    public static final Predicate<ItemDisplay> ITEM_DISPLAY_SMELTING_INPUT = new Predicate<ItemDisplay>()
    {
        @Override
        public boolean apply(ItemDisplay input)
        {
            return input.getItemStack() != null && FurnaceRecipes.smelting().getSmeltingResult(input.getItemStack()) == null;
        }
    };
}
