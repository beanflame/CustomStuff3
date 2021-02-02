package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class ItemDisplayBuilder extends ControlBuilder<ItemDisplay>
{
    private ItemStack stack;

    public ItemDisplayBuilder(ItemStack stack, ControlContainer c)
    {
        super(c);
        this.stack = stack;
        width = 18;
        height = 18;
    }

    @Override
    protected ItemDisplay newInstance()
    {
        ItemDisplay display = new ItemDisplay(anchor, offsetX, offsetY, container);
        if (stack != null)
            display.setItemStack(stack);
        return display;
    }
}
