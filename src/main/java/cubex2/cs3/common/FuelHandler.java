package cubex2.cs3.common;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class FuelHandler
{
    private final BaseContentPack pack;

    public FuelHandler(BaseContentPack pack)
    {
        this.pack = pack;
    }

    public boolean isItemFuel(ItemStack stack, String fuelList)
    {
        if (fuelList.equals("vanilla"))
            return TileEntityFurnace.isItemFuel(stack);
        for (Fuel fuel : pack.getContentRegistry(Fuel.class).getContentList())
        {
            if (fuel.isRepresentingStack(stack, fuelList))
                return true;
        }
        return false;
    }

    public int getItemBurnTime(ItemStack stack, String fuelList)
    {
        if (fuelList.equals("vanilla"))
            return TileEntityFurnace.getItemBurnTime(stack);
        for (Fuel fuel : pack.getContentRegistry(Fuel.class).getContentList())
        {
            if (fuel.isRepresentingStack(stack, fuelList))
                return fuel.duration;
        }
        return 0;
    }
}
