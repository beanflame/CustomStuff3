package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import net.minecraft.creativetab.CreativeTabs;

public class WindowEditCreativeTab extends WindowEditItemAttribute implements IStringProvider<CreativeTabs>
{
    private DropBox<CreativeTabs> dbTabs;

    public WindowEditCreativeTab(WrappedItem item)
    {
        super(item, "creativeTab", EDIT | CANCEL, 150, 55);

        dbTabs = dropBox(CreativeTabs.creativeTabArray).top(7).fillWidth(7).add();
        dbTabs.setStringProvider(this);
        dbTabs.setSelectedValue(container.creativeTab);
    }

    @Override
    protected void applyChanges()
    {
        container.creativeTab = dbTabs.getSelectedValue();
    }

    @Override
    public String getStringFor(CreativeTabs value)
    {
        return value.getTabLabel();
    }
}
