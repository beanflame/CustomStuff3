package cubex2.cs3.lib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabs
{
    private static BiMap<String, net.minecraft.creativetab.CreativeTabs> creativeTabMap = HashBiMap.create();

    public static net.minecraft.creativetab.CreativeTabs getCreativeTab(String name)
    {
        if (name == null)
            return null;

        net.minecraft.creativetab.CreativeTabs creativeTab = null;

        if (creativeTabMap.containsKey(name))
        {
            creativeTab = creativeTabMap.get(name);
        }
        else
        {
            for (net.minecraft.creativetab.CreativeTabs tab : net.minecraft.creativetab.CreativeTabs.creativeTabArray)
            {
                String tabLabel = ObfuscationReflectionHelper.getPrivateValue(net.minecraft.creativetab.CreativeTabs.class, tab, "tabLabel", "field_78034_o");
                if (tabLabel.equals(name))
                {
                    creativeTabMap.put(name, tab);
                    creativeTab = tab;
                    break;
                }
            }
        }

        return creativeTab;
    }

    public static String getTabName(net.minecraft.creativetab.CreativeTabs creativeTab)
    {
        if (creativeTab == null)
            return null;

        String name = null;

        if (creativeTabMap.inverse().containsKey(creativeTab))
        {
            name = creativeTabMap.inverse().get(creativeTab);
        }
        else
        {
            for (net.minecraft.creativetab.CreativeTabs tab : net.minecraft.creativetab.CreativeTabs.creativeTabArray)
            {
                if (tab == creativeTab)
                {
                    String tabLabel = ObfuscationReflectionHelper.getPrivateValue(net.minecraft.creativetab.CreativeTabs.class, tab, "tabLabel", "field_78034_o");
                    creativeTabMap.put(tabLabel, creativeTab);
                    name = tabLabel;
                    break;
                }
            }
        }

        return name;
    }

    @SideOnly(Side.CLIENT)
    public static String[] getTabNames()
    {
        net.minecraft.creativetab.CreativeTabs[] tabs = net.minecraft.creativetab.CreativeTabs.creativeTabArray;
        String[] names = new String[tabs.length];
        for (int i=0;i<tabs.length;i++)
        {
            names[i] = tabs[i].getTabLabel();
        }
        return names;
    }
}
