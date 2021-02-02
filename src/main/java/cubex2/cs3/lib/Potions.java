package cubex2.cs3.lib;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.potion.Potion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Potions
{
    private static Map<String, Potion> potionMap = Maps.newHashMap();

    public static Potion getPotion(String name)
    {
        if (name == null)
            return null;

        Potion potion = null;

        if (!name.startsWith("potion."))
        {
            name = "potion.".concat(name);
        }

        if (potionMap.containsKey(name))
        {
            potion = potionMap.get(name);
        } else
        {
            for (Potion p : Potion.potionTypes)
            {
                if (p == null)
                {
                    continue;
                }
                if (p.getName().equals(name))
                {
                    potionMap.put(p.getName(), p);
                    potion = p;
                    break;
                }
            }
        }

        return potion;
    }

    public static String getPotionName(Potion potion)
    {
        return potion == null ? null : potion.getName().replaceFirst("potion\\.", "");
    }

    public static Potion[] getPotions(boolean addNull)
    {
        List<Potion> res = Lists.newArrayList();
        for (Potion p : Potion.potionTypes)
        {
            if (p != null)
                res.add(p);
        }
        Collections.sort(res, COMPARATOR);
        if (addNull)
            res.add(0, null);
        return res.toArray(new Potion[res.size()]);
    }

    private static final Comparator<Potion> COMPARATOR = new Comparator<Potion>()
    {
        @Override
        public int compare(Potion o1, Potion o2)
        {
            return getPotionName(o1).compareTo(getPotionName(o2));
        }
    };
}
