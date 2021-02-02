package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.ItemSelectionArray;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ItemSelectionArrayBuilder extends ControlBuilder<ItemSelectionArray>
{
    private final ArrayList<ItemStack> stacks;

    public ItemSelectionArrayBuilder(ArrayList<ItemStack> stacks, ControlContainer c)
    {
        super(c);
        this.stacks = stacks;
    }

    @Override
    protected ItemSelectionArray newInstance()
    {
        return new ItemSelectionArray(stacks, width, height, anchor, offsetX, offsetY, container);
    }
}
