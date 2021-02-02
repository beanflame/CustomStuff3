package cubex2.cs3.handler;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cubex2.cs3.common.TradeRecipe;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.village.MerchantRecipeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VillageTradeHandler implements VillagerRegistry.IVillageTradeHandler
{
    public static final VillageTradeHandler INSTANCE = new VillageTradeHandler();

    private final Map<Integer, List<TradeRecipe>> recipes = Maps.newHashMap();

    private VillageTradeHandler()
    {
    }

    public void addRecipe(TradeRecipe recipe)
    {
        int villagerType = recipe.profession;

        if (!recipes.containsKey(villagerType))
        {
            recipes.put(villagerType, new ArrayList<TradeRecipe>());
            VillagerRegistry.instance().registerVillageTradeHandler(villagerType, INSTANCE);
        }
        if (!recipes.get(villagerType).contains(recipe))
        {
            recipes.get(villagerType).add(recipe);
        }
    }

    public void removeRecipe(TradeRecipe recipe)
    {
        if (recipes.containsKey(recipe.profession))
        {
            recipes.get(recipe.profession).remove(recipe);
        }
    }

    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
        if (recipes.containsKey(villager.getProfession()))
        {
            for (TradeRecipe recipe : recipes.get(villager.getProfession()))
            {
                if (random.nextFloat() < recipe.chance)
                {
                    recipeList.add(recipe.recipe);
                }
            }
        }
    }
}
