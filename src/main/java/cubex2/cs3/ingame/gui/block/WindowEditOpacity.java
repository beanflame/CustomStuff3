package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;

public class WindowEditOpacity extends WindowEditInteger
{
    private final WrappedBlock wrappedBlock;

    public WindowEditOpacity(WrappedBlock wrappedBlock)
    {
        super("opacity", null, 0, 255, wrappedBlock.container);
        this.wrappedBlock = wrappedBlock;
    }

    @Override
    protected void applyChangedValue()
    {
        wrappedBlock.block.setLightOpacity(wrappedBlock.container.opacity);
    }
}
