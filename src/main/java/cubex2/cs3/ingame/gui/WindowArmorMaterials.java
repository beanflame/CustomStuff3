package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ArmorMaterial;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowArmorMaterials extends WindowContentList<ArmorMaterial>
{
    public WindowArmorMaterials(BaseContentPack pack)
    {
        super(ArmorMaterial.class, "Armor Materials", NEW | DELETE | BACK, 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<ArmorMaterial> desc)
    {
        desc.rows = 12;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateArmorMaterial(pack);
    }

    @Override
    protected Window createEditContentWindow(ArmorMaterial content)
    {
        return null;
    }
}
