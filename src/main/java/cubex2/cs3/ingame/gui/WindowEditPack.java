package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.registry.ContentRegistry;

import java.util.List;

public class WindowEditPack extends Window implements IListBoxItemClickListener<ContentRegistry>
{
    private static final List<String> listBoxElements = Lists.newArrayList("Fuels", "Smelting Recipes",
            "Ore Dictionary", "Shaped Recipes", "Shapeless Recipes", "Trade Recipes", "Items", "Blocks", "World Generation");

    private BaseContentPack pack;

    private ListBox<ContentRegistry> listBox;

    public WindowEditPack(BaseContentPack pack)
    {
        super(pack.getName(), BACK, 180, 201);
        this.pack = pack;

        ListBoxDescription<ContentRegistry> desc = new ListBoxDescription<ContentRegistry>(7, 7);
        desc.rows = 12;
        desc.elements = pack.getRegistries();
        desc.canSelect = false;
        desc.sorted = true;
        listBox = listBox(desc).fillWidth(7).top(7).add();
    }

    @Override
    public void itemClicked(ContentRegistry item, ListBox<ContentRegistry> listBox, int button)
    {
        if (button == 0)
        {
            GuiBase.openWindow(item.createListWindow());
        }
    }
}
