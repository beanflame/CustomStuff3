package cubex2.cs3.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public interface Filter<T>
{
    boolean matches(T obj, String searchText);

    Filter<ItemStack> ITEM_STACK = new Filter<ItemStack>()
    {
        @Override
        public boolean matches(ItemStack obj, String searchText)
        {
            return obj != null && StringUtils.containsIgnoreCase(obj.getDisplayName(), searchText);
        }
    };

    Filter<OreDictionaryClass> ORE_CLASS = new Filter<OreDictionaryClass>()
    {
        @Override
        public boolean matches(OreDictionaryClass obj, String searchText)
        {
            return obj != null && StringUtils.containsIgnoreCase(obj.oreClass, searchText);
        }
    };

    Filter<ResourceLocation> RESOURCE_LOCATION = new Filter<ResourceLocation>()
    {
        @Override
        public boolean matches(ResourceLocation obj, String searchText)
        {
            return obj != null && StringUtils.containsIgnoreCase(obj.toString(), searchText);
        }
    };

}
