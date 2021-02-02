package cubex2.cs3.tileentity.data;

import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.NBTData;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;

public class FurnaceModule implements NBTData, IPurposeStringProvider
{
    public String name;
    public String recipeList = "vanilla";
    public String fuelList = "vanilla";

    public int inputSlot;
    public int outputSlot;
    public int fuelSlot;

    public int cookTime = 200;

    public FurnaceModule(String name, String recipeList, String fuelList, int inputSlot, int outputSlot, int fuelSlot, int cookTime)
    {
        this.name = name;
        this.recipeList = recipeList;
        this.fuelList = fuelList;
        this.inputSlot = inputSlot;
        this.outputSlot = outputSlot;
        this.fuelSlot = fuelSlot;
        this.cookTime = cookTime;
    }

    public FurnaceModule()
    {
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", name);
        compound.setString("RecipeList", recipeList);
        compound.setString("FuelList", fuelList);
        compound.setInteger("Input", inputSlot);
        compound.setInteger("Output", outputSlot);
        compound.setInteger("Fuel", fuelSlot);
        compound.setInteger("CookTime", cookTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        if (compound.hasKey("RecipeList"))
            recipeList = compound.getString("RecipeList");
        if (compound.hasKey("FuelList"))
            fuelList = compound.getString("FuelList");
        inputSlot = compound.getInteger("Input");
        outputSlot = compound.getInteger("Output");
        fuelSlot = compound.getInteger("Fuel");
        cookTime = compound.getInteger("CookTime");
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        if (purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl)
        {
            return String.format("%s - I:%d O:%d F:%d CT:%d", name, inputSlot, outputSlot, fuelSlot, cookTime);
        }
        return null;
    }
}
