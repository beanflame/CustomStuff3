package cubex2.cs3.gui.data;

import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.NBTData;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ControlData implements NBTData
{
    public int x;
    public int y;
    public int width;
    public int height;

    public ControlData(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public ControlData()
    {
    }

    public boolean isSizeable()
    {
        return true;
    }

    public abstract Control addToWindow(Window window, IInventory slotInv);

    protected abstract String getControlType();

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("ControlType", getControlType());
        compound.setInteger("X", x);
        compound.setInteger("Y", y);
        compound.setInteger("Width", width);
        compound.setInteger("Height", height);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        x = compound.getInteger("X");
        y = compound.getInteger("Y");
        width = compound.getInteger("Width");
        height = compound.getInteger("Height");
    }
}
