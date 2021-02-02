package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import cubex2.cs3.item.attributes.ArmorAttributes;
import net.minecraft.item.ItemArmor;

public class WindowEditArmorMaterial extends WindowEditItemAttribute implements IStringProvider<ItemArmor.ArmorMaterial>
{
    private DropBox<ItemArmor.ArmorMaterial> dbMaterials;

    public WindowEditArmorMaterial(WrappedItem item)
    {
        super(item, "material", EDIT | CANCEL, 150, 55);

        dbMaterials = dropBox(ItemArmor.ArmorMaterial.values()).top(7).fillWidth(7).add();
        dbMaterials.setStringProvider(this);
        dbMaterials.setSelectedValue(((ArmorAttributes) container).material);
    }

    @Override
    protected void applyChanges()
    {
        ((ArmorAttributes) container).material = dbMaterials.getSelectedValue();
    }

    @Override
    public String getStringFor(ItemArmor.ArmorMaterial value)
    {
        return value.name();
    }
}
