package cubex2.cs3.registry;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowFuels;
import cubex2.cs3.lib.Strings;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Set;

public class FuelRegistry extends ContentRegistry<Fuel> implements IFuelHandler
{
    public FuelRegistry(BaseContentPack pack)
    {
        super(pack);
        GameRegistry.registerFuelHandler(this);
    }

    @Override
    public Fuel newDataInstance()
    {
        return new Fuel(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowFuels(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Fuels";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_FUEL;
    }

    @Override
    public int getBurnTime(ItemStack stack)
    {
        for (Fuel fuel : getContentList())
        {
            if (fuel.isRepresentingStack(stack, "vanilla"))
                return fuel.duration;
        }
        return 0;
    }

    public String[] getFuelLists()
    {
        Set<String> lists = Sets.newHashSet();
        for (Fuel fuel : getContentList())
        {
            lists.add(fuel.fuelList);
        }
        lists.add("vanilla");

        String[] ret = lists.toArray(new String[lists.size()]);
        Arrays.sort(ret);
        return ret;
    }
}
