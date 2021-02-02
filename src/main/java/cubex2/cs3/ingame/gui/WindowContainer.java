package cubex2.cs3.ingame.gui;

import net.minecraft.inventory.Container;

public abstract class WindowContainer extends Window
{
    protected GuiContainerBase gui;

    public WindowContainer(int xSize, int ySize)
    {
        super(xSize, ySize);
    }

    public abstract Container getContainer();

    public void setGui(GuiContainerBase gui)
    {
        this.gui = gui;
    }
}
