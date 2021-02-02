package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.control.ItemDisplay;

public class WindowEditGeneratedBlock extends WindowEditWorldGenAttribute
{
    private ItemDisplay itemDisplay;

    public WindowEditGeneratedBlock(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen, "generatedBlock", 150, 100);

        itemDisplay = itemDisplay().top(31).centerHor().add();
        itemDisplay.setItemStack(container.generatedBlock);
        itemDisplay.setDrawSlotBackground();
        itemDisplay.useSelectBlockDialog();
    }

    @Override
    protected void applyChanges()
    {
        container.generatedBlock = itemDisplay.getItemStack();
    }
}
