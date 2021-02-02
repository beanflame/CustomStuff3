package cubex2.cs3.ingame.gui.item;

import com.google.common.base.Predicate;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.item.attributes.BucketAttributes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidBase;


public class WindowEditFluid extends WindowEditItemAttribute
{
    private ItemDisplay itemDisplay;

    public WindowEditFluid(WrappedItem item)
    {
        super(item, "fluid", 150, 100);

        itemDisplay = itemDisplay().top(8).centerHor().add();
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setClearOnRightClick();
        itemDisplay.setItemStack(new ItemStack(((BucketAttributes) container).fluid));
        itemDisplay.useSelectBlockDialog(IS_FLUID);
    }

    @Override
    protected void applyChanges()
    {
        ((BucketAttributes) container).fluid = Block.getBlockFromItem(itemDisplay.getItemStack().getItem());
    }

    private static final Predicate<ItemStack> IS_FLUID = new Predicate<ItemStack>()
    {
        @Override
        public boolean apply(ItemStack input)
        {
            return Block.getBlockFromItem(input.getItem()) instanceof BlockFluidBase;
        }
    };
}
