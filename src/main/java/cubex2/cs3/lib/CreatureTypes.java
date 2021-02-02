package cubex2.cs3.lib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.entity.EnumCreatureType;

public class CreatureTypes
{
    private static final BiMap<String, EnumCreatureType> map = HashBiMap.create();

    public static String getName(EnumCreatureType type)
    {
        return map.inverse().containsKey(type) ? map.inverse().get(type) : "monster";
    }

    public static EnumCreatureType getType(String name)
    {
        return map.containsKey(name) ? map.get(name) : EnumCreatureType.monster;
    }

    static
    {
        map.put("monster", EnumCreatureType.monster);
        map.put("creature", EnumCreatureType.creature);
        map.put("ambient", EnumCreatureType.ambient);
        map.put("water", EnumCreatureType.waterCreature);
    }
}
