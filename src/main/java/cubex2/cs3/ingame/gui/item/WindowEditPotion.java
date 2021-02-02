package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import cubex2.cs3.item.attributes.FoodAttributes;
import cubex2.cs3.lib.Potions;
import net.minecraft.potion.Potion;

public class WindowEditPotion extends WindowEditItemAttribute implements IStringProvider<Potion>
{
    private DropBox<Potion> dbPotions;

    public WindowEditPotion(WrappedItem item)
    {
        super(item, "potion", 150, 55);

        dbPotions = dropBox(Potions.getPotions(true)).top(7).fillWidth(7).add();
        dbPotions.drawNullValue = true;
        dbPotions.setStringProvider(this);
        dbPotions.setSelectedValue(((FoodAttributes) container).potion);
    }

    @Override
    protected void applyChanges()
    {
        ((FoodAttributes) container).potion = dbPotions.getSelectedValue();
    }

    @Override
    public String getStringFor(Potion value)
    {
        if (value == null)
            return "none";
        return Potions.getPotionName(value);
    }
}
