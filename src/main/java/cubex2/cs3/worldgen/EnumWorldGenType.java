package cubex2.cs3.worldgen;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.worldgen.attributes.WorldGenAttributes;
import cubex2.cs3.worldgen.attributes.WorldGenFlowerAttributes;
import cubex2.cs3.worldgen.attributes.WorldGenOreAttributes;

import java.util.Map;

public enum EnumWorldGenType
{
    ORE("ore", WorldGenCSOre.class, WorldGenOreAttributes.class),
    FLOWER("flower", WorldGenCSFlower.class, WorldGenFlowerAttributes.class);

    public final String name;
    public final Class<? extends WorldGenCS> worldGenClass;
    public final Class<? extends WorldGenAttributes> attributeClass;

    EnumWorldGenType(String name, Class<? extends WorldGenCS> worldGenClass, Class<? extends WorldGenAttributes> attributeClass)
    {
        this.name = name;
        this.worldGenClass = worldGenClass;
        this.attributeClass = attributeClass;
    }

    public WorldGenCS createWorldGen(WrappedWorldGen wrappedWorldGen)
    {
        try
        {
            WorldGenCS worldGen = worldGenClass.getConstructor(WrappedWorldGen.class).newInstance(wrappedWorldGen);
            GameRegistry.registerWorldGenerator(worldGen, 10);
            return worldGen;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public WorldGenAttributes createAttributeContainer(WrappedWorldGen wrappedWorldGen)
    {
        try
        {
            return attributeClass.getConstructor(BaseContentPack.class).newInstance(wrappedWorldGen.getPack());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Map<String, EnumWorldGenType> map = Maps.newHashMap();

    public static EnumWorldGenType get(String name)
    {
        if (map.isEmpty())
        {
            for (EnumWorldGenType e : values())
            {
                map.put(e.name, e);
            }
        }
        return map.get(name);
    }
}
