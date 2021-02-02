package cubex2.cs3.util;

import cubex2.cs3.lib.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.Constants;

import java.util.Collection;
import java.util.List;

public class Util
{
    public static void writeStacksToNBT(String name, ItemStack[] stacks, NBTTagCompound nbt)
    {
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < stacks.length; i++)
        {
            if (stacks[i] != null)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("Slot", (byte) i);
                stacks[i].writeToNBT(compound);
                tagList.appendTag(compound);
            }
        }

        nbt.setTag(name, tagList);
    }

    public static void readStacksFromNBT(String name, ItemStack[] stacks, NBTTagCompound nbt)
    {
        NBTTagList tagList = nbt.getTagList(name, 10);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            int j = compound.getByte("Slot") & 255;

            if (j >= 0 && j < stacks.length)
            {
                stacks[j] = ItemStack.loadItemStackFromNBT(compound);
            }
        }
    }

    public static <T extends NBTData> void writeListToNBT(String name, Collection<T> list, NBTTagCompound nbt)
    {
        NBTTagList tagList = new NBTTagList();
        for (NBTData data : list)
        {
            NBTTagCompound compound = new NBTTagCompound();
            data.writeToNBT(compound);
            tagList.appendTag(compound);
        }
        nbt.setTag(name, tagList);
    }

    public static <T extends NBTData> void readListFromNBT(String name, Collection<T> list, NBTTagCompound nbt, Class<T> clazz)
    {
        NBTTagList tagList = nbt.getTagList(name, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            try
            {
                NBTTagCompound compound = tagList.getCompoundTagAt(i);
                T data = clazz.newInstance();
                data.readFromNBT(compound);
                list.add(data);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static <T> void writeListToNBT(String listName, List<T> list, MyBiConsumer<T, NBTTagCompound> writer, NBTTagCompound nbt)
    {
        NBTTagList tagList = new NBTTagList();
        for (T t : list)
        {
            NBTTagCompound compound = new NBTTagCompound();
            writer.accept(t, compound);
            tagList.appendTag(compound);
        }

        nbt.setTag(listName, tagList);
    }

    public static <T> void readListFromNBT(String listName, List<T> list, MyFunction<NBTTagCompound, T> reader, NBTTagCompound nbt)
    {
        NBTTagList tagList = nbt.getTagList(listName, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            list.add(reader.apply(compound));
        }
    }

    public static final MyBiConsumer<ItemStack, NBTTagCompound> NBT_ITEMSTACK_WRITER = new MyBiConsumer<ItemStack, NBTTagCompound>()
    {
        @Override
        public void accept(ItemStack itemStack, NBTTagCompound nbtTagCompound)
        {
            if (itemStack != null)
            {
                itemStack.writeToNBT(nbtTagCompound);
            }
        }
    };

    public static final MyFunction<NBTTagCompound, ItemStack> NBT_ITEMSTACK_READER = new MyFunction<NBTTagCompound, ItemStack>()
    {
        @Override
        public ItemStack apply(NBTTagCompound nbtTagCompound)
        {
            return ItemStack.loadItemStackFromNBT(nbtTagCompound);
        }
    };

    public static final MyBiConsumer<String, NBTTagCompound> NBT_STRING_WRITER = new MyBiConsumer<String, NBTTagCompound>()
    {
        @Override
        public void accept(String string, NBTTagCompound nbtTagCompound)
        {
            if (string != null && string.length() > 0)
            {
                nbtTagCompound.setString("string", string);
            }
        }
    };

    public static final MyFunction<NBTTagCompound, String> NBT_STRING_READER = new MyFunction<NBTTagCompound, String>()
    {
        @Override
        public String apply(NBTTagCompound nbtTagCompound)
        {
            return nbtTagCompound.getString("string");
        }
    };

    public static final MyBiConsumer<Integer, NBTTagCompound> NBT_INTEGER_WRITER = new MyBiConsumer<Integer, NBTTagCompound>()
    {
        @Override
        public void accept(Integer i, NBTTagCompound nbtTagCompound)
        {
            if (i != null)
            {
                nbtTagCompound.setInteger("int", i);
            }
        }
    };

    public static final MyFunction<NBTTagCompound, Integer> NBT_INTEGER_READER = new MyFunction<NBTTagCompound, Integer>()
    {
        @Override
        public Integer apply(NBTTagCompound nbtTagCompound)
        {
            return nbtTagCompound.getInteger("int");
        }
    };

    public static final MyBiConsumer<BiomeGenBase, NBTTagCompound> NBT_BIOME_WRITER = new MyBiConsumer<BiomeGenBase, NBTTagCompound>()
    {
        @Override
        public void accept(BiomeGenBase biome, NBTTagCompound nbtTagCompound)
        {
            if (biome != null)
            {
                nbtTagCompound.setString("Value", biome.biomeName);
            }
        }
    };

    public static final MyFunction<NBTTagCompound, BiomeGenBase> NBT_BIOME_READER = new MyFunction<NBTTagCompound, BiomeGenBase>()
    {
        @Override
        public BiomeGenBase apply(NBTTagCompound nbtTagCompound)
        {
            return Biomes.getBiome(nbtTagCompound.getString("Value"));
        }
    };
}
