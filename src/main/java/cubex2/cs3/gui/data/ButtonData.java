package cubex2.cs3.gui.data;

import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.ScriptWrapper;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public class ButtonData extends ControlData
{
    public String text;
    public ScriptWrapper onClicked = null;

    public ButtonData(int x, int y, int width, int height, String text)
    {
        super(x, y, width, height);
        this.text = text;
    }

    public ButtonData()
    {
    }

    @Override
    public Control addToWindow(Window window, IInventory slotInv)
    {
        return window.button(text).at(0, 0).offset(x, y).size(width, height).add();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        text = compound.getString("Text");
        if (compound.hasKey("OnClicked"))
        {
            onClicked = new ScriptWrapper(compound.getString("OnClicked"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (text != null && text.length() > 0)
        {
            compound.setString("Text", text);
        }
        if (onClicked != null && onClicked.getSource() != null)
        {
            compound.setString("OnClicked", onClicked.getSource());
        }
    }

    @Override
    protected String getControlType()
    {
        return "button";
    }
}
