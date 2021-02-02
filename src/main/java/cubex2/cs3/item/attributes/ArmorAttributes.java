package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.common.WindowEditScript;
import cubex2.cs3.ingame.gui.item.WindowEditArmorMaterial;
import cubex2.cs3.ingame.gui.item.WindowEditArmorTexture;
import cubex2.cs3.util.ScriptWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;

public class ArmorAttributes extends ItemAttributes
{
    @Attribute(windowClass = WindowEditArmorMaterial.class)
    public ItemArmor.ArmorMaterial material = ItemArmor.ArmorMaterial.CLOTH;

    @Attribute(windowClass = WindowEditArmorTexture.class)
    public String texture = "somepack:textures/models/armor/my_armor.png";

    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onArmorUpdate = null;

    public ArmorAttributes(BaseContentPack pack)
    {
        super(pack);
        creativeTab = CreativeTabs.tabCombat;
        maxStack = 1;
    }

    @Override
    protected boolean addAttribute(String attribute, String type)
    {
        return !attribute.equals("maxStack") &&
                !attribute.equals("maxDamage") &&
                !attribute.equals("enchantability");

    }
}
