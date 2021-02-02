package cubex2.cs3.gui;

import cpw.mods.fml.common.FMLCommonHandler;
import cubex2.cs3.common.BaseContentPack;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotFurnaceOutput extends Slot
{
    private final String recipeList;
    private final EntityPlayer player;
    private final BaseContentPack pack;
    private int numCrafted;

    public SlotFurnaceOutput(BaseContentPack pack, String recipeList, EntityPlayer player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
        this.pack = pack;
        this.recipeList = recipeList;
    }

    protected void onCrafting(ItemStack stack)
    {
        stack.onCrafting(player.worldObj, player, numCrafted);

        if (!player.worldObj.isRemote)
        {
            int i = numCrafted;
            float f = pack.smeltingRecipeHandler.getSmeltingExperience(stack, recipeList);

            if (f == 0.0F)
            {
                i = 0;
            } else if (f < 1.0F)
            {
                int j = MathHelper.floor_float((float) i * f);

                if (j < MathHelper.ceiling_float_int((float) i * f) && Math.random() < (double) ((float) i * f - (float) j))
                {
                    ++j;
                }

                i = j;
            }

            while (i > 0)
            {
                int k = EntityXPOrb.getXPSplit(i);
                i -= k;
                player.worldObj.spawnEntityInWorld(new EntityXPOrb(player.worldObj, player.posX, player.posY + 0.5D, player.posZ + 0.5D, k));
            }
        }

        numCrafted = 0;

        if (recipeList.equals("vanilla"))
            FMLCommonHandler.instance().firePlayerSmeltedEvent(player, stack);

        if (stack.getItem() == Items.iron_ingot)
        {
            player.triggerAchievement(AchievementList.acquireIron);
        }

        if (stack.getItem() == Items.cooked_fished)
        {
            player.triggerAchievement(AchievementList.cookFish);
        }
    }
}
