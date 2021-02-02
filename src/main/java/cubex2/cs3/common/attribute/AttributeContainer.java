package cubex2.cs3.common.attribute;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.Window;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AttributeContainer
{
    protected final BaseContentPack pack;
    private final Map<Field, AttributeBridge> bridgeMap = Maps.newHashMap();

    private static final HashMap<Class, Field[]> fieldCache = Maps.newHashMap();

    public AttributeContainer(BaseContentPack pack)
    {
        this.pack = pack;
        createBridges();
    }

    public BaseContentPack getPack()
    {
        return pack;
    }

    public void loadFromNBT(NBTTagCompound compound, boolean postInit)
    {
        Field[] fields = getAttributeFields(postInit ? ATTRIBUTE_POST_INIT : ATTRIBUTE_NO_POST_INIT);

        try
        {
            for (Field field : fields)
            {
                if (compound.hasKey(field.getName()))
                {
                    AttributeBridge bridge = bridgeMap.get(field);
                    NBTTagCompound attributeCompound = compound.getCompoundTag(field.getName());
                    field.set(this, bridge.loadValueFromNBT(attributeCompound));
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        Field[] fields = getAttributeFields();

        for (Field field : fields)
        {
            try
            {
                AttributeBridge bridge = bridgeMap.get(field);
                NBTTagCompound attributeCompound = new NBTTagCompound();

                Object value = field.get(this);
                bridge.writeValueToNBT(attributeCompound, value);

                compound.setTag(field.getName(), attributeCompound);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private Field[] getFields()
    {
        Class<?> clazz = getClass();

        if (fieldCache.containsKey(clazz))
            return fieldCache.get(clazz);
        else
        {
            Field[] classFields = clazz.getFields();
            List<Field> list = Lists.newArrayList();
            for (Field field : classFields)
            {
                if (field.isAnnotationPresent(Attribute.class))
                    list.add(field);
            }
            Field[] fields = list.toArray(new Field[list.size()]);

            fieldCache.put(clazz, fields);

            return fields;
        }
    }

    public Field[] getAttributeFields(Predicate<Field>... predicates)
    {
        Iterator<Field> fields = Iterators.forArray(getFields());
        for (Predicate<Field> pred : predicates)
        {
            fields = Iterators.filter(fields, pred);
        }
        return Iterators.toArray(fields, Field.class);
    }

    private void createBridges()
    {
        try
        {
            for (Field field : getAttributeFields())
            {
                bridgeMap.put(field, getBridge(field));
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private AttributeBridge getBridge(Field field) throws Exception
    {
        Class<? extends AttributeBridge> bridgeClass = field.getAnnotation(Attribute.class).bridgeClass();
        if (bridgeClass == NullAttributeBridge.class)
        {
            bridgeClass = DefaultAttributeBridges.getDefaultBridge(field.getType());
            if (bridgeClass == NullAttributeBridge.class)
            {
                throw new RuntimeException("Attribute " + field.getName() + " doesn't have a bridge class!");
            }
        }
        AttributeBridge bridge = bridgeClass.newInstance();
        bridge.additionalInfo = field.getAnnotation(Attribute.class).additionalInfo();
        return bridge;
    }

    /**
     * Gets the data of the attributes that have 'hasOwnWindow' set to true.
     *
     * @param type The type of the block/item/...
     * @return The names of the fields.
     */
    public AttributeData[] getAttributeDatas(String type)
    {
        Field[] fields = getAttributeFields(ATTRIBUTE_WITH_OWN_WINDOW, new PredicateCheckExclude(type, this));
        AttributeData[] datas = new AttributeData[fields.length];
        for (int i = 0; i < datas.length; i++)
        {
            datas[i] = new AttributeData(fields[i].getAnnotation(Attribute.class), fields[i]);
        }
        return datas;
    }

    protected boolean addAttribute(String attribute, String type)
    {
        return true;
    }

    public <T> T getAttribute(String attributeName)
    {
        try
        {
            return (T) getClass().getField(attributeName).get(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public <T> void setAttribute(String attributeName, T value)
    {
        try
        {
            getClass().getField(attributeName).set(this, value);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static final Predicate<Field> ATTRIBUTE_NO_POST_INIT = new Predicate<Field>()
    {
        @Override
        public boolean apply(Field input)
        {
            return !input.getAnnotation(Attribute.class).loadOnPostInit();
        }
    };

    private static final Predicate<Field> ATTRIBUTE_POST_INIT = new Predicate<Field>()
    {
        @Override
        public boolean apply(Field input)
        {
            return input.getAnnotation(Attribute.class).loadOnPostInit();
        }
    };

    private static final Predicate<Field> ATTRIBUTE_WITH_OWN_WINDOW = new Predicate<Field>()
    {
        @Override
        public boolean apply(Field input)
        {
            return input.getAnnotation(Attribute.class).hasOwnWindow();
        }
    };

    public Class<? extends Window> getWindowClass(AttributeData item)
    {
        return item.attribute.windowClass();
    }

    private static class PredicateCheckExclude implements Predicate<Field>
    {
        private final AttributeContainer container;
        private String type;

        public PredicateCheckExclude(String type, AttributeContainer container)
        {
            this.type = type;
            this.container = container;
        }

        @Override
        public boolean apply(Field input)
        {
            return container.addAttribute(input.getName(), type);
        }
    }
}
