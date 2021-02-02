package cubex2.cs3.util;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GeneralHelper
{
    public static Block getBlock(String value)
    {
        return GameData.getBlockRegistry().getObject(value);
    }

    public static String getBlockName(Block block)
    {
        if (block == Blocks.air)
        {
            return "minecraft:air";
        }
        if (GameData.getBlockRegistry().getId(block) > 0)
        {
            return GameData.getBlockRegistry().getNameForObject(block);
        }
        return null;
    }

    public static Item getItem(String value)
    {
        return GameData.getItemRegistry().getObject(value);
    }

    public static String getItemName(Item item)
    {
        if (GameData.getItemRegistry().getId(item) > 0)
        {
            return GameData.getItemRegistry().getNameForObject(item);
        }
        return null;
    }

    public static String[] getChestGenNames()
    {
        HashMap<String, ChestGenHooks> chestInfo = ReflectionHelper.getPrivateValue(ChestGenHooks.class, null, "chestInfo");
        String[] ret = chestInfo.keySet().toArray(new String[chestInfo.size()]);
        Arrays.sort(ret);
        return ret;
    }

    public static String[] getMobNames()
    {
        List<String> list = Lists.newArrayList();
        for (Object clazz : EntityList.classToStringMapping.keySet())
        {
            if (clazz instanceof Class && EntityLiving.class.isAssignableFrom((Class<?>) clazz))
            {
                list.add((String) EntityList.classToStringMapping.get(clazz));
            }
        }
        String[] ret = list.toArray(new String[list.size()]);
        Arrays.sort(ret);
        return ret;
    }

    public static String rangeToString(int min, int max)
    {
        String res = String.valueOf(min);
        if (max != min)
            res += "-" + max;
        return res;
    }

    public static void dropItems(IInventory inv, World world, int x, int y, int z, Block block)
    {
        if (inv != null)
        {
            for (int i1 = 0; i1 < inv.getSizeInventory(); ++i1)
            {
                ItemStack itemstack = inv.getStackInSlot(i1);

                if (itemstack != null)
                {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                    {
                        int j1 = world.rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }
    }
}
