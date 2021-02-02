package cubex2.cs3.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.util.PostponableTask;
import cubex2.cs3.worldgen.EnumWorldGenType;
import cubex2.cs3.worldgen.WorldGenCS;
import cubex2.cs3.worldgen.attributes.WorldGenAttributes;
import net.minecraft.nbt.NBTTagCompound;

public class WrappedWorldGen extends AttributeContent implements Comparable<WrappedWorldGen>
{
    public WorldGenCS worldGen;
    public WorldGenAttributes container;

    private EnumWorldGenType type;

    public WrappedWorldGen(String name, EnumWorldGenType type, BaseContentPack pack)
    {
        super(name, pack);
        this.type = type;
    }

    public WrappedWorldGen(BaseContentPack pack)
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

    public EnumWorldGenType getType()
    {
        return type;
    }

    @Override
    public boolean readFromNBT(final NBTTagCompound compound)
    {
        name = compound.getString("Name");
        type = EnumWorldGenType.get(compound.getString("Type"));

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

        worldGen = type.createWorldGen(this);

        return true;
    }

    @Override
    public int compareTo(WrappedWorldGen o)
    {
        return name.compareTo(o.name);
    }
}
