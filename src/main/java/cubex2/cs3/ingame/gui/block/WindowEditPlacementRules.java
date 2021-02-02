package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.CheckBox;

public class WindowEditPlacementRules extends WindowEditBlockAttribute
{
    private CheckBox cbPlaceOnFloor;
    private CheckBox cbPlaceOnCeiling;
    private CheckBox cbPlaceOnWall;

    public WindowEditPlacementRules(WrappedBlock block)
    {
        super(block, "placement", 150, 100);

        cbPlaceOnFloor = checkBox("Can place on floor").checked(container.canPlaceOnFloor).at(7, 7).add();

        cbPlaceOnCeiling = checkBox("Can place on ceiling").checked(container.canPlaceOnCeiling).below(cbPlaceOnFloor, 7).add();

        cbPlaceOnWall = checkBox("Can place on wall").checked(container.canPlaceOnWall).below(cbPlaceOnCeiling, 7).add();
    }

    @Override
    protected void applyChanges()
    {
        container.canPlaceOnFloor = cbPlaceOnFloor.getIsChecked();
        container.canPlaceOnCeiling = cbPlaceOnCeiling.getIsChecked();
        container.canPlaceOnWall = cbPlaceOnWall.getIsChecked();
    }
}
