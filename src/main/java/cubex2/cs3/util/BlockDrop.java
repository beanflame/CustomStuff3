package cubex2.cs3.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockDrop
{
    public static class DropData implements StackLabelItem
    {
        String itemName;
        Item item;
        int dv;
        int minCount;
        int maxCount;

        public DropData(Item item, int dv, int min, int max)
        {
            this.item = item;
            this.dv = dv;
            this.minCount = min;
            this.maxCount = max;
        }

        public DropData()
        {
        }

        public void setAmount(int min, int max)
        {
            minCount = min;
            maxCount = max;
        }

        private int getAmount(Random random)
        {
            if (minCount == maxCount)
                return minCount;

            return random.nextInt(maxCount + 1 - minCount) + minCount;
        }

        public Item getItem()
        {
            if (item == null)
                item = GeneralHelper.getItem(itemName);

            return item;
        }

        public ItemStack createStack()
        {
            return new ItemStack(getItem(), 1, dv);
        }

        public int getMinCount()
        {
            return minCount;
        }

        public int getMaxCount()
        {
            return maxCount;
        }

        @Override
        public ItemStack getStack()
        {
            return createStack();
        }

        @Override
        public String getLabel()
        {
            int min = getMinCount();
            int max = getMaxCount();
            return "Amount: " + min + (min != max ? " - " + max : "");
        }
    }

    private List<DropData> drops = Lists.newArrayList();

    public BlockDrop(Block block, int dv)
    {
        this(Item.getItemFromBlock(block), dv);
    }

    public BlockDrop(Item item, int dv)
    {
        DropData data = new DropData();
        data.item = item;
        data.dv = dv;
        data.minCount = 1;
        data.maxCount = 1;
        drops.add(data);
    }

    public List<DropData> getDrops()
    {
        return drops;
    }

    public BlockDrop()
    {
    }

    public ArrayList<ItemStack> getDrops(int fortuneAmount, Random random)
    {
        ArrayList<ItemStack> ret = Lists.newArrayList();

        for (DropData drop : drops)
        {
            int amount = drop.getAmount(random) + fortuneAmount;


            for (int i = 0; i < amount; i++)
            {
                ret.add(new ItemStack(drop.getItem(), 1, drop.dv));
            }
        }

        return ret;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        drops.clear();

        NBTTagList list = nbt.getTagList("Drops", 10);
        for (int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound dropNBT = list.getCompoundTagAt(i);

            DropData drop = new DropData();
            drop.itemName = dropNBT.getString("Item");
            drop.dv = dropNBT.getInteger("DV");
            drop.minCount = dropNBT.getInteger("Min");
            drop.maxCount = dropNBT.getInteger("Max");

            drops.add(drop);
        }
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        NBTTagList list = new NBTTagList();
        for (DropData drop : drops)
        {
            NBTTagCompound dropNbt = new NBTTagCompound();
            dropNbt.setString("Item", GeneralHelper.getItemName(drop.getItem()));
            dropNbt.setInteger("DV", drop.dv);
            dropNbt.setInteger("Min", drop.minCount);
            dropNbt.setInteger("Max", drop.maxCount);

            list.appendTag(dropNbt);
        }

        nbt.setTag("Drops", list);
    }
}
