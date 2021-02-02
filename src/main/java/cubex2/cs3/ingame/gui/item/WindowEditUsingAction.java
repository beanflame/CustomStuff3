package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import net.minecraft.item.EnumAction;

public class WindowEditUsingAction extends WindowEditItemAttribute implements IStringProvider<EnumAction>
{
    private DropBox<EnumAction> dbActions;

    public WindowEditUsingAction(WrappedItem item)
    {
        super(item, "usingAction", 150, 55);

        dbActions = dropBox(EnumAction.values()).top(7).fillWidth(7).add();
        dbActions.setStringProvider(this);
        dbActions.setSelectedValue(container.usingAction);
    }

    @Override
    protected void applyChanges()
    {
        container.usingAction = dbActions.getSelectedValue();
    }

    @Override
    public String getStringFor(EnumAction value)
    {
        return value.name();
    }
}
