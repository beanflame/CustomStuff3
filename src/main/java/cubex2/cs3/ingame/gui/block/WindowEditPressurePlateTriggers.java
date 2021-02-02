package cubex2.cs3.ingame.gui.block;

import com.google.common.collect.Lists;
import cubex2.cs3.block.attributes.PressurePlateAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.ISelectElementCallback;
import cubex2.cs3.ingame.gui.WindowSelectString;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import net.minecraft.entity.EntityList;

import java.util.List;

public class WindowEditPressurePlateTriggers extends WindowEditBlockAttribute implements IListBoxItemClickListener<String>, ISelectElementCallback<String>
{
    private Button btnAddInclude;
    private Button btnRemoveInclude;
    private ListBox<String> lbIncluded;

    private Button btnAddExclude;
    private Button btnRemoveExclude;
    private ListBox<String> lbExcluded;

    private String currentSelection;

    PressurePlateAttributes attributes;

    private List<String> included;
    private List<String> excluded;

    public WindowEditPressurePlateTriggers(WrappedBlock block)
    {
        super(block, "trigger", 150 + 7 + 136, 150);
        attributes = (PressurePlateAttributes) block.container;
        included = Lists.newArrayList(attributes.include);
        excluded = Lists.newArrayList(attributes.exclude);

        Label label = label("Included").at(7, 7).add();

        ListBoxDescription<String> desc = new ListBoxDescription<String>();
        desc.elements = included;
        desc.columns = 1;
        desc.sorted = true;
        desc.elementHeight = 18;
        desc.rows = 4;
        lbIncluded = listBox(desc).top(label, 7).left(7).width(136).add();

        btnAddInclude = button("Add").left(7).top(lbIncluded, 3).add();
        btnRemoveInclude = button("Remove").right(lbIncluded, 0, true).top(lbIncluded, 3).add();
        btnRemoveInclude.setEnabled(false);

        label = label("Excluded").at(7 + 139, 7).add();

        desc = new ListBoxDescription<String>();
        desc.elements = excluded;
        desc.columns = 1;
        desc.sorted = true;
        desc.elementHeight = 18;
        desc.rows = 4;
        lbExcluded = listBox(desc).top(label, 7).left(lbIncluded, 7).width(136).add();

        btnAddExclude = button("Add").left(lbExcluded, 0, true).top(lbExcluded, 3).add();
        btnRemoveExclude = button("Remove").right(7).top(lbExcluded, 3).add();
        btnRemoveExclude.setEnabled(false);
    }

    @Override
    protected void applyChanges()
    {
        attributes.include = included.toArray(new String[included.size()]);
        attributes.exclude = excluded.toArray(new String[excluded.size()]);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnAddInclude)
        {
            currentSelection = "include";
            WindowSelectString window = new WindowSelectString("Select Trigger", getIncludeTriggers());
            window.setCallback(this);
            GuiBase.openWindow(window);
        } else if (c == btnAddExclude)
        {
            currentSelection = "exclude";
            WindowSelectString window = new WindowSelectString("Select Trigger", getExcludeTriggers());
            window.setCallback(this);
            GuiBase.openWindow(window);
        } else if (c == btnRemoveInclude)
        {
            included.remove(lbIncluded.getSelectedItem());
            lbIncluded.updateElements(included);
        } else if (c == btnRemoveExclude)
        {
            excluded.remove(lbExcluded.getSelectedItem());
            lbExcluded.updateElements(excluded);
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    private List<String> getIncludeTriggers()
    {
        List<String> ret = getValidTriggers();
        ret.add("all");
        for (String s : included)
        {
            ret.remove(s);
        }
        return ret;
    }

    private List<String> getExcludeTriggers()
    {
        List<String> ret = getValidTriggers();
        for (String s : excluded)
        {
            ret.remove(s);
        }
        return ret;
    }

    private List<String> getValidTriggers()
    {
        List<String> ret = Lists.newArrayList();
        ret.add("mobs");
        ret.add("players");
        ret.add("hostiles");
        ret.add("animals");
        ret.add("items");

        for (Object o : EntityList.stringToClassMapping.keySet())
        {
            ret.add((String) o);
        }

        return ret;
    }

    @Override
    public void itemClicked(String item, ListBox<String> listBox, int button)
    {
        btnRemoveInclude.setEnabled(lbIncluded.getSelectedIndex() >= 0);
        btnRemoveExclude.setEnabled(lbExcluded.getSelectedIndex() >= 0);
    }

    @Override
    public void itemSelected(String element)
    {
        if (currentSelection.equals("include"))
        {
            included.add(element);
            lbIncluded.updateElements(included);
        } else
        {
            excluded.add(element);
            lbExcluded.updateElements(excluded);
        }
    }
}
