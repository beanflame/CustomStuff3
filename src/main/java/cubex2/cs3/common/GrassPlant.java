package cubex2.cs3.common;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.util.NBTHelper;
import cubex2.cs3.util.StackLabelItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;

public class GrassPlant extends BaseContent implements StackLabelItem
{
    public ItemStack block;
    public int weight;

    private BiomeGenBase.FlowerEntry entry;

    public GrassPlant(BaseContentPack pack)
    {
        super(pack);
    }

    public GrassPlant(ItemStack block, int weight, BaseContentPack pack)
    {
        super(pack);
        this.block = block;
        this.weight = weight;
    }

    @Override
    public void apply()
    {
        Block block = Block.getBlockFromItem(this.block.getItem());
        int meta = this.block.getItemDamage();
        entry = new BiomeGenBase.FlowerEntry(block, meta, weight);

        addToBiomes();

        super.apply();
    }

    private void addToBiomes()
    {
        for (BiomeGenBase bgb : BiomeGenBase.getBiomeGenArray())
        {
            if (bgb != null)
            {
                List<BiomeGenBase.FlowerEntry> entries = ReflectionHelper.getPrivateValue(BiomeGenBase.class, bgb, "flowers");
                entries.add(entry);
            }
        }
    }

    @Override
    public void remove()
    {
        removeFromBiomes();

        super.remove();
    }

    private void removeFromBiomes()
    {
        for (BiomeGenBase bgb : BiomeGenBase.getBiomeGenArray())
        {
            if (bgb != null)
            {
                List<BiomeGenBase.FlowerEntry> entries = ReflectionHelper.getPrivateValue(BiomeGenBase.class, bgb, "flowers");
                entries.remove(entry);
            }
        }
    }

    @Override
    public void edit()
    {
        removeFromBiomes();
        addToBiomes();

        super.edit();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTHelper.writeToNBT(block, "Block", compound);
        compound.setInteger("Weight", weight);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        block = NBTHelper.readStackFromNBT("Block", compound);
        weight = compound.getInteger("Weight");

        return block != null;
    }

    @Override
    public ItemStack getStack()
    {
        return block;
    }

    @Override
    public String getLabel()
    {
        return "Weight: " + weight;
    }
}
