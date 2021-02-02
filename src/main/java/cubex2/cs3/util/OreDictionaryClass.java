package cubex2.cs3.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class OreDictionaryClass implements Comparable<OreDictionaryClass>
{
    private static final Map<String, OreDictionaryClass> instances = Maps.newHashMap();

    public static OreDictionaryClass getInstance(String oreClass)
    {
        if (!instances.containsKey(oreClass))
            instances.put(oreClass, new OreDictionaryClass(oreClass));

        return instances.get(oreClass);
    }

    public static List<OreDictionaryClass> getAllClasses()
    {
        List<OreDictionaryClass> list = Lists.newArrayList();
        for (String s : OreDictionary.getOreNames())
        {
            list.add(getInstance(s));
        }
        return list;
    }

    public static List<OreDictionaryClass> getAllClassesSorted()
    {
        List<OreDictionaryClass> list = getAllClasses();
        Collections.sort(list);
        return list;
    }

    public final String oreClass;

    private OreDictionaryClass(String oreClass)
    {
        this.oreClass = oreClass;
    }

    /*@Override
    public ItemStack getItemStack()
    {
        return OreDictionary.getOres(oreClass).get(0);
    }

    @Override
    public List<ItemStack> getItemStacks()
    {
        return OreDictionary.getOres(oreClass);
    }

    @Override
    public boolean isRepresentingStack(ItemStack stack)
    {
        for (ItemStack oreStack : OreDictionary.getOres(oreClass))
        {
            if (oreStack.getItem() == stack.getItem() &&
                    (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == oreStack.getItemDamage()))
                return true;
        }
        return false;
    }*/

    /*@Override
    public void modifyToolTip(List<String> toolTipList, IItemMatcher itemMatcher)
    {
        toolTipList.add(EnumChatFormatting.GRAY + "Ore Class: " + oreClass);
    }*/

    @Override
    public int compareTo(OreDictionaryClass o)
    {
        return oreClass.compareTo(o.oreClass);
    }
}
