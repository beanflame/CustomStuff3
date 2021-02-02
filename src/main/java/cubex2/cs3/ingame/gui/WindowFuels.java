package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

import java.util.Comparator;

public class WindowFuels extends WindowContentList<Fuel>
{
    public WindowFuels(BaseContentPack pack)
    {
        super(Fuel.class, "Fuels", 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<Fuel> desc)
    {
        desc.rows = 7;
        desc.elementHeight = 22;
        desc.sorted = true;
        desc.comparator = new Comparator<Fuel>()
        {
            @Override
            public int compare(Fuel o1, Fuel o2)
            {
                return o1.fuelList.compareTo(o2.fuelList);
            }
        };
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateFuel(pack);
    }

    @Override
    protected Window createEditContentWindow(Fuel content)
    {
        return new WindowEditOrCreateFuel(content, pack);
    }
}
