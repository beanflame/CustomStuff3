package cubex2.cs3.common;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.CreativeTabCS3;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.StackLabelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;
import java.util.Properties;

public class CreativeTab extends BaseContent implements StackLabelItem
{
    public String name;
    public String label;
    public ItemStack icon;

    private CreativeTabCS3 tab;

    public CreativeTab(BaseContentPack pack)
    {
        super(pack);
    }

    public CreativeTab(String name, String label, ItemStack icon, BaseContentPack pack)
    {
        super(pack);
        this.name = name;
        this.label = label;
        this.icon = icon;
    }

    @Override
    public void apply()
    {
        tab = new CreativeTabCS3(name, icon);

        Map<String, Properties> modLangData = ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), "modLanguageData");
        Properties p = modLangData.get("en_US");
        p.put("itemGroup." + name, label);

        super.apply();
    }

    @Override
    public void edit()
    {
        tab.icon = icon;

        Map<String, Properties> modLangData = ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), "modLanguageData");
        Properties p = modLangData.get("en_US");
        if (p.getProperty("itemgroup." + name) == null || !p.getProperty("itemgroup." + name).equals(label))
        {
            p.put("itemGroup." + name, label);
            ClientHelper.refreshResources(FMLClientHandler.instance().getClient());
        }

        super.edit();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", name);
        compound.setString("Label", label);
        compound.setTag("Icon", ItemStackHelper.writeToNBTNamed(icon));
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        label = compound.getString("Label");
        icon = ItemStackHelper.readFromNBTNamed(compound.getCompoundTag("Icon"));

        return icon != null;
    }

    @Override
    public ItemStack getStack()
    {
        return icon;
    }

    @Override
    public String getLabel()
    {
        return name;
    }
}
