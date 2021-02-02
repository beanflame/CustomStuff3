package cubex2.cs3.gui.data;

import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public class LabelData extends ControlData
{
    public String text;

    public LabelData(int x, int y, String text)
    {
        super(x, y, 0, 0);
        this.text = text;
    }

    public LabelData()
    {
    }

    @Override
    public boolean isSizeable()
    {
        return false;
    }

    @Override
    public Control addToWindow(Window window, IInventory slotInv)
    {
        return window.label(text).at(0, 0).offset(x, y).add();
    }

    @Override
    protected String getControlType()
    {
        return "label";
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        text = compound.getString("Text");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("Text", text);
    }
}
