package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import cubex2.cs3.lib.Materials;
import net.minecraft.block.material.Material;

public class WindowEditMaterial extends WindowEditBlockAttribute implements IStringProvider<Material>
{
    private DropBox<Material> dbTabs;

    public WindowEditMaterial(WrappedBlock block)
    {
        super(block, "creativeTab", 170, 100);

        dbTabs = dropBox(Materials.getAllMaterials()).top(7).fillWidth(7).add();
        dbTabs.setStringProvider(this);
        dbTabs.setSelectedValue(container.material);

        label("You need to restart Minecraft\nfor the change to happen.").below(dbTabs, 10).add();
    }

    @Override
    protected void applyChanges()
    {
        container.material = dbTabs.getSelectedValue();
    }

    @Override
    public String getStringFor(Material value)
    {
        return Materials.getMaterialName(value);
    }
}
