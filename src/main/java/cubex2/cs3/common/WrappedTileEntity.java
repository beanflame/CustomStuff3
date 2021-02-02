package cubex2.cs3.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.tileentity.EnumTileEntityType;
import cubex2.cs3.tileentity.TileEntityCS;
import cubex2.cs3.tileentity.attributes.TileEntityAttributes;
import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.PostponableTask;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;

public class WrappedTileEntity extends AttributeContent implements Comparable<WrappedTileEntity>, IPurposeStringProvider
{
    public TileEntityAttributes container;
    private EnumTileEntityType type;

    public WrappedTileEntity(String name, EnumTileEntityType type, BaseContentPack pack)
    {
        super(name, pack);
        this.type = type;
    }

    public WrappedTileEntity(BaseContentPack pack)
    {
        super(pack);
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

    public EnumTileEntityType getType()
    {
        return type;
    }

    public TileEntityCS createTileEntity()
    {
        return type.createTileEntity(this);
    }

    @Override
    public boolean readFromNBT(final NBTTagCompound compound)
    {
        name = compound.getString("Name");
        type = EnumTileEntityType.get(compound.getString("Type"));

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
    public int compareTo(WrappedTileEntity o)
    {
        return name.compareTo(o.name);
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        return name;
    }
}
