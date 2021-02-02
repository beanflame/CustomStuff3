package cubex2.cs3.lib;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Biomes
{
    private static Map<String, BiomeGenBase> biomeMap = Maps.newHashMap();

    public static BiomeGenBase getBiome(String name)
    {
        if (name == null)
            return null;

        BiomeGenBase biome = null;

        if (biomeMap.containsKey(name))
        {
            biome = biomeMap.get(name);
        } else
        {
            for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray())
            {
                if (b != null && b.biomeName.equals(name))
                {
                    biomeMap.put(b.biomeName, b);
                    biome = b;
                    break;
                }
            }
        }

        return biome;
    }

    public static Set<String> getBiomeNames()
    {
        Set<String> res = Sets.newHashSet();
        for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray())
        {
            if (b != null)
            {
                res.add(b.biomeName);
            }
        }
        return res;
    }

    public static List<BiomeGenBase> getBiomes()
    {
        List<BiomeGenBase> res = Lists.newArrayList();
        for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray())
        {
            if (b != null)
            {
                res.add(b);
            }
        }
        return res;
    }

    public static final Comparator<BiomeGenBase> COMPARATOR = new Comparator<BiomeGenBase>()
    {
        @Override
        public int compare(BiomeGenBase o1, BiomeGenBase o2)
        {
            return o1.biomeName.compareTo(o2.biomeName);
        }
    };
}
