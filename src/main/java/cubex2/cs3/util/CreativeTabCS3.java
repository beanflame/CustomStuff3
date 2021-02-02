package cubex2.cs3.util;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabCS3 extends CreativeTabs
{
    public ItemStack icon;

    public CreativeTabCS3(String label, ItemStack icon)
    {
        super(label);
        this.icon = icon;
    }

    @Override
    public ItemStack getIconItemStack()
    {
        return icon;
    }

    @Override
    public Item getTabIconItem()
    {
        return icon.getItem();
    }
}
