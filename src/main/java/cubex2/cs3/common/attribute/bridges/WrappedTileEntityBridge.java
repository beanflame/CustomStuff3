package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.registry.TileEntityRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class WrappedTileEntityBridge extends AttributeBridge<WrappedTileEntity>
{
    @Override
    public WrappedTileEntity loadValueFromNBT(NBTTagCompound compound)
    {
        if (!compound.hasKey("PackName"))
            return null;

        BaseContentPack pack = BaseContentPackLoader.instance().getContentPack(compound.getString("PackName"));
        TileEntityRegistry registry = (TileEntityRegistry) pack.getContentRegistry(WrappedTileEntity.class);
        return registry.getTileEntity(compound.getString("TileEntityName"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, WrappedTileEntity value)
    {
        if (value != null)
        {
            compound.setString("PackName", value.getPack().getName());
            compound.setString("TileEntityName", value.getName());
        }
    }
}
