package cubex2.cs3.ingame.gui.worldgen;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.ISelectElementCallback;
import cubex2.cs3.ingame.gui.WindowSelectString;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.lib.Biomes;

import java.util.List;

public class WindowEditAllowedBiomes extends WindowEditWorldGenAttribute implements IListBoxItemClickListener<String>, ISelectElementCallback<String>
{
    private ListBox<String> listBox;
    private List<String> biomes;

    public WindowEditAllowedBiomes(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen, "allowedBiomes", CREATE | DELETE | BACK, 256, 150);

        btnCreate.setText("Add");
        btnDelete.setText("Remove");
        btnDelete.setEnabled(false);

        biomes = Lists.newArrayList(container.allowedBiomes);

        ListBoxDescription<String> desc = new ListBoxDescription<String>();
        desc.elements = biomes;
        desc.columns = 1;
        desc.sorted = true;
        desc.elementHeight = 18;
        desc.rows = 4;
        listBox = listBox(desc).at(7, 7).right(7).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            WindowSelectString window = new WindowSelectString("Select Biome", getBiomes());
            window.setCallback(this);
            GuiBase.openWindow(window);
        } else if (c == btnDelete)
        {
            biomes.remove(listBox.getSelectedItem());
            listBox.updateElements(biomes);
        } else if (c == btnBack)
        {
            handleEditButtonClicked();
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    protected void applyChanges()
    {
        container.allowedBiomes = biomes.toArray(new String[biomes.size()]);
    }

    private List<String> getBiomes()
    {
        List<String> ret = Lists.newArrayList();
        ret.add("all");
        for (String biome : Biomes.getBiomeNames())
        {
            ret.add(biome);
        }
        for (String s : biomes)
        {
            ret.remove(s);
        }
        return ret;
    }

    @Override
    public void itemClicked(String item, ListBox<String> listBox, int button)
    {
        btnDelete.setEnabled(listBox.getSelectedItem() != null);
    }

    @Override
    public void itemSelected(String element)
    {
        biomes.add(element);
        listBox.updateElements(biomes);
    }
}
