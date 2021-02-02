package cubex2.cs3.registry;

import cubex2.cs3.common.ArmorMaterial;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowArmorMaterials;
import cubex2.cs3.lib.Strings;

public class ArmorMaterialRegistry extends ContentRegistry<ArmorMaterial>
{
    public ArmorMaterialRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public ArmorMaterial newDataInstance()
    {
        return new ArmorMaterial(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowArmorMaterials(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Armor Materials";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_ARMOR_MATERIAL;
    }
}
