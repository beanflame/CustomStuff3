package cubex2.cs3.common;

import cubex2.cs3.handler.VillageTradeHandler;
import cubex2.cs3.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipe;

public class TradeRecipe extends BaseContent
{
    public int profession;
    public ItemStack input1;
    public ItemStack input2;
    public ItemStack result;
    public float chance;

    public MerchantRecipe recipe;

    public TradeRecipe(BaseContentPack pack)
    {
        super(pack);
    }

    public TradeRecipe(int profession, ItemStack input1, ItemStack input2, ItemStack result, float chance, BaseContentPack pack)
    {
        super(pack);
        this.profession = profession;
        this.input1 = input1;
        this.input2 = input2;
        this.result = result;
        this.chance = chance;
    }

    @Override
    public void apply()
    {
        recipe = new MerchantRecipe(input1, input2, result);
        VillageTradeHandler.INSTANCE.addRecipe(this);

        super.apply();
    }

    @Override
    public void remove()
    {
        VillageTradeHandler.INSTANCE.removeRecipe(this);

        super.remove();
    }

    @Override
    public void edit()
    {
        recipe = new MerchantRecipe(input1, input2, result);
        VillageTradeHandler.INSTANCE.addRecipe(this);

        super.edit();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Profession", profession);
        NBTHelper.writeToNBT(input1, "Input1", compound);
        NBTHelper.writeToNBT(input2, "Input2", compound);
        NBTHelper.writeToNBT(result, "Result", compound);
        compound.setFloat("Chance", chance);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        profession = compound.getInteger("Profession");
        input1 = NBTHelper.readStackFromNBT("Input1", compound);
        input2 = NBTHelper.readStackFromNBT("Input2", compound);
        result = NBTHelper.readStackFromNBT("Result", compound);
        chance = compound.getFloat("Chance");

        return input1 != null && result != null;
    }
}
