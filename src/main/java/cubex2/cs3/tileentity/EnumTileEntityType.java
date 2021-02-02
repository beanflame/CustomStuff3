package cubex2.cs3.tileentity;

import com.google.common.collect.Maps;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.tileentity.attributes.TileEntityAttributes;
import cubex2.cs3.tileentity.attributes.TileEntityInventoryAttributes;

import java.lang.reflect.Constructor;
import java.util.Map;

public enum EnumTileEntityType
{
    NORMAL("normal", TileEntityCS.class, TileEntityAttributes.class),
    INVENTORY("inventory", TileEntityInventory.class, TileEntityInventoryAttributes.class);

    public final String name;
    public final Class<? extends TileEntityCS> tileEntityClass;
    public final Class<? extends TileEntityAttributes> attributeClass;

    private final Constructor<? extends TileEntityCS> constructor;

    EnumTileEntityType(String name, Class<? extends TileEntityCS> tileEntityClass, Class<? extends TileEntityAttributes> attributeClass)
    {
        this.name = name;
        this.tileEntityClass = tileEntityClass;
        this.attributeClass = attributeClass;
        constructor = createConstructor();
    }

    private Constructor<? extends TileEntityCS> createConstructor()
    {
        try
        {
            return tileEntityClass.getConstructor(WrappedTileEntity.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public TileEntityCS createTileEntity(WrappedTileEntity wrappedTileEntity)
    {
        try
        {
            TileEntityCS tileEntity = constructor.newInstance(wrappedTileEntity);
            return tileEntity;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public TileEntityAttributes createAttributeContainer(WrappedTileEntity wrappedTileEntity)
    {
        try
        {
            return attributeClass.getConstructor(BaseContentPack.class).newInstance(wrappedTileEntity.getPack());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Map<String, EnumTileEntityType> map = Maps.newHashMap();

    public static EnumTileEntityType get(String name)
    {
        if (map.isEmpty())
        {
            for (EnumTileEntityType e : values())
            {
                map.put(e.name, e);
            }
        }
        return map.get(name);
    }
}
