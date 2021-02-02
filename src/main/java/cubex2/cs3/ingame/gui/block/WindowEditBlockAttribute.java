package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.common.WindowEditContainerAttribute;

public abstract class WindowEditBlockAttribute extends WindowEditContainerAttribute<BlockAttributes>
{
    protected final WrappedBlock wrappedBlock;

    public WindowEditBlockAttribute(WrappedBlock block, String title, int usedControls, int width, int height)
    {
        super(block.container, title, usedControls, width, height);
        wrappedBlock = block;
    }

    public WindowEditBlockAttribute(WrappedBlock block, String title, int width, int height)
    {
        super(block.container, title, EDIT | CANCEL, width, height);
        wrappedBlock = block;
    }
}
