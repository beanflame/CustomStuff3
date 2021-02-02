package cubex2.cs3.gui.data;

import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public class SlotData extends ControlData
{
    public boolean furnaceOutput = false;
    public String recipeList = "vanilla";

    public SlotData()
    {
    }

    public SlotData(int x, int y, boolean furnaceOutput, String recipeList)
    {
        super(x, y, 0, 0);
        this.furnaceOutput = furnaceOutput;
        this.recipeList = recipeList;
    }

    @Override
    public boolean isSizeable()
    {
        return false;
    }

    @Override
    public Control addToWindow(Window window, IInventory slotInv)
    {
        return window.inventorySlot().at(0, 0).offset(x, y).add();
    }

    @Override
    protected String getControlType()
    {
        return "invSlot";
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("FurnaceOutput", furnaceOutput);
        compound.setString("RecipeList", recipeList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        furnaceOutput = compound.getBoolean("FurnaceOutput");
        if (compound.hasKey("RecipeList"))
            recipeList = compound.getString("RecipeList");
    }
}
