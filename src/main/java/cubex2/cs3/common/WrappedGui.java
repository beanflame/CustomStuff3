package cubex2.cs3.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.gui.EnumGuiType;
import cubex2.cs3.gui.attributes.GuiAttributes;
import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.PostponableTask;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;

public class WrappedGui extends AttributeContent implements Comparable<WrappedGui>, IPurposeStringProvider
{
    public GuiAttributes container;
    private EnumGuiType type;

    public WrappedGui(String name, EnumGuiType type, BaseContentPack pack)
    {
        super(name, pack);
        this.type = type;
    }

    public WrappedGui(BaseContentPack pack)
    {
        super(pack);
    }

    public EnumGuiType getType()
    {
        return type;
    }

    @Override
    public AttributeContainer getContainer()
    {
        return container;
    }

    @Override
    public String getTypeString()
    {
        return type.name;
    }

    @Override
    public int compareTo(WrappedGui o)
    {
        return o.getName().compareTo(getName());
    }

    @Override
    public boolean readFromNBT(final NBTTagCompound compound)
    {
        name = compound.getString("Name");
        type = EnumGuiType.get(compound.getString("Type"));

        container = type.createAttributeContainer(this);
        container.loadFromNBT(compound.getCompoundTag("Attributes"), false);
        pack.postponeHandler.addTask(new PostponableTask()
        {
            @Override
            protected boolean doWork()
            {
                container.loadFromNBT(compound.getCompoundTag("Attributes"), true);
                return true;
            }
        });

        return true;
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        return name;
    }
}
