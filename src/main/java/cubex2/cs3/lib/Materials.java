package cubex2.cs3.lib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.material.Material;

public class Materials
{
    private static BiMap<String, Material> materialMap = HashBiMap.create();

    public static Material getMaterial(String name)
    {
        if (name == null)
            return null;

        Material material = null;

        if (materialMap.containsKey(name))
        {
            material = materialMap.get(name);
        }

        return material;
    }

    public static String getMaterialName(Material material)
    {
        if (material == null)
            return null;

        String name = null;

        if (materialMap.inverse().containsKey(material))
        {
            name = materialMap.inverse().get(material);
        }

        return name;
    }

    public static Material[] getAllMaterials()
    {
        return materialMap.values().toArray(new Material[materialMap.values().size()]);
    }

    static
    {
        materialMap.put("cactus", Material.cactus);
        materialMap.put("circuits", Material.circuits);
        materialMap.put("clay", Material.clay);
        materialMap.put("cloth", Material.cloth);
        materialMap.put("craftedSnow", Material.craftedSnow);
        materialMap.put("fire", Material.fire);
        materialMap.put("glass", Material.glass);
        materialMap.put("grass", Material.grass);
        materialMap.put("ground", Material.ground);
        materialMap.put("ice", Material.ice);
        materialMap.put("iron", Material.iron);
        materialMap.put("lava", Material.lava);
        materialMap.put("leaves", Material.leaves);
        materialMap.put("plants", Material.plants);
        materialMap.put("pumpkin", Material.gourd);
        materialMap.put("redstoneLight", Material.redstoneLight);
        materialMap.put("rock", Material.rock);
        materialMap.put("sand", Material.sand);
        materialMap.put("snow", Material.snow);
        materialMap.put("sponge", Material.sponge);
        materialMap.put("tnt", Material.tnt);
        materialMap.put("vine", Material.vine);
        materialMap.put("water", Material.water);
        materialMap.put("wood", Material.wood);
    }
}
