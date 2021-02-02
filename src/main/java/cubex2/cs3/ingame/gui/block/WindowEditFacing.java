package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.block.attributes.FacingAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.CheckBox;

public class WindowEditFacing extends WindowEditBlockAttribute
{
    private CheckBox cbCanFaceTop;
    private CheckBox cbCanFaceBottom;
    private CheckBox cbCanFaceSides;
    private CheckBox cbFaceBySide;

    FacingAttributes attributes;

    public WindowEditFacing(WrappedBlock block)
    {
        super(block, "facing", 150, 100);

        attributes = (FacingAttributes) container;

        cbCanFaceTop = checkBox("Can face upwards").checked(attributes.canFaceTop).at(7, 7).add();
        cbCanFaceBottom = checkBox("Can face downwards").checked(attributes.canFaceBottom).below(cbCanFaceTop, 7).add();
        cbCanFaceSides = checkBox("Can face sidewards").checked(attributes.canFaceSides).below(cbCanFaceBottom, 7).add();
        cbFaceBySide = checkBox("Face by clicked side").checked(attributes.faceBySide).below(cbCanFaceSides, 7).add();
    }

    @Override
    protected void applyChanges()
    {
        attributes.canFaceTop = cbCanFaceTop.getIsChecked();
        attributes.canFaceBottom = cbCanFaceBottom.getIsChecked();
        attributes.canFaceSides = cbCanFaceSides.getIsChecked();
        attributes.faceBySide = cbFaceBySide.getIsChecked();
    }
}
