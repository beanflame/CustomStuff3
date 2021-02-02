package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeDescription;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import cubex2.cs3.ingame.gui.common.WindowEditFloat;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.item.WindowEditPotion;
import cubex2.cs3.lib.Strings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.potion.Potion;

public class FoodAttributes extends ItemAttributes
{
    @Attribute(windowClass = WindowEditPotion.class)
    public Potion potion;

    @Attribute(windowClass = WindowEditInteger.class)
    @AttributeDescription(Strings.INFO_HUNGER)
    public int hunger = 2;
    @Attribute(windowClass = WindowEditInteger.class)
    @AttributeDescription(Strings.INFO_POTION_DURATION)
    public int potionDuration = 60;
    @Attribute(windowClass = WindowEditInteger.class)
    public int potionAmplifier;

    @Attribute(windowClass = WindowEditFloat.class)
    @AttributeDescription(Strings.INFO_SATURATION)
    public float saturation = 0.3f;
    @Attribute(windowClass = WindowEditFloat.class)
    public float potionProbability = 1.0f;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Can be eaten with full hunger")
    public boolean alwaysEdible;

    public FoodAttributes(BaseContentPack pack)
    {
        super(pack);
        maxUsingDuration = 32;
        usingAction = EnumAction.eat;
        creativeTab = CreativeTabs.tabFood;
    }
}
