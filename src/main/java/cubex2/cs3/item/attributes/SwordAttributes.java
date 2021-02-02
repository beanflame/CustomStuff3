package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;

public class SwordAttributes extends ItemAttributes
{
    public SwordAttributes(BaseContentPack pack)
    {
        super(pack);
        creativeTab = CreativeTabs.tabCombat;
        full3d = true;
        usingAction = EnumAction.block;
        maxUsingDuration = 72000;
        maxStack = 1;
        maxDamage = 100;
    }
}
