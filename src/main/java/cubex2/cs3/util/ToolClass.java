package cubex2.cs3.util;

import net.minecraft.nbt.NBTTagCompound;

public class ToolClass implements IPurposeStringProvider
{
    public String toolClass;
    public int harvestLevel;

    public ToolClass()
    {
    }

    public ToolClass(String toolClass, int harvestLevel)
    {
        this.toolClass = toolClass;
        this.harvestLevel = harvestLevel;
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setString("ToolClass", toolClass);
        nbt.setInteger("HarvestLevel", harvestLevel);
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        toolClass = nbt.getString("ToolClass");
        harvestLevel = nbt.getInteger("HarvestLevel");
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        if (purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl)
            return toolClass + " - " + harvestLevel;
        return null;
    }
}
