package cubex2.cs3.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AttributeContent extends BaseContent
{
    protected String name;

    public AttributeContent(String name, BaseContentPack pack)
    {
        super(pack);
        this.name = name;
    }

    public AttributeContent(BaseContentPack pack)
    {
        super(pack);
    }

    public String getName()
    {
        return name;
    }

    public abstract AttributeContainer getContainer();

    public abstract String getTypeString();

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", name);
        compound.setString("Type", getTypeString());

        NBTTagCompound attributesCompound = new NBTTagCompound();
        if (getContainer() != null)
        {
            getContainer().writeToNBT(attributesCompound);
        }
        compound.setTag("Attributes", attributesCompound);
    }
}
