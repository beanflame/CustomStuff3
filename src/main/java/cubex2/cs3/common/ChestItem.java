package cubex2.cs3.common;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.util.GeneralHelper;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.StackLabelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import java.util.ArrayList;

public class ChestItem extends BaseContent implements StackLabelItem
{
    public String chest;
    public ItemStack stack;
    public int minCount = 1;
    public int maxCount = 1;
    public int rarity;

    private String curChest;
    private WeightedRandomChestContent content;

    public ChestItem(ItemStack stack, String chest, int minCount, int maxCount, int rarity, BaseContentPack pack)
    {
        super(pack);
        this.stack = stack;
        this.chest = chest;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.rarity = rarity;
    }

    public ChestItem(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public void apply()
    {
        content = new WeightedRandomChestContent(stack, minCount, maxCount, rarity);
        curChest = chest;
        ChestGenHooks.getInfo(chest).addItem(content);

        super.apply();
    }

    @Override
    public void remove()
    {
        removeItem();

        super.remove();
    }

    @Override
    public void edit()
    {
        content.theItemId = stack;
        content.theMinimumChanceToGenerateItem = minCount;
        content.theMaximumChanceToGenerateItem = maxCount;
        content.itemWeight = rarity;

        if (!chest.equals(curChest))
        {
            removeItem();
            ChestGenHooks.getInfo(chest).addItem(content);
            curChest = chest;
        }
        super.edit();
    }

    private void removeItem()
    {
        ArrayList<WeightedRandomChestContent> contents = ReflectionHelper.getPrivateValue(ChestGenHooks.class, ChestGenHooks.getInfo(curChest), "contents");
        contents.remove(content);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Chest", chest);
        compound.setTag("Item", ItemStackHelper.writeToNBTNamed(stack));
        compound.setInteger("MinCount", minCount);
        compound.setInteger("MaxCount", maxCount);
        compound.setInteger("Rarity", rarity);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        chest = compound.getString("Chest");
        stack = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Item"));
        minCount = compound.getInteger("MinCount");
        maxCount = compound.getInteger("MaxCount");
        rarity = compound.getInteger("Rarity");

        return stack != null;
    }

    @Override
    public ItemStack getStack()
    {
        return stack;
    }

    @Override
    public String getLabel()
    {
        return chest + ", Count: " + GeneralHelper.rangeToString(minCount, maxCount) + ", Rarity: " + rarity;
    }
}
