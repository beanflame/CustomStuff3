package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.common.WindowEditFloat;

public class WindowEditSlipperiness extends WindowEditFloat
{
    private final WrappedBlock wrappedBlock;

    public WindowEditSlipperiness(WrappedBlock wrappedBlock)
    {
        super("slipperiness",null, wrappedBlock.container);
        this.wrappedBlock = wrappedBlock;
    }

    @Override
    protected void applyChangedValue()
    {
        wrappedBlock.block.slipperiness = wrappedBlock.container.slipperiness;
    }
}
